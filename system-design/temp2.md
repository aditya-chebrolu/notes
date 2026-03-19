Got it — here’s a clean **chart-style (table + flow)** version that’s great for interviews and quick revision 👇

---

# URL Shortener – One-Page Cheat Sheet

## 🔹 1. Functional Flow

```
User → API Server → Generate Short Code → Store in DB → Return Short URL

User → Short URL → Cache (Redis)
                      ↓ (miss)
                    Database
                      ↓
                 Redirect (301/302)
                      ↓
              Async Analytics Queue → Analytics DB
```

---

## 🔹 2. Functional Requirements (Chart)

| Feature                 | Requirement         | Solution Options                                         |
| ----------------------- | ------------------- | -------------------------------------------------------- |
| Shorten URL             | Accept long URL     | `POST /shorten`                                          |
|                         | Generate short code | 1. DB ID + Base62<br>2. Random string<br>3. Snowflake ID |
|                         | Store mapping       | DB (`short_code → long_url`)                             |
| Redirect                | Fast lookup         | Redis cache + DB fallback                                |
|                         | Redirect user       | HTTP 301 / 302                                           |
| Analytics (Optional)    | Track clicks        | Kafka / MQ                                               |
|                         | Store analytics     | Analytics DB                                             |
| Custom Alias (Optional) | User-defined code   | DB unique constraint                                     |

---

## 🔹 3. Non-Functional Requirements (Chart)

| Category     | Problem                    | Solution                              |
| ------------ | -------------------------- | ------------------------------------- |
| Latency      | DB lookup is slow          | Redis cache, hot URL caching          |
| Availability | Server failure             | Load balancer + multiple app servers  |
|              |                            | DB replication, Redis replication     |
| Read-heavy   | More redirects than writes | Redis, read replicas, CDN             |
| Scalability  | Growing traffic/data       | Stateless servers, DB sharding        |
|              |                            | Distributed ID generation             |
| Consistency  | Duplicate codes            | Unique constraint, retry on collision |
| Reliability  | Data loss risk             | Persistent DB, backups, failover      |

---

## 🔹 4. Tech Stack Mapping (Quick View)

| Layer         | Tech                           |
| ------------- | ------------------------------ |
| API Layer     | Node.js / Java / Python        |
| Cache         | Redis                          |
| DB            | MySQL / PostgreSQL / NoSQL     |
| ID Generation | Base62 / Snowflake             |
| Queue         | Kafka / RabbitMQ               |
| Analytics     | OLAP DB (BigQuery, ClickHouse) |
| Infra         | Load Balancer + CDN            |

---

## 🔹 5. Key Design Insights (Interview Gold)

* **Read-heavy system → optimize reads (Redis + CDN)**
* **Cache-first, DB fallback**
* **Async everything non-critical (analytics)**
* **Avoid collisions → deterministic or checked generation**
* **Stateless servers → horizontal scaling**

---

If you want, I can also convert this into a **“how to explain in 2 minutes” script** or a **deep-dive architecture (HLD + LLD)**.
