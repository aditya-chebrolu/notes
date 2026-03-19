| Approach                                                       | How it works                                                                | Best when to use                                                        | Advantages                                                                            | Disadvantages / Pitfalls                                                                                      | Example                                |
| -------------------------------------------------------------- | --------------------------------------------------------------------------- | ----------------------------------------------------------------------- | ------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------- | -------------------------------------- |
| **Auto-increment ID + Base62 encode**                          | Generate numeric ID from DB sequence, convert to Base62                     | Most common interview answer; simple, centralized systems               | Very simple, deterministic, short codes, easy to debug, no collisions if ID unique    | Predictable URLs, easier enumeration, DB sequence can become bottleneck, harder in multi-region active-active | `12345 -> dnh`                         |
| **Distributed ID generator (Snowflake-style) + Base62 encode** | Generate globally unique numeric IDs across machines, then Base62 encode    | Large-scale distributed systems needing high write throughput           | No central DB bottleneck for ID generation, sortable IDs, scalable across nodes       | More complex, clock/time dependency, machine ID coordination needed, still somewhat predictable               | `snowflake_id -> K8sd2x`               |
| **Random string generation**                                   | Pick random Base62 string directly and check if unused                      | When you want unguessable short URLs and can tolerate collision checks  | Harder to predict, no sequence leakage, easy to generate anywhere                     | Collision handling required, retries increase with higher utilization, harder capacity planning               | `aZ91xQ`                               |
| **Random string + retry on collision**                         | Same as above, but explicitly retry until unique                            | Good default for moderate scale with security/guessability concerns     | Simple, decentralized, hides traffic/order information                                | Write path may need DB/cache uniqueness check, collisions become operational concern at scale                 | Try `xY12ab`, if taken try again       |
| **Hash of long URL (MD5/SHA) truncated**                       | Hash original URL, take first N chars                                       | When you want same long URL to map to same short code deterministically | Same input gives same output, good for deduplication, no separate ID generator needed | Truncation can collide, difficult custom alias handling, changing code length later is messy                  | `hash(url)[:7]`                        |
| **Hash + collision resolution**                                | Hash URL, on collision append salt/counter and rehash                       | Deduplication-friendly systems with deterministic leaning               | More robust than plain truncated hash, still mostly deterministic                     | Added complexity, collision resolution logic needed, slightly less intuitive                                  | `hash(url+1)`, `hash(url+2)`           |
| **Full hash stored as key, short alias derived**               | Store full hash internally, expose shortened version                        | When internal uniqueness matters more than exposed code simplicity      | Strong uniqueness internally, better collision safety                                 | Still need exposed short-code strategy, storage overhead higher                                               | Internal SHA-256, external short token |
| **Pre-generated key pool**                                     | Generate short codes beforehand and assign from free pool                   | Very high write traffic where request latency must stay low             | Fast assignment, can validate/reserve codes in advance, operational control           | Need pool refill logic, wasted unused keys possible, inventory management complexity                          | Pool contains `ab12Cd`, `Qw91Er`       |
| **Range allocation + local Base62 encoding**                   | Central service gives ID ranges to app servers, each server encodes locally | When DB sequence is bottleneck but full Snowflake is overkill           | Less coordination than per-request central ID generation, scalable writes             | Range wastage on crashes/restarts, some operational complexity                                                | Server A gets 1M–2M                    |
| **Custom alias (user-provided)**                               | User chooses code if available                                              | Premium/custom short links, branded use cases                           | Human-friendly, memorable, marketing-friendly                                         | Need profanity/reserved-word checks, conflicts, abuse/spam risk                                               | `/openai`, `/sale2026`                 |
| **Keyword / semantic code generation**                         | Generate readable code from title/words, maybe with suffix                  | Marketing or user-facing branded links                                  | More memorable than random codes                                                      | High collision probability, privacy leakage, not great for scale alone                                        | `/summer-sale-7`                       |
| **Encrypted / obfuscated ID**                                  | Take sequential ID, encrypt or permute before encoding                      | When you want sequence simplicity without obvious predictability        | Hides raw order/volume better than plain Base62 ID                                    | Reversible scheme complexity, key management, not fully “random”                                              | `ID -> obfuscate -> Base62`            |




