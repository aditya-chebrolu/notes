# Rate Limiting Architecture: Placement & Responsibility

## System Flow with Rate Limiting Placement
```
Client
   |
   v
[ CDN / WAF ]
   |   --> IP-based rate limiting (coarse, anti-abuse)
   v
[ API Gateway / Edge ]
   |   --> IP limit
   |   --> User limit (after auth)
   |   --> Tenant / API key quota
   |   --> Route-specific limits
   v
[ Load Balancer / Reverse Proxy ]
   |   --> Basic connection / request rate limits
   v
[ Service Middleware ]
   |   --> Business-logic-aware limits
   |   --> Per-resource / per-operation limits
   v
[ DB / downstream systems ]
```
---

## 1) IP-based Rate Limiting → Edge (CDN / WAF / Gateway)

### Protects Against
- Bots
- Scrapers
- DDoS
- Credential stuffing
- Unauthenticated abuse

### Why It Lives Early
At this stage, identity is typically limited to:
- IP
- Headers
- Possibly API key

The only reliable identity is usually the IP.

### Examples
- 100 requests/min per IP
- 10 login attempts/min per IP

### Goal
Drop malicious or abusive traffic before it reaches your application services.

---

## 2) User-based Rate Limiting → API Gateway (After Auth)

### Identity Available
- user_id
- roles
- subscription plan
- scopes

### Examples
- 1000 requests/min per user
- 5 password reset attempts/hour per user
- 50 POST /orders per minute per user

### Why at the Gateway
- Centralized enforcement
- Consistent across services
- Protects downstream systems
- Avoids duplicated logic in microservices

---

## 3) Tenant / Customer / API Key Limits → API Gateway

### Common in Multi-Tenant Systems
Identity may be:
- tenant_id
- customer_id
- API key
- subscription tier

### Examples
- Free: 1,000 requests/day
- Pro: 100,000 requests/day
- Enterprise: custom quota

### Implementation
- API Gateway
- Backed by Redis or distributed cache
- Config store for plan definitions

### Why Here
- Global across services
- Enforces contractual quotas
- Protects infrastructure capacity

---

## 4) Route-Specific Limits → Gateway or Service

### Examples
- POST /checkout → strict limits
- GET /health → high limits
- /search → controlled but higher throughput

### Placement
- Gateway (preferred for consistency)
- Service (if dependent on business context)

---

## 5) Business-Logic Limits → Service Layer

These are not pure traffic limits.

### Examples
- Max 3 active projects per user
- Max 10 concurrent exports per tenant
- Only 1 payout request at a time
- Prevent duplicate order submission within 10 seconds

### Why Service-Level
- Requires database or state awareness
- Depends on domain logic
- Not just request counting

---

## 6) Load Balancer Limits → Infrastructure Protection

### What It Can Do
- Connection limits
- Requests/sec per IP

### Limitations
- No user identity
- No tenant context
- No subscription awareness

### Role
- Safety net
- Circuit breaker
- Infrastructure-level protection

---

## Summary: What Goes Where?

| Limit Type           | Best Placement        | Why |
|----------------------|----------------------|------|
| IP-based             | CDN / Gateway        | Early drop, anti-abuse |
| User-based           | API Gateway          | Centralized & auth-aware |
| Tenant-based         | API Gateway          | Contract enforcement |
| API key              | API Gateway          | Standard pattern |
| Route-specific       | Gateway (usually)    | Configurable |
| Business-rule limits | Service              | Needs application context |
| Connection limits    | Load Balancer        | Infrastructure-level |

---

## Golden Rule

Enforce as early as possible, but as late as necessary.

- IP identity → Edge  
- User/Tenant identity → Gateway  
- Business state → Service  

---

## Typical Modern SaaS Setup

IP throttling          → CDN / WAF  
Global quotas          → API Gateway  
User & tenant limits   → API Gateway + Redis  
Business constraints   → Service layer  
Safety caps            → Load Balancer  
