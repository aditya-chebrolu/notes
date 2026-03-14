# 1. Start with the right clarifying questions

These are the kinds of **valid interview questions** you should ask first.

## Product scope

* Is this like Bitly, TinyURL, or an internal company-only shortener?
* Do we only need **shorten + redirect**, or also:

  * custom aliases
  * expiration times
  * analytics/click tracking
  * QR codes
  * bulk shortening
  * user accounts
* Should short URLs be **publicly guessable** or hard to enumerate?
* Do deleted links need to stop working immediately?

## Traffic and scale

* How many URLs are created per day?
* How many redirects per day?
* Expected read/write ratio?
* Global traffic or single region?
* Expected latency target for redirect?

## Reliability and consistency

* Is it okay if analytics are eventually consistent?
* Should short links work even during regional outages?
* Do we need strong consistency for alias uniqueness?

## Security and abuse

* Should we block phishing/spam/malware URLs?
* Rate limits per user/IP?
* Do we need auth for creating links?

---

# 2. Define the interview scope clearly

A clean scope statement helps a lot.

## Functional requirements

* User submits a long URL and gets a short URL back
* Visiting short URL redirects to original URL
* Optional custom alias support
* Optional expiration support
* Optional analytics: click count, timestamp, country, referrer

## Non-functional requirements

* Very low redirect latency
* High availability
* Massive read-heavy scale
* Durable storage
* Short code uniqueness
* Basic abuse prevention

## Out of scope unless asked

* Full auth system
* Billing
* Advanced dashboards
* ML-based abuse detection
* Cross-device identity analytics

In interviews, it is good to say:

> “I’ll first design core shortening and redirect flow, then extend it for analytics, custom aliases, expiration, and abuse prevention if time allows.”

---

# 3. Estimate scale

Use rough numbers. Interviewers usually care more about method than precision.

Example assumptions:

* **100 million new URLs/month**
* **1 billion redirects/month**
* Read/write ratio = **10:1**

## Storage

Suppose each record stores:

* short code: 8 bytes
* original URL: 500 bytes average
* metadata/user/created_at/expiry: 100 bytes
* overhead/indexes: say 100–200 bytes

Roughly **~700–800 bytes per URL**

For 100M URLs:

* around **70–80 GB raw**
* with replication + indexes, several hundred GB total

This is very manageable.

## Bandwidth

Redirect traffic dominates, not writes.

---

# 4. Define APIs

Keep them simple.

## Create short URL

`POST /api/v1/urls`

Request:

```json
{
  "long_url": "https://example.com/some/very/long/path",
  "custom_alias": "promo2026",
  "expire_at": "2026-12-31T00:00:00Z"
}
```

Response:

```json
{
  "short_url": "https://sho.rt/abc123",
  "code": "abc123"
}
```

## Resolve URL

`GET /{code}`

Response:

* `301` or `302` redirect to original URL

## Analytics

`GET /api/v1/urls/{code}/stats`

---

# 5. Core data model

A basic schema:

## URL mapping table

```text
short_code       PK
long_url
created_at
expire_at
user_id          nullable
is_active
custom_alias     nullable
```

## Analytics events table or stream sink

```text
event_id
short_code
timestamp
ip_hash
country
user_agent
referrer
```

For core redirect service, the important table is just the mapping.

---

# 6. High-level architecture

A standard design looks like this:

```text
Client
  -> Load Balancer
    -> URL Shortening Service
       -> Cache (Redis)
       -> Database (SQL or NoSQL)
       -> ID Generator
       -> Analytics Queue

Client
  -> Load Balancer
    -> Redirect Service
       -> Cache
       -> Database
       -> Async Analytics Queue
```

## Key idea

* **Redirect path must be very fast**
* Analytics should be **async**, not in the critical path

---

# 7. Step-by-step process to design it

This is the sequence you can follow in an interview.

## Step 1: Start with the simplest solution

* Store `{short_code -> long_url}` in DB
* On shorten: generate unique code and save mapping
* On redirect: look up code and return redirect

Then say why that is not enough:

* DB hot path for every redirect
* no caching
* ID generation not defined
* no analytics or abuse handling

## Step 2: Add caching

Use Redis:

* `cache[short_code] = long_url`

Redirect flow:

1. Check cache
2. On miss, query DB
3. Populate cache
4. Redirect

This reduces DB load massively.

## Step 3: Decide how to generate short codes

This is one of the most important parts.

---

# 8. Short code generation options

There are 3 common approaches.

## Option A: Auto-increment ID + Base62 encoding

Example:

* DB ID = `125`
* Base62(125) = `cb`