| Situation                                                          | Best choice                            |
| ------------------------------------------------------------------ | -------------------------------------- |
| **Interview default / easiest to explain**                         | **Auto-increment ID + Base62**         |
| **Huge distributed scale**                                         | **Snowflake-style ID + Base62**        |
| **Need unguessable public links**                                  | **Random string + retry on collision** |
| **Want same URL to usually get same short code**                   | **Hash + collision resolution**        |
| **Need ultra-fast writes with prepared inventory**                 | **Pre-generated key pool**             |
| **Need user-friendly branded links**                               | **Custom alias**                       |
| **Want simple system but less predictability than sequential IDs** | **Obfuscated/encrypted ID**            |


| Method                   | Use when              | Pros                    | Cons                       |
| ------------------------ | --------------------- | ----------------------- | -------------------------- |
| **Auto-ID + Base62**     | Simple system         | Easy, no collisions     | Predictable, DB bottleneck |
| **Snowflake + Base62**   | Distributed scale     | Scalable, no central DB | Complex, time sync issues  |
| **Random string**        | Need unguessable URLs | Secure, simple          | Collisions, retries        |
| **Hash (truncated)**     | Same URL → same code  | Deterministic           | Collisions, inflexible     |
| **Hash + collision fix** | Dedup + safer         | More robust hashing     | Added complexity           |
| **Pre-generated pool**   | Low latency writes    | Fast allocation         | Pool management            |
| **Custom alias**         | Branding use-case     | User-friendly           | Conflicts, abuse risk      |
| **Obfuscated ID**        | Hide sequence         | Safer than auto-ID      | Reversible complexity      |


| Scale / design maturity      | Common answer                                                                    |
| ---------------------------- | -------------------------------------------------------------------------------- |
| Basic URL shortener          | Auto-increment ID + Base62                                                       |
| Mid/large scale              | Snowflake ID + Base62                                                            |
| Security-focused public URLs | Random Base62                                                                    |
| Advanced product design      | Mix of **random/custom alias** externally and **internal ID mapping** internally |


| Method                 | Example flow                       | When to think of it   | Key idea                   |
| ---------------------- | ---------------------------------- | --------------------- | -------------------------- |
| **Auto-ID + Base62**   | `ID: 125 → Base62 → "cb"`          | Basic system          | Sequential → encode        |
| **Snowflake + Base62** | `ID: 839274982734 → "Kx9Ab"`       | Distributed system    | Unique IDs across machines |
| **Random string**      | Generate `"aZ91xQ"` directly       | Need security         | Pure random                |
| **Random + retry**     | `"abc123"` exists → try `"xYz789"` | Real-world random     | Retry on collision         |
| **Hash (truncated)**   | `hash(url) → "a94xK2"`             | Same URL → same code  | Deterministic              |
| **Hash + salt**        | `hash(url+1) → new code`           | Handle hash collision | Adjust input               |
| **Pre-generated pool** | Pick `"Qw91Er"` from pool          | High traffic writes   | Pre-create keys            |
| **Custom alias**       | User enters `"myblog"`             | Branding              | User-defined               |
| **Obfuscated ID**      | `ID 125 → encrypt → "XyZ91"`       | Hide sequence         | Encode + scramble          |

