# CompletableFuture in Java: Comprehensive Guide

1. [Method Comparisons (Java vs JavaScript)](#1-method-comparisons-java-vs-javascript)
2. [Java-Specific Examples and Explanations](#2-java-specific-examples-and-explanations)
   - [Creating CompletableFutures](#21-creating-completablefutures)
   - [Chaining Operations](#22-chaining-operations)
   - [Combining Futures](#23-combining-futures)
   - [Composing Futures](#24-composing-futures)
   - [Error Handling](#25-error-handling)
   - [Timeouts](#26-timeouts)
   - [Waiting for Multiple Futures](#27-waiting-for-multiple-futures)
3. [Execution Scenarios and Threading Behavior](#3-execution-scenarios-and-threading-behavior)
   - [Sequential Execution](#31-sequential-execution)
   - [Controlling Execution Threads](#32-controlling-execution-threads)
   - [Combining vs. Composing vs. Chaining](#33-combining-vs-composing-vs-chaining)
   - [Parallel Execution](#34-parallel-execution)
4. [Best Practices and Considerations](#4-best-practices-and-considerations)

## 1. Method Comparisons (Java vs JavaScript)

| JavaScript (Promise) | Java (CompletableFuture) | Description |
|----------------------|--------------------------|-------------|
| `Promise.resolve()` | `CompletableFuture.completedFuture()` | Creates a resolved future with a given value |
| `Promise.reject()` | `CompletableFuture.failedFuture()` | Creates a failed future with a given exception |
| `Promise.all()` | `CompletableFuture.allOf()` | Waits for all futures to complete |
| `Promise.any()` | `CompletableFuture.anyOf()` | Completes when any future completes |
| `Promise.race()` | `CompletableFuture.anyOf()` | Completes when the first future settles |
| `then()` | `thenApply()`, `thenAccept()`, `thenRun()` | Chains asynchronous operations |
| `catch()` | `exceptionally()` | Handles errors in the chain |
| `finally()` | `whenComplete()` | Executes code regardless of success or failure |

## 2. Java-Specific Examples and Explanations

### 2.1 Creating CompletableFutures

```java
// Completed future
CompletableFuture<String> completedFuture = CompletableFuture.completedFuture("Hello");

// Failed future
CompletableFuture<String> failedFuture = CompletableFuture.failedFuture(new Exception("Oops"));

// Asynchronous future
CompletableFuture<String> asyncFuture = CompletableFuture.supplyAsync(() -> {
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    return "Hello, Async World!";
});
```

### 2.2 Chaining Operations

```java
CompletableFuture.supplyAsync(() -> "Hello")
    .thenApply(s -> s + " World")
    .thenApply(String::toUpperCase)
    .thenAccept(System.out::println);
```

Key methods:
- `thenApply()`: Transforms the result of the previous stage.
- `thenAccept()`: Consumes the result without returning a value.
- `thenRun()`: Runs an action that doesn't depend on the previous result.

### 2.3 Combining Futures

```java
CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "World");

CompletableFuture<String> combined = future1.thenCombine(future2, (s1, s2) -> s1 + " " + s2);
combined.thenAccept(System.out::println);
```

`thenCombine()` allows you to combine the results of two independent futures.

### 2.4 Composing Futures

```java
CompletableFuture<User> fetchUser(int id) {
    return CompletableFuture.supplyAsync(() -> new User(id, "John"));
}

CompletableFuture<List<Post>> fetchPosts(User user) {
    return CompletableFuture.supplyAsync(() -> Arrays.asList(new Post("Post 1"), new Post("Post 2")));
}

CompletableFuture<List<Post>> userPosts = fetchUser(1)
    .thenCompose(this::fetchPosts);
```

`thenCompose()` is used for chaining dependent futures, where the next operation depends on the result of the previous one.

### 2.5 Error Handling

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    if (Math.random() < 0.5) throw new RuntimeException("Oops");
    return "Success";
})
.exceptionally(ex -> "Error: " + ex.getMessage())
.handle((result, ex) -> {
    if (ex != null) return "Handled: " + ex.getMessage();
    return "Result: " + result;
});
```

- `exceptionally()`: Handles exceptions and provides a fallback value.
- `handle()`: Allows handling both the success and failure cases.

### 2.6 Timeouts

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    return "Result";
})
.orTimeout(1, TimeUnit.SECONDS)
.exceptionally(ex -> "Timeout occurred");
```

`orTimeout()` allows setting a timeout for the future's completion.

### 2.7 Waiting for Multiple Futures

```java
CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Result 1");
CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Result 2");
CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "Result 3");

CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2, future3);
allOf.thenRun(() -> {
    try {
        System.out.println(future1.get());
        System.out.println(future2.get());
        System.out.println(future3.get());
    } catch (Exception e) {
        e.printStackTrace();
    }
});
```

`allOf()` waits for all specified futures to complete.





## 3. Execution Scenarios and Threading Behavior

### 3.1 Sequential Execution

CompletableFuture allows for sequential execution of tasks. However, it's important to understand how this affects threading:

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    System.out.println("First task: " + Thread.currentThread().getName());
    return "Result from first";
}).thenApply(result -> {
    System.out.println("Second task: " + Thread.currentThread().getName());
    return result + " -> processed";
}).thenApply(result -> {
    System.out.println("Third task: " + Thread.currentThread().getName());
    return result + " -> finalized";
});

System.out.println("Final result: " + future.get());
```

**Scenario:**
- The tasks are executed sequentially.
- By default, they may run on the same thread or different threads from the common ForkJoinPool.
- The thread usage depends on the availability in the pool and the complexity of each task.

### 3.2 Controlling Execution Threads

You can control which thread executes each stage:

```java
ExecutorService executor = Executors.newFixedThreadPool(3);

CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    System.out.println("First task: " + Thread.currentThread().getName());
    return "Result";
}, executor).thenApplyAsync(result -> {
    System.out.println("Second task: " + Thread.currentThread().getName());
    return result + " -> processed";
}, executor).thenApplyAsync(result -> {
    System.out.println("Third task: " + Thread.currentThread().getName());
    return result + " -> finalized";
}, executor);

System.out.println("Final result: " + future.get());
executor.shutdown();
```

**Scenario:**
- Using `*Async` methods with a custom Executor ensures each stage runs on a thread from that Executor.
- This gives you more control over thread allocation and can be useful for managing system resources.

### 3.3 Combining vs. Composing vs. Chaining

Understanding the differences in execution patterns:

```java
CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
    sleep(1000);
    return "Hello";
});

CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
    sleep(1000);
    return "World";
});

// Combining
CompletableFuture<String> combined = future1.thenCombine(future2, (s1, s2) -> s1 + " " + s2);

// Composing
CompletableFuture<String> composed = future1.thenCompose(s1 -> 
    CompletableFuture.supplyAsync(() -> s1 + " Composed"));

// Chaining
CompletableFuture<String> chained = future1.thenApply(s -> s + " Chained");

System.out.println("Combined: " + combined.get());
System.out.println("Composed: " + composed.get());
System.out.println("Chained: " + chained.get());
```

**Scenarios:**
1. **Combining (thenCombine)**:
   - Both `future1` and `future2` execute independently and potentially concurrently.
   - The combining function runs after both futures complete.
   - Useful for aggregating results from independent operations.

2. **Composing (thenCompose)**:
   - The second operation starts only after the first one completes.
   - Allows for dependent future operations.
   - Each stage can run on a different thread.

3. **Chaining (thenApply)**:
   - Transforms the result of the previous stage.
   - Executes sequentially, potentially on the same thread as the previous stage.
   - Best for simple transformations that don't require new async operations.

### 3.4 Parallel Execution

Executing multiple futures in parallel:

```java
List<CompletableFuture<String>> futures = Arrays.asList(
    CompletableFuture.supplyAsync(() -> {
        sleep(1000);
        return "Result 1";
    }),
    CompletableFuture.supplyAsync(() -> {
        sleep(1500);
        return "Result 2";
    }),
    CompletableFuture.supplyAsync(() -> {
        sleep(500);
        return "Result 3";
    })
);

CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

allOf.thenRun(() -> {
    futures.forEach(f -> {
        try {
            System.out.println(f.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    });
});

allOf.get(); // Wait for all to complete
```

**Scenario:**
- All futures start execution in parallel.
- They may run on different threads from the common ForkJoinPool.
- `CompletableFuture.allOf()` allows waiting for all futures to complete.
- The order of completion may differ from the order of creation.

## 4. Best Practices and Considerations

1. **Thread Pool Management**: 
   - Be cautious when using `*Async` methods without a custom executor. They use the common ForkJoinPool, which might get saturated in high-load scenarios.
   - Consider using a custom ExecutorService for better control over thread allocation.

2. **Avoiding Blocking Operations**: 
   - In `thenApply()` or similar synchronous continuations, avoid long-running or blocking operations. Use `thenApplyAsync()` for such cases.

3. **Exception Handling in Parallel Executions**:
   - When using `allOf()`, exceptions in individual futures don't propagate automatically. Always check each future's completion status.

4. **Deadlock Prevention**:
   - Be careful when composing futures that depend on each other. Improper composition can lead to deadlocks.

5. **Resource Management**:
   - Always shut down custom ExecutorServices to prevent resource leaks.

6. **Testing Asynchronous Code**:
   - Use `CompletableFuture.completedFuture()` for creating pre-completed futures in unit tests.
   - Consider using awaitility or similar libraries for testing asynchronous operations.

By understanding these execution scenarios and best practices, you can effectively leverage CompletableFuture for complex asynchronous workflows while maintaining performance and code clarity.
