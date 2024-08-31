# CompletableFuture in Java

## Overview

- Part of `java.util.concurrent` package
- Introduced in Java 8
- Represents a future that can be explicitly completed

## Key Characteristics

- Implements both `Future<T>` and `CompletionStage<T>` interfaces
- Provides a rich set of methods for composing, combining, and handling asynchronous computations

## Core Methods

1. `static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)`

   Creates a CompletableFuture that runs asynchronously using the default executor (ForkJoinPool.commonPool()).
   ```java
   CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
   ```
   
   `static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)`

   Overloaded version that allows specifying a custom executor.
   ```java
   ExecutorService executor = Executors.newFixedThreadPool(4);
   CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello", executor);
   ```

4. `<U> CompletableFuture<U> thenApply(Function<? super T,? extends U> fn)`

   Returns a new CompletableFuture that, when this CompletableFuture completes normally, is executed with this CompletableFuture's result as the argument to the supplied function.
   ```java
   CompletableFuture<Integer> lengthFuture = future.thenApply(s -> s.length());
   ```

6. `CompletableFuture<Void> thenAccept(Consumer<? super T> action)`

   Returns a new CompletableFuture that, when this CompletableFuture completes normally, is executed with this CompletableFuture's result as the argument to the supplied action.
   ```java
   future.thenAccept(s -> System.out.println(s));
   ```

8. `<U> CompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn)`

   Returns a new CompletableFuture that, when this CompletableFuture completes normally, is executed with this CompletableFuture's result as the argument to the supplied function.
   ```java
   CompletableFuture<String> greetingFuture = nameFuture.thenCompose(name -> getGreeting(name));
   ```

10. `CompletableFuture<T> exceptionally(Function<Throwable, ? extends T> fn)`
   Returns a new CompletableFuture that, when this CompletableFuture completes exceptionally, is executed with this CompletableFuture's exception as the argument to the supplied function.
   ```java
   CompletableFuture<String> safeFuture = future.exceptionally(ex -> "Error: " + ex.getMessage());
   ```

## Usage Example

```java
CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "World");

CompletableFuture<String> combinedFuture = future1.thenCombine(future2, (s1, s2) -> s1 + " " + s2);

combinedFuture.thenAccept(System.out::println);
```

## Common Use Cases

1. Chaining multiple asynchronous operations
2. Combining results from multiple independent futures
3. Handling exceptions in asynchronous computations
4. Implementing non-blocking, reactive programming patterns

## Advantages Over Basic Future

- Allows for chaining and combining of asynchronous tasks
- Provides built-in support for callbacks and completion handlers
- Offers both synchronous and asynchronous completion
- Extensive API for composing complex asynchronous logic

## Edge Cases and Considerations

1. Uncaught exceptions in asynchronous stages can lead to unhandled errors
2. Improper use of `join()` or `get()` methods can still lead to blocking behavior
3. Overuse of deeply nested completable futures can lead to callback hell
4. Memory leaks possible if long chains of futures are not properly managed
5. `supplyAsync()` always uses an executor, even when not explicitly provided

## Best Practices

1. Use exception handling methods like `exceptionally()` or `handle()` to manage errors
2. Prefer non-blocking methods (e.g., `thenAccept()`) over blocking ones (e.g., `get()`)
3. Use `thenCompose()` for flat-mapping futures to avoid nested futures
4. Utilize `CompletableFuture.allOf()` for coordinating multiple independent futures
5. Consider using a custom executor for better control over thread allocation
6. Be aware that `supplyAsync()` uses `ForkJoinPool.commonPool()` by default, which may not be ideal for all scenarios

These updated notes now include the clarification about `supplyAsync()` and its use of executors, providing a more comprehensive overview of CompletableFuture in Java.
