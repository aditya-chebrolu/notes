# Redis — How It Works (Concise Technical Notes)

---

## 1. What Redis Is

- In-memory data structure store
- Runs as a separate server process
- Primary use cases:
  - Caching
  - Sessions
  - Rate limiting
  - Queues
  - Counters
  - Leaderboards
- Usually not the system of record

---

## 2. High-Level Architecture

### Machine View
```
Machine
├── CPU
├── RAM
│   └── Redis Process
│       └── Dataset (stored entirely in RAM)
└── Disk
    └── Optional persistence files (RDB / AOF)
```
Key idea:
- Active dataset lives in RAM.
- Disk is used only for persistence (optional).

---

## 3. Core Data Model

Redis behaves like a giant in-memory dictionary:

`dict[key] → value`

Internally:
- Main hash table maps keys → pointers to objects
- Objects stored in RAM

Supported data types:
- String
- Hash
- List
- Set
- Sorted Set
- Stream
- Bitmap
- HyperLogLog

Properties:
- Most operations are O(1)
- Direct memory lookup
- No SQL parsing or query planner

---

## 4. Request Flow
```
Client
  ↓
API Server
  ↓ (TCP)
Redis
  ↓
Hash lookup in RAM
  ↓
Return result
```
Work per request:
- Parse command
- Lookup key
- Return value

No joins, no MVCC, no query planning.

---

## 5. Why Redis Is Fast

### A. RAM-Based Access

- RAM access: ~100 nanoseconds
- SSD access: ~100 microseconds
- RAM ≈ 1000x faster than disk

Reads do not require disk access.

---

### B. Simple Execution Model

- Single-threaded event loop
- Executes one command at a time
- No internal locks
- No deadlocks
- Minimal context switching

Commands are small and atomic.

---

### C. Minimal Overhead

Redis does NOT:
- Parse SQL
- Build execution plans
- Handle joins
- Manage complex index trees
- Perform MVCC visibility checks

Less abstraction → lower latency.

---

## 6. Persistence Options

Redis is memory-first but supports durability.

### A. RDB (Snapshots)

- Periodic snapshot of RAM → disk
- On restart, load snapshot

Pros:
- Compact
- Faster restarts

Cons:
- Data loss between snapshots

---

### B. AOF (Append Only File)

- Every write appended to log
- On restart, replay log to rebuild memory

Durability modes:
- fsync every write (safest, slowest)
- fsync every second (balanced, common)
- no fsync (fastest, risky)

---

## 7. Crash Behavior

No persistence:
- All data lost

RDB:
- Restores last snapshot
- Some recent writes lost

AOF:
- Replays write log
- Minimal data loss depending on fsync policy

---

## 8. Memory Management & Eviction

RAM is finite.

When maxmemory reached:

Option 1 — No eviction:
- Writes fail

Option 2 — LRU eviction:
- Remove least recently used keys

Option 3 — TTL expiration:
- Keys auto-expire after configured time

Example:
    SET key value EX 60

Common for:
- Sessions
- Cache entries

---

## 9. Redis vs Postgres (Conceptual Difference)

### Redis
```
CPU
 ↓
RAM (primary database)
 ↓
Optional disk persistence

Optimized for:
- Speed
- Atomic operations
- Caching
- Short-lived data
```
---

### Postgres
```
CPU
 ↓
RAM (buffer cache)
 ↓
Disk (primary storage)

Optimized for:
- Durability
- Transactions
- Complex queries
- Relational integrity
```
---

## 10. Typical Production Pattern (Cache-Aside)

Read Flow:
1. Check Redis
2. If hit → return
3. If miss → query Postgres
4. Store result in Redis

Write Flow:
1. Write to Postgres
2. Invalidate or update Redis

Redis reduces load on primary database.

---

## 11. Concurrency Model

- Single-threaded command execution
- I/O multiplexing for connections
- Commands are atomic by default

Benefits:
- No locking overhead
- Predictable latency
- High throughput for small operations

---

## 12. When Redis Is a Good Fit

✔ Caching expensive queries  
✔ Rate limiting  
✔ Session storage  
✔ Counters  
✔ Leaderboards  
✔ Distributed locks  
✔ Pub/Sub  
✔ Real-time analytics  

---

## 13. When Redis Is NOT Ideal

✖ Complex relational joins  
✖ Heavy analytics  
✖ Very large datasets exceeding RAM budget  
✖ Long-term durable storage without careful persistence config  

---

## 14. Mental Model Summary

Redis =
    Ultra-fast in-memory dictionary server

Postgres =
    Durable relational query engine

Redis trades:
- Deep durability
- Rich query capabilities

For:
- Extreme speed
- Low latency
- Simplicity
