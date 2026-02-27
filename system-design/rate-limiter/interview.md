# Production-Grade Rate Limiter Notes (Public REST API)

These notes cover concepts, algorithms, distributed architecture, Redis/Lua implementation patterns, failure handling, performance, and common interview nuances for building a production rate limiter (e.g., 100 requests/min per API key, per endpoint).

---

## 1) What a Rate Limiter Does

**Goal:** Control how many requests a client can make in a time period, to:
- protect backend services from overload
- stop abuse / scraping
- provide fairness across tenants
- manage cost and QoS tiers

**Typical behavior:**
- Identify caller (API key / OAuth client / user ID / IP)
- Decide whether request is allowed
- If not allowed, return **HTTP 429 Too Many Requests**
- Provide headers to help clients back off (e.g., `Retry-After`)

---

## 2) Core Requirements Checklist

### Functional
- Limit by **identity** (e.g., API key)
- Enforce limit: e.g. **100 requests/min**
- Return **429** when exceeded
- Support **burst traffic** (define what “burst” means)
- Can be **per endpoint** / per route template

### Non-Functional
- **Distributed** across multiple gateway/API servers
- **Consistent** across nodes (no double-spend)
- **Low latency** (e.g., p99 < 5ms for the check)
- **Highly available** (survive Redis failures)
- Observability, correctness under concurrency

---

## 3) Key Terms (Interview-Friendly)

### p50 / p95 / p99
Percentile latency:
- **p99** = 99% of requests complete faster than this.
Used because tail latency matters in distributed systems.

### Burst
Two common meanings:
1. **Token bucket “instant capacity”**: how many requests can be allowed immediately if client was idle.
2. **Hard per-second cap**: max requests in *any* 1-second window.

If user says “burst = how many per second”, you may need **two limits** (per-second + per-minute).

### Atomicity
Prevent race conditions when multiple nodes update the same rate state. Achieved via:
- Redis **Lua script** (read-modify-write in one atomic operation)
- Or Redis **transactions** (less common / trickier)

### Connection Pooling
Reusing a small set of pre-opened TCP/TLS connections to Redis.
Avoids connection handshake overhead and keeps p99 stable.

### Cardinality
Number of unique keys:
- **High cardinality**: millions of `(api_key, endpoint)` combinations.
Impacts memory, sharding, and metric label design.

### Clock Skew
Different machines have slightly different clocks.
For time-based refill logic, rely on a single time source (e.g., **Redis TIME**) to avoid inconsistencies.

---

## 4) Where to Enforce

### Best Practice: At the API Gateway
- blocks early (cheaper)
- protects all downstream services
- unified policy enforcement

You can also add a **secondary limiter** in services for defense-in-depth.

---

## 5) Identity & Bucket Design

### Identify caller
- API key in header (`X-API-Key`) or Authorization token
- If absent: apply anonymous/IP-based policy (optional)

### Per-endpoint limiting
Never key on raw paths like `/users/123` → creates a key per ID.
Use **route template**:
- `GET /users/{id}` not `/users/123`

### Bucket key examples
- `bucket = api_key + ":" + METHOD + ":" + route_template`
- Redis key: `rl:v1:{bucket}` (optionally hash route template if long)

---

## 6) Algorithm Options (Pros/Cons)

### Fixed Window Counter
**How:** Count requests in a fixed time bucket (e.g., per minute).
- Implementation: `INCR` + `EXPIRE`
- Pros: simple, fast
- Cons: **boundary burst** (100 at 12:00:59 + 100 at 12:01:00 = 200 in ~1s)
- Distributed: OK with Redis atomic increment, but fairness is weaker

### Sliding Window Log
**How:** Store timestamps of each request; count last 60 seconds.
- Pros: most accurate
- Cons: expensive storage and CPU (O(N) per request)

### Sliding Window Counter
**How:** Approximate sliding window by weighting previous bucket.
- Pros: better than fixed window, cheaper than log
- Cons: still approximation; more logic

### Token Bucket (Recommended Default)
**How:** Bucket has tokens; refill over time; each request consumes 1 token.
- Pros: supports bursts naturally; smooth control; efficient
- Cons: burst semantics must be defined carefully
- Distributed: best with Redis + Lua