### Pros

* Simple
* Short and compact
* Easy uniqueness

### Cons

* Predictable/enumerable
* Harder in distributed multi-region setup unless using centralized ID generation

This is often the best interview baseline.

---

## Option B: Distributed unique ID (Snowflake-style) + Base62

Generate 64-bit unique IDs using:

* timestamp
* machine ID
* sequence

Then Base62 encode.

### Pros

* Distributed
* No DB bottleneck for ID generation
* Unique and scalable

### Cons

* Still somewhat guessable
* Slightly more complex

This is a strong production answer.

---

## Option C: Random string

Generate a random 6–8 char Base62 code.

### Pros

* Harder to predict
* Simple conceptually

### Cons

* Collisions possible
* Need uniqueness checks/retries
* More expensive at very large scale

Good if interviewer cares about unguessability.

---

# 9. Base62 basics

Character set:

* `a-z` = 26
* `A-Z` = 26
* `0-9` = 10

Total = **62 characters**

Capacity:

* 6 chars = `62^6 ~= 56.8B`
* 7 chars = `62^7 ~= 3.5T`

So **6–7 characters** is usually enough for a long time.

---

# 10. Database choice

## SQL

Good when you need:

* strong constraints
* uniqueness on custom aliases
* simple schema
* predictable queries

## NoSQL

Good when you need:

* huge horizontal scale
* simple key-value access
* partitioned writes

For this problem, both are acceptable.

A clean answer:

* Use **SQL** for simpler correctness and alias uniqueness early on
* Move to **distributed KV/NoSQL** for extreme scale if needed

Interviewers usually accept either if justified.

---

# 11. Partitioning and scaling

As traffic grows:

## Reads

* Cache handles most redirect traffic
* Use read replicas or distributed KV store

## Writes

* Partition by `short_code` hash
* Or by generated ID range

## Important note

Do **not** partition by long URL. Redirect lookup is by short code.

---

# 12. Redirect behavior: 301 vs 302

This is a good technical point.

## 301 Permanent Redirect

* Better caching by browsers/CDNs
* Good if mapping never changes

## 302 Temporary Redirect

* Safer if destination could change
* More flexible

Interview answer:

* Use **302 by default** initially for safety
* Use **301** when links are immutable and caching benefits matter

---

# 13. Handling duplicate long URLs

Question: if two users shorten the same long URL, should they get the same short link?

## Option 1: Always create new short URL

### Pros

* Simpler
* supports user-level ownership/analytics

## Option 2: Deduplicate by long URL hash

### Pros

* Saves storage

### Cons

* Harder with custom aliases, expiration, user-specific settings

Best interview answer:

* For public products, I would **not deduplicate by default**
* If needed, keep a normalized URL hash index for optional dedup

---

# 14. Cache design

Redis is the usual answer.

## What to cache

* `short_code -> long_url`
* optionally `short_code -> metadata`

## TTL

* Can use long TTL because mappings rarely change
* On delete/update, invalidate cache

## Hot keys

Very popular links can become hot.
Mitigations:

* Redis clustering
* local in-memory cache in app servers
* CDN for redirect edge handling

---

# 15. Analytics design

Do not put analytics writes in the redirect critical path.

## Redirect flow with analytics

1. Resolve URL from cache/DB
2. Return redirect immediately
3. Publish click event asynchronously to Kafka/PubSub/SQS
4. Downstream consumers aggregate stats

## Why async

* Protect redirect latency
* absorb spikes
* support batch analytics pipelines

You can store:

* total clicks
* unique visitors approximation
* geography
* referrers
* device/browser

---

# 16. Expiration support

Store `expire_at`.

On redirect:

* if expired, return `404` or custom “link expired” page

Optimization:

* include expiry metadata in cache
* background job can clean expired links eventually

---

# 17. Custom alias support

Example:
`sho.rt/summer-sale`

Need:

* uniqueness constraint on alias
* reserved words blocklist (`admin`, `login`, `api`, etc.)
* validation rules
* rate limiting to prevent squatting

This is a good place to mention:

* strong consistency matters for alias uniqueness

---

# 18. Abuse prevention and safety

This is a very good differentiator in interviews.

## Risks

* phishing
* malware
* spam campaigns
* brute-force enumeration
* open redirect abuse

## Defenses

* URL validation
* domain/IP allowlist or denylist
* Safe Browsing checks
* rate limiting by IP/user/API key
* CAPTCHA for anonymous creation
* abuse reporting + takedown
* monitor unusually high creation rates
* random/non-sequential codes if enumeration is a concern

---

# 19. Availability and failure handling