| Method                 | Example flow             | Use when              | Pros                    | Cons                       |
| ---------------------- | ------------------------ | --------------------- | ----------------------- | -------------------------- |
| **Auto-ID + Base62**   | `125 → "cb"`             | Simple system         | Easy, no collisions     | Predictable, DB bottleneck |
| **Snowflake + Base62** | `839274982734 → "Kx9Ab"` | Distributed scale     | Scalable, no central DB | Complex, time sync issues  |
| **Random string**      | `"aZ91xQ"`               | Need unguessable URLs | Secure, simple          | Collisions, retries        |
| **Random + retry**     | `"abc123" ❌ → "xYz789"`  | Real-world random     | Decentralized, flexible | Retry overhead             |
| **Hash (truncated)**   | `hash(url) → "a94xK2"`   | Same URL → same code  | Deterministic           | Collisions, inflexible     |
| **Hash + salt**        | `hash(url+1) → new`      | Handle collisions     | Safer hashing           | More complexity            |
| **Pre-generated pool** | Pick `"Qw91Er"`          | Low latency writes    | Fast allocation         | Pool management            |
| **Custom alias**       | `/myblog`                | Branding use-case     | User-friendly           | Conflicts, abuse risk      |
| **Obfuscated ID**      | `125 → "XyZ91"`          | Hide sequence         | Safer than auto-ID      | Reversible complexity      |


| Strategy                   | Who gives the ID                        | 1 Server + 1 DB (simple world)     | N Servers + 1 DB (traffic grows)                           | 1 Server + N DBs (data split)             | N Servers + N DBs (fully distributed) | What’s really happening                        | Verdict                             |
| -------------------------- | --------------------------------------- | ---------------------------------- | ---------------------------------------------------------- | ----------------------------------------- | ------------------------------------- | ---------------------------------------------- | ----------------------------------- |
| **DB Auto Increment**      | DB is the “ticket counter”              | S1 → DB → “next ID = 101” ✅ smooth | S1, S2, S3 all queue at DB → “give next ID” ⚠️ crowd at DB | DB1 & DB2 both say “101” ❌ collision risk | Chaos unless coordinated ❌            | Everyone stands in one line for IDs            | Great start, bad at scale           |
| **Single Counter Service** | One central “ID server”                 | S1 → ID service → 101 ✅            | S1, S2, S3 → all hit ID service ⚠️ queue shifts here       | Works (DB doesn’t matter)                 | Still one logical counter ⚠️          | You moved the queue from DB → service          | Better separation, still bottleneck |
| **DB Range Allocation**    | DB gives *bundles*, server uses locally | S1 gets 1–1000, uses locally ✅     | S1 → (1–1000), S2 → (1001–2000) 🚀 less contention         | Works if ranges are globally managed      | Works well if ranges don’t overlap    | Fewer trips to “ticket counter”                | Very practical scaling solution     |
| **Snowflake**              | Each server generates its own ID        | S1 makes ID from time+machine ✅    | S1, S2, S3 all generate IDs independently 🚀               | Works perfectly                           | Works perfectly 🚀                    | No central counter at all                      | Best distributed solution           |
| **Random String**          | Each server guesses a code              | S1 generates “aZ91x” → check DB ✅  | S1, S2 may generate same code ⚠️ retry needed              | Hard to check globally ⚠️                 | More collisions / coordination ❌      | “Try your luck, retry if clash”                | Simple, but messy at scale          |
| **Hash of URL**            | Server computes hash                    | “google.com” → fixed code ✅        | Same everywhere ✅                                          | Same everywhere ✅                         | Same everywhere ✅                     | Same input → same output                       | Good only if you want dedup         |
| **Timestamp + Random**     | Server uses time + randomness           | S1 → (time + rand) ✅               | S1, S2 generate mostly unique ⚠️ rare collisions           | Works                                     | Works                                 | “Mostly unique” IDs                            | Lightweight but not strongest       |
| **UUID**                   | Server generates globally unique ID     | S1 generates UUID ✅                | All servers generate safely 🚀                             | Works perfectly                           | Works perfectly 🚀                    | No coordination at all                         | Very safe, but long IDs             |
| **Shard Prefix + Counter** | Each shard has its own mini counter     | S1 → A1, A2 ✅                      | S1=A*, S2=B*, S3=C* 🚀                                     | DB1=A*, DB2=B*                            | Works great 🚀                        | Multiple small queues instead of one big queue | Strong, but ops complexity          |


