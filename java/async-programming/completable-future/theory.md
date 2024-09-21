# CompletableFuture in Java: Comprehensive Guide

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

## 3. Key Concepts and Best Practices

1. **Asynchronous Execution**: Use `supplyAsync()` or `runAsync()` to execute tasks asynchronously.

2. **Non-blocking Chains**: Prefer non-blocking methods like `thenApply()`, `thenAccept()`, and `thenRun()` over blocking `get()` calls.

3. **Composition over Nesting**: Use `thenCompose()` instead of nesting futures to maintain clean, readable code.

4. **Error Propagation**: Always handle exceptions, either with `exceptionally()` or `handle()`, to prevent silent failures.

5. **Thread Management**: Be aware of which thread pool your futures are running on. Use `asyncXXX` methods to control execution context.

6. **Cancellation**: CompletableFuture supports cancellation. Use `cancel(boolean mayInterruptIfRunning)` to attempt cancelling a running future.

7. **Timeouts**: Implement timeouts using `orTimeout()` or `completeOnTimeout()` to handle long-running operations.

8. **Testing**: Use `CompletableFuture.completedFuture()` for creating pre-completed futures in unit tests.

Remember, while CompletableFuture provides powerful tools for asynchronous programming, it's important to use them judiciously. Overuse can lead to complex, hard-to-debug code. Always strive for clarity and simplicity in your asynchronous workflows.
