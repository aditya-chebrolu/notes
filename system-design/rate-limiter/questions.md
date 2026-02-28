# Rate Limiter Practice Bank (Varied Limits + Scenarios)

Each question is designed to *push you toward a different algorithm / architecture*.

---

## 1) Public API — Simple Per-Key (Token Bucket / Fixed Window)
**Scenario:** Public REST API for third-party devs  
**Limit:** 120 req/min per API key  
**Burst:** allow up to 60 requests instantly after idle  
**Requirements:**
- distributed gateways (20 nodes)
- p99 check < 5ms
- return 429 + Retry-After
**Twist:** Redis outage must be fail-open with coarse caps.

---

## 2) Strict Per-Second + Per-Minute (Dual Limiter)
**Scenario:** Payments API  
**Limits:** 10 req/sec AND 300 req/hour per API key  
**Requirements:**
- hard per-second ceiling (no micro-bursts)
- consistent across nodes
**Goal:** design **two-level** limiter in one atomic check.

---

## 3) Login Protection (IP + User + Device Dimensions)
**Scenario:** Login endpoint  
**Limits:**
- per IP: 20/min
- per username: 10/min
- per device fingerprint: 30/min
**Twist:** attackers rotate IPs and usernames  
**Goal:** multi-dimensional limiting + abuse resistance.

---

## 4) SMS OTP Throttle (Long Windows, Few Requests)
**Scenario:** OTP service  
**Limits:**
- 3 OTPs / 15 minutes per phone number
- 10 OTPs / day per phone number
**Twist:** strict regulatory auditing + exact reset times  
**Goal:** long-window accurate limiter + auditability.

---

## 5) Sliding Window Accuracy (User-Facing Fairness)
**Scenario:** Chat API for customers  
**Limit:** 60 req/min per user  
**Constraint:** no boundary burst allowed (must be fair in last 60s)  
**Goal:** justify **sliding window** (counter or GCRA) vs token bucket.

---

## 6) Multi-Tier Plans (Config-Driven Limits)
**Scenario:** SaaS API with pricing tiers  
**Limits:**
- Free: 60/min
- Pro: 600/min
- Enterprise: 10,000/min
**Twist:** per endpoint multipliers (writes cost more)  
**Goal:** dynamic config + weighted token bucket.

---

## 7) Weighted Requests (Cost-Based Limiting)
**Scenario:** Search API  
**Limit:** 1000 “cost units” / minute  
**Costs:**
- simple search = 1 unit
- advanced search = 5 units
- export = 50 units
**Goal:** weighted token bucket / leaky bucket + fairness.

---

## 8) GraphQL Limiter (Query Complexity)
**Scenario:** GraphQL endpoint  
**Limit:** 500 complexity points / minute per API key  
**Twist:** complexity is computed from query AST  
**Goal:** limiter integrated with request parsing + cost model.

---

## 9) Burst Smoothing (Leaky Bucket / Queue)
**Scenario:** Email sending API  
**Limit:** 10,000 emails/hour per tenant  
**Constraint:** downstream provider only accepts 2/sec sustained  
**Goal:** **leaky bucket** / queue-based smoothing (not just reject).

---

## 10) Background Jobs API (Fair Scheduling)
**Scenario:** Job submission API  
**Limit:** 100 jobs/min per tenant  
**Twist:** must ensure fairness across tenants under heavy load  
**Goal:** rate limiting + **fair queueing** / token allocation per tenant.

---

## 11) Multi-Region Consistency (Global Limit)
**Scenario:** API deployed in 3 regions  
**Limit:** 3000 req/min globally per API key (NOT per region)  
**Constraints:**
- must enforce global limit
- latency budget tight
**Goal:** discuss global coordination: centralized Redis, regional quotas, or async reconciliation.

---

## 12) Edge/CDN Limiter (Approximate at Edge)
**Scenario:** CDN/edge rate limiting for DDoS mitigation  
**Limit:** 100 req/10s per IP  
**Constraint:** must work at edge locations with no shared DB  
**Goal:** approximate local limit + central signals, or hierarchical design.