### Leaky Bucket
**How:** Process at fixed rate (queue-like).
- Pros: smooth output
- Cons: can add queuing/latency; not always desired for APIs

### GCRA (Generic Cell Rate Algorithm)
**How:** A compact sliding-window equivalent used by some systems.
- Pros: accurate and memory-efficient
- Cons: harder to explain; token bucket often simpler for interviews

---

## 7) Recommended Production Architecture (Redis + Lua)

### High-level flow
1. Gateway extracts `(api_key, endpoint_bucket)`
2. Gateway calls Redis **EVALSHA** for atomic allow/deny
3. If deny: return 429 with headers
4. If allow: forward to backend

### Why Redis?
- shared state across nodes → global consistency
- very low latency in-region
- supports atomic scripts

### Why Lua?
Avoid race conditions:
- without Lua: two gateways can read same token value and both allow
- with Lua: read+update is one atomic operation inside Redis

---

## 8) Redis Data Model

### State per bucket
- `tokens` (float)
- `ts_ms` (last update time)

Use a Redis hash:
- `HSET key tokens <float> ts <ms>`

### TTL (Why it’s safe)
Even if “bucket is permanent”, state is reconstructable:
- If inactive long enough, bucket would be full anyway.
TTL prevents unbounded memory growth for high cardinality keys.

Typical:
- `PEXPIRE key 5 minutes` (or a few multiples of your window)

---

## 9) Token Bucket Math (Example)

Limit: 100 requests/minute  
Refill rate:
- 100 / 60 = 1.6667 tokens/sec
- 100 / 60000 = 0.0016667 tokens/ms

Capacity:
- If capacity=100, an idle client can do 100 instantly.

Refill formula:
- `tokens = min(capacity, tokens + elapsed_ms * refill_per_ms)`

Reject and compute retry:
- If `tokens < 1`, `retry_after_ms = ceil((1 - tokens) / refill_per_ms)`

---

## 10) Lua Script Contract (What to Return)

Return fields (example):
- `allowed` (1/0)
- `tokens_remaining`
- `retry_after_ms` (0 if allowed)
- `reset_after_ms` (time until full)

Use `redis.call("TIME")` inside Lua to avoid clock skew.

**Constant-time script:** fixed amount of work per request (O(1)):
- read 2 fields → compute → write 2 fields → set TTL
No loops over stored data.

---

## 11) HTTP Responses & Headers (Best Practice)

On deny:
- Status: **429 Too Many Requests**
- Headers:
  - `Retry-After: <seconds>` (rounded up)
  - `X-RateLimit-Limit: 100;w=60` (optional)
  - `X-RateLimit-Remaining: <n>` (optional)
  - `X-RateLimit-Reset: <epoch_seconds>` (optional)
- Body (optional JSON):
  - `{ "error": "rate_limited", "retry_after_seconds": 12 }`

---

## 12) Performance (<5ms) Checklist

- Redis in the **same region/VPC**
- **Connection pooling** at gateway
- **One Redis round-trip** (Lua)
- Use **EVALSHA** (preload script) not EVAL each time
- Keep script **O(1)** (constant time)
- Avoid excessive per-request allocations/logging

### Latency Budget (Typical)
- network RTT: ~0.2–1ms in-region
- Lua compute: microseconds
- serialization: small
Achievable p99 < 5ms if Redis is healthy and near.

---

## 13) Scaling Redis: Why Cluster / Horizontal Scaling

You scale when:
- QPS rises (CPU/network bottleneck)
- keyspace grows (memory bottleneck)
- you need higher availability

Redis Cluster:
- shards keys across nodes (horizontal scale)
- distributes memory and compute

**High cardinality** (many API keys × endpoints) amplifies memory needs and may require sharding.

---

## 14) Failure Handling (Redis Down)

### Common strategies
- **Fail-closed:** reject when Redis unavailable (protect backend, hurts availability)
- **Fail-open:** allow when Redis unavailable (keeps API available, risk overload)
- **Graceful degrade:** temporary local limits + allow partial

### Recommended for your choice: Fail-open + Guardrails
1. **Circuit breaker**
   - If Redis error rate high or latency spikes → stop calling Redis temporarily
   - Avoid cascading latency from Redis to API

2. **Coarse backend cap**
   - Prevent backend meltdown during Redis outage
   - Example: per-gateway-node max RPS to upstream, or queue/backpressure