## Failure cases

* Cache down
* DB shard down
* ID generator unavailable
* analytics queue backed up

## Design choices

* Redirect still works from DB if cache misses
* Analytics can be dropped or retried without affecting redirect
* Use replicated DB / multi-AZ
* ID generator should be distributed or have failover
* Use circuit breakers and rate limiting

---

# 20. Multi-region design

If interviewer pushes on global scale:

## Simple approach

* one primary write region
* multiple read regions
* CDN or geo-DNS
* cache at edge

## Tradeoff

* cross-region latency for create path is acceptable
* redirect path should be region-local via cache/replicated store

This is usually enough unless interviewer wants deep geo-distributed consistency.

---

# 21. Common tradeoffs to discuss

These are the kinds of technical tradeoffs interviewers like.

## Predictable IDs vs random codes

* predictable is simpler
* random is safer against enumeration

## SQL vs NoSQL

* SQL easier for correctness
* NoSQL easier for extreme scale

## 301 vs 302

* 301 better caching
* 302 safer flexibility

## Sync analytics vs async analytics

* sync gives immediate stats
* async gives much better latency and resilience

## Dedup vs no dedup

* dedup saves storage
* no dedup keeps semantics simpler

---

# 22. A strong “final design” you can present

You can summarize like this in an interview:

## Core design

* Use a **URL Shortening Service** and **Redirect Service**
* Store mappings in a **sharded key-value store or relational DB**
* Generate IDs using **Snowflake-style distributed IDs**, then **Base62 encode**
* Cache hot mappings in **Redis**
* Redirect requests check cache first, then DB on miss
* Return **302** initially
* Send click events asynchronously to **Kafka/queue**
* Support custom aliases with a uniqueness check
* Support expiration with `expire_at`
* Add rate limiting and Safe Browsing checks to prevent abuse

---

# 23. What interviewers may ask next

Be ready for these follow-ups:

* How do you prevent collisions?
* How many characters do you need?
* How do you make links hard to guess?
* How do you support custom aliases?
* What happens if Redis goes down?
* How do you handle a celebrity link getting billions of clicks?
* How would you support analytics without slowing redirects?
* Would you use SQL or NoSQL and why?
* How would you delete or expire links?
* How would you detect malicious URLs?

---

# 24. A clean interview answer flow

You can use this exact structure:

## 1. Clarify scope

“Do we only need shorten + redirect, or also custom alias, expiration, analytics?”

## 2. State assumptions

“I’ll optimize for a read-heavy, highly available service with low redirect latency.”

## 3. Define APIs and data model

Show `POST /urls` and `GET /{code}`.

## 4. Present baseline architecture

App servers + cache + DB + ID generator.

## 5. Dive into key challenge

Short code generation and uniqueness.

## 6. Add scale improvements

Redis, sharding, async analytics, multi-region reads.

## 7. Discuss tradeoffs

Base62, SQL/NoSQL, 301/302, random vs sequential IDs.

## 8. Close with extensions

Custom aliases, expiration, abuse prevention, dashboards.

---

# 25. Short sample answer you can say in an interview

> I’d first clarify whether the system only needs URL creation and redirection, or also custom aliases, expiration, and analytics.
> For the core system, I’d expose a create API and a redirect API. I’d store mappings from short code to long URL in a durable database, and cache hot mappings in Redis since this is a heavily read-dominant system.
> For short code generation, I’d use a distributed unique ID generator like Snowflake and Base62 encode the result, which avoids a central bottleneck and gives compact URLs.
> Redirect requests would hit cache first, fall back to DB on misses, and return a 302 redirect initially. Click analytics should be emitted asynchronously through a queue so they don’t impact redirect latency.
> At scale, I’d shard by short code hash, replicate across regions for reads, and add rate limiting, Safe Browsing checks, and alias uniqueness constraints. If needed, I’d extend the design for custom aliases, expiration, and analytics dashboards.

---

# 26. What makes a great answer vs average answer

## Average answer

* talks only about DB and hash map
* ignores scale
* ignores ID generation
* ignores analytics path
* ignores abuse

## Great answer

* asks good clarifying questions
* scopes the problem
* gives API + schema
* explains short-code generation carefully
* optimizes read path with cache
* makes analytics async
* discusses tradeoffs and abuse prevention

---

# 27. If you want a very practical template

Use this checklist every time:

## Interview checklist

* Requirements
* Scale assumptions
* API design
* Data model
* High-level architecture
* ID generation
* Cache strategy
* DB choice + partitioning
* Analytics
* Reliability
* Security/abuse
* Tradeoffs
* Extensions