---

## 13) Mobile API (Offline Tolerance)
**Scenario:** Mobile clients sometimes offline then reconnect  
**Limit:** 200/day per user  
**Twist:** clients replay queued requests on reconnect  
**Goal:** idempotency + rate limiting + replay handling.

---

## 14) Per-Endpoint Mix (Different Limits per Route)
**Scenario:** Public API  
**Limits:**
- GET /status: 300/min
- GET /search: 60/min
- POST /orders: 30/min
**Twist:** must be configurable without redeploy  
**Goal:** policy engine + route template bucketing.

---

## 15) Hot Key Attack (Redis Shard Meltdown)
**Scenario:** One API key gets 50k RPS abusive traffic  
**Limit:** 100/min but attacker keeps hammering  
**Goal:** negative caching, local blocking, circuit breaker, protect Redis from reject storms.

---

## 16) “Retry-After” Precision (Developer UX)
**Scenario:** API used by partners  
**Limit:** 500/min per key  
**Requirement:** Retry-After must be accurate; clients depend on it  
**Goal:** algorithm that returns precise retry time (token bucket / GCRA).

---

## 17) Privacy Constraints (No User ID Storage)
**Scenario:** privacy-sensitive API  
**Limit:** 100/min per “anonymous user”  
**Constraint:** cannot store raw user identifiers in Redis  
**Goal:** hashing/salting keys, privacy, compliance, rotating salts.

---

## 18) On-Prem + Cloud Hybrid (Unreliable Central Store)
**Scenario:** some gateways are on-prem with intermittent link to cloud Redis  
**Limit:** 1000/min per tenant  
**Goal:** local limiting + periodic sync, or token leasing model.

---

## 19) Token Leasing (Reduce Central Calls)
**Scenario:** extremely high QPS service  
**Limit:** 10,000/sec per tenant  
**Constraint:** Redis calls per request too expensive  
**Goal:** token leasing: gateway fetches a batch of tokens and spends locally.

---

## 20) Rate Limit + Quota (Daily Caps)
**Scenario:** API with both realtime rate limit and daily quota  
**Limits:**
- 100/min per key
- 10,000/day per key
**Goal:** combine short-window limiter with long-window quota counter.

---

## 21) Webhook Delivery (Provider Throttling + Backoff)
**Scenario:** you deliver webhooks to customers  
**Limits:**
- per destination host: 5/sec
- per tenant: 100/sec
**Twist:** must retry failed webhooks with exponential backoff  
**Goal:** limiter + retry queue + per-destination fairness.

---

## 22) Streaming API (Connection-Based Limits)
**Scenario:** SSE/WebSocket streaming API  
**Limits:**
- max 5 concurrent connections per user
- max 50 messages/sec per connection
**Goal:** connection tracking + message rate limiting.

---

## 23) Multi-Identity Priority (User + App)
**Scenario:** API calls have both `app_id` and `user_id`  
**Limits:**
- app_id: 10,000/min
- user_id: 100/min
**Goal:** hierarchical limiting: request must pass both.

---

## 24) Org-Wide Budget (Shared Pool)
**Scenario:** Enterprise org has shared pool  
**Limits:**
- org: 1,000,000/day
- team within org: 50,000/day
**Goal:** shared budget allocation, fairness, reporting, preventing team starvation.

---

## 25) “Warm Start” Semantics (Cold Key vs Known Key)
**Scenario:** unknown/new API keys should be stricter initially  
**Limits:**
- new key: 10/min for first hour
- then: 100/min
**Goal:** dynamic policy based on key age + state.

---

# How to Use These in Practice
Pick one question and answer in this order:
1) Clarify burst semantics + reset behavior
2) Choose algorithm + justify
3) Choose storage (in-memory vs Redis vs multi-region)
4) Ensure atomicity + race-free updates
5) Failure mode + fallback
6) Performance (p99) + scaling
7) Observability and ops

---
