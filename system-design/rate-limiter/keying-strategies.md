# Rate Limiting – Keying Strategies (When to Use What)

## 1️⃣ User (user_id)
**Use when:**
- Traffic is authenticated
- You need fairness per individual user
- Enforcing behavioral limits (e.g., posts/min, searches/min)

**Purpose:** Product-level fairness and abuse control  
**Not suitable for:** Pre-auth traffic

---

## 2️⃣ API Key
**Use when:**
- Building an API platform
- Enforcing subscription tiers or customer quotas
- Tracking usage for billing or SLAs

**Purpose:** Commercial quota enforcement per integration/app  
**Note:** Often combined with tenant-level limits

---

## 3️⃣ IP Address
**Use when:**
- Traffic is unauthenticated
- Protecting login/signup/password reset endpoints
- Preventing brute force or volumetric abuse

**Purpose:** Early infrastructure protection  
**Limitations:** NAT sharing, proxy rotation, botnets

---

## 4️⃣ Tenant (Organization / Account)
**Use when:**
- Multi-tenant SaaS
- Preventing one customer from consuming disproportionate resources
- Enforcing organization-level plans

**Purpose:** Platform fairness + blast radius control

---

## 5️⃣ Endpoint (Route-Level)
**Use when:**
- Different endpoints have different cost or risk
- Login, payments, search, or heavy compute endpoints need stricter limits

**Purpose:** Cost and risk-based traffic control  
**Typically combined with:** user, tenant, or API key

---

# Practical Rule

- **Unauthenticated traffic → IP**
- **Authenticated product fairness → User**
- **API monetization → API Key**
- **Multi-tenant protection → Tenant**
- **High-cost/high-risk operations → Endpoint (as additional dimension)**

Most real systems combine multiple keys for layered protection.
