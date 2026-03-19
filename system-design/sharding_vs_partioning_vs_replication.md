| Aspect                 | Replication                                 | Partitioning                                                  | Sharding                                                     |
| ---------------------- | ------------------------------------------- | ------------------------------------------------------------- | ------------------------------------------------------------ |
| **Definition**         | Copying the same data across multiple nodes | Splitting a dataset into smaller parts within the same system | Distributing partitions across multiple independent machines |
| **Goal**               | High availability & fault tolerance         | Improve performance & manageability                           | Horizontal scalability across servers                        |
| **Data Distribution**  | Same data is duplicated everywhere          | Data is divided into segments                                 | Data is divided and spread across nodes                      |
| **Storage Pattern**    | Redundant copies                            | Logical division (tables/files)                               | Physical distribution across servers                         |
| **Scalability**        | Limited (adds redundancy, not capacity)     | Moderate (within a system)                                    | High (adds more machines)                                    |
| **Fault Tolerance**    | High (failover to replicas)                 | Low (partition loss affects data)                             | Medium–High (depends on replication within shards)           |
| **Complexity**         | Low–Moderate                                | Moderate                                                      | High                                                         |
| **Query Performance**  | Faster reads (can read from replicas)       | Faster queries on smaller subsets                             | Faster if query hits one shard; slower if cross-shard        |
| **Consistency Issues** | Possible (replica lag)                      | Minimal                                                       | Possible (distributed consistency challenges)                |
| **Typical Use Case**   | Backup, read scaling                        | Large tables, indexing                                        | Massive distributed systems                                  |


```

                     ┌──────────────────────┐
                     │     App / Client     │
                     └─────────┬────────────┘
                               │
                     ┌─────────▼──────────┐
                     │   Router / Proxy   │
                     └─────────┬──────────┘
                               │
        ┌──────────────────────┼──────────────────────┐
        │                      │                      │
   ┌────▼────┐           ┌────▼────┐           ┌────▼────┐
   │ Shard 1 │           │ Shard 2 │           │ Shard 3 │   ← SHARDING
   └────┬────┘           └────┬────┘           └────┬────┘
        │                      │                      │
  ┌─────▼─────┐        ┌──────▼─────┐        ┌──────▼─────┐
  │ Primary   │        │ Primary    │        │ Primary    │
  │ (writes)  │        │ (writes)   │        │ (writes)   │
  └─────┬─────┘        └──────┬─────┘        └──────┬─────┘
        │                      │                      │
  ┌─────▼─────┐        ┌──────▼─────┐        ┌──────▼─────┐
  │ Replica   │        │ Replica    │        │ Replica    │
  │ (reads)   │        │ (reads)    │        │ (reads)    │  ← REPLICATION
  └───────────┘        └────────────┘        └────────────┘

   Inside each shard:
   ┌──────────────────────────────┐
   │ Orders Table Partitioned By: │
   │  Jan | Feb | Mar | Apr       │  ← PARTITIONING
   └──────────────────────────────┘
```