**Important nuance:** If you have N gateway nodes and each allows M RPS during outage,
total allowed can be N×M → can overload backend. Choose caps accordingly.

---

## 15) Hot Key Problem & Mitigations

Hot key = one API key gets massive traffic → one Redis shard becomes a hotspot.

Mitigations:
- **Negative cache**: if a key is blocked with retry-after, cache that locally for 100–500ms
  → avoids repeatedly calling Redis for known violators
- **Hierarchical limiting**: coarse per-node caps before Redis
- Consider per-second caps for abusive patterns (optional)
- WAF/bot protection upstream if needed

---

## 16) Observability & Metrics

### Must-have metrics
- Allowed count / blocked count (`429`) by endpoint bucket (careful with high-cardinality labels)
- Redis command latency p50/p95/p99
- Redis error rate, timeouts, reconnects
- Circuit breaker state (open/closed) + transitions
- Fallback (fail-open) traffic volume
- Coarse-cap drops/queues
- Upstream latency/errors during incidents

### Logging
- log sampled events for rate-limit denies
- avoid logging every request (costly)

---

## 17) Config & Policy Nuances

- Different limits for tiers: free vs paid (e.g., 60/min vs 600/min)
- Per-method: `GET` vs `POST` different limits
- Weighted requests: expensive endpoints cost more than 1 token
- Exemptions / allowlist
- Multiple dimensions: per IP + per key (defense in depth)

---

## 18) Common Interview Pitfalls

- Keying on raw path params (explodes cardinality)
- Not using atomic operations (race condition)
- Ignoring TTL/memory growth
- Not addressing Redis outage behavior
- Confusing “burst” semantics
- Not discussing tail latency (p99)
- High-cardinality metrics causing monitoring issues

---

# Practice Question Variations (Flavors)

## A) Basic → Distributed
1. Design 100 req/min per API key (single node).
2. Make it distributed across 50 API servers.
3. Make it per endpoint + per method.

## B) Burst Semantics
4. Support burst: “max 20 requests in any 1 second” + 100/min.
5. Allow 100/min but cap instantaneous burst to 30.

## C) Multi-Tenant / Plans
6. Free: 60/min, Pro: 600/min, Enterprise: custom.
7. Add per-tenant global limit + per-user limit.

## D) Fairness & Weighted Costs
8. Some endpoints cost 5 tokens, others cost 1.
9. Batch endpoints: 1 request can represent 100 operations → how to charge?

## E) Failure & Resilience
10. Redis goes down: choose fail-open/closed; design fallback.
11. Redis latency spikes: circuit breaker + degraded mode.

## F) Scale & Hot Keys
12. One API key does 50k RPS: handle hot key and protect Redis.
13. Millions of API keys: memory sizing and TTL policy.

## G) Edge / CDN / Global
14. Global API with multiple regions: ensure consistent limiting across regions.
15. Edge enforcement at CDN + central enforcement (two-layer).

## H) Abuse / Security
16. Attackers rotate API keys/IPs: combine rate limiting with WAF/bot detection.
17. Replay attacks / stolen keys: anomaly detection + throttling.

## I) Product/UX Headers & Developer Experience
18. Return structured headers and docs; design client backoff behavior.
19. Provide per-key quota visibility endpoint.

---

# Mini “Interview Answer Templates”

## Template: Choose algorithm
- Token bucket for smooth rate and burst support
- Redis + Lua for distributed atomicity
- Per-route-template keys to avoid explosion
- TTL to prevent unbounded key growth

## Template: Race condition explanation
- Multiple gateways can read same token count
- Both decrement → double-spend
- Lua makes it atomic and consistent globally

## Template: Redis outage stance
- Pick fail-open vs fail-closed based on business priority
- Add circuit breaker
- Add coarse caps to prevent backend meltdown

---

# Quick Glossary
- **EVALSHA:** run a cached Lua script by hash (faster than sending script each time)
- **Circuit breaker:** stop calling a failing dependency temporarily
- **Hot key:** a key receiving disproportionate traffic causing shard hotspot
- **p99:** 99th percentile latency
- **TTL:** time-to-live expiry to bound memory
- **Cardinality:** number of unique keys/labels

---
