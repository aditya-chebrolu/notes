# Rate Limiter – System Design Cheat Sheet

## 1️⃣ Clarify Requirements
- What to limit? (requests, logins, etc.)
- Key? (user, API key, IP, tenant, endpoint, combination)
- Rate format? (N req / T sec, burst + sustained?)
- Strict or approximate?
- Single-region or multi-region?
- What happens on limit? (429, retry-after, queue?)
- Observability needed?

## 2️⃣ Where to Enforce?
- Prefer API Gateway / Edge
- Earlier = better protection
- Service-level only if very specific

## 3️⃣ Choose Algorithm
- ✅ Token Bucket (default choice) – supports bursts + steady rate
- Leaky Bucket – smooth output
- Fixed Window – simple but bursty at edges
- Sliding Window – more accurate, more expensive

## 4️⃣ Data Per Key
For each limiter key:
- tokens
- last_refill_timestamp
- capacity
- refill_rate
- Add TTL to auto-clean unused keys

## 5️⃣ Storage Design
### Small scale:
- In-memory (per node)

### Distributed (common case):
- Redis
- Use atomic Lua script
- Single operation = refill + check + decrement

## 6️⃣ Multiple Limits (Stacked Rules)
Apply in order:
- Global platform limit (IP)
- Tenant limit
- User limit
- Endpoint limit

Reject if any fails.

## 7️⃣ Failure Handling
If Redis is down:
- Fail-closed → safer but risks outage
- Fail-open → more available but risky

Use:
- Circuit breaker
- Local fallback (optional)

## 8️⃣ Multi-Region Strategy
### Approximate (common):
- Per-region quotas
- Split global budget

### Strict:
- Single global store
- Higher latency & complexity

## 9️⃣ Scaling Considerations
- Use atomic operations
- Redis clustering / sharding
- Handle hot keys (striping or hierarchical limits)
- Avoid multiple network round-trips

## 🔟 Response Design
Return:
- 429 Too Many Requests
- Retry-After
- Optional rate-limit headers

## 1️⃣1️⃣ Observability
Track:
- Allowed vs rejected
- Top keys
- Latency
- Backend errors
- Rule versioning

## 🎯 5-Minute Interview Flow

1. Clarify requirements  
2. Enforce at gateway  
3. Choose token bucket  
4. Store tokens + timestamp in Redis  
5. Use atomic script  
6. Stack limits  
7. Plan failure handling  
8. Address multi-region  
9. Discuss scaling & hot keys  
10. Add observability  
