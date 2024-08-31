# Future in Java

## Overview

- Part of `java.util.concurrent` package
- Introduced in Java 5
- Represents the result of an asynchronous computation

## Key Characteristics

- Generic interface: `Future<V>`
- Provides a way to access the result of a task that may not have completed yet

## Core Methods

1. `boolean cancel(boolean mayInterruptIfRunning)`

   Attempts to cancel the execution of the task.
   ```java
   Future<String> future = executor.submit(task);
   boolean canceled = future.cancel(true);
   ```

3. `boolean isCancelled()`

   Returns true if the task was cancelled before it completed normally.
   ```java
   if (future.isCancelled()) {
       System.out.println("Task was cancelled");
   }
   ```

5. `boolean isDone()`

   Returns true if the task completed (normally, exceptionally, or was cancelled).
   ```java
   if (future.isDone()) {
       System.out.println("Task is complete");
   }
   ```

7. `V get()` throws InterruptedException, ExecutionException

   Waits if necessary for the computation to complete, and then retrieves its result.
   ```java
   String result = future.get();
   ```

9. `V get(long timeout, TimeUnit unit)` throws InterruptedException, ExecutionException, TimeoutException

   Waits if necessary for at most the given time for the computation to complete, and then retrieves its result, if available.
   ```java
   try {
       String result = future.get(5, TimeUnit.SECONDS);
   } catch (TimeoutException e) {
       System.out.println("Task took too long");
   }
   ```

## Usage Example

```java
ExecutorService executor = Executors.newSingleThreadExecutor();
Future<Integer> future = executor.submit(() -> {
    Thread.sleep(2000);
    return 123;
});

try {
    Integer result = future.get(3, TimeUnit.SECONDS);
    System.out.println("Result: " + result);
} catch (InterruptedException | ExecutionException | TimeoutException e) {
    e.printStackTrace();
} finally {
    executor.shutdown();
}
```

## Common Use Cases

1. Performing time-consuming operations asynchronously
2. Improving application responsiveness
3. Executing multiple independent operations concurrently

## Limitations

- Basic `Future` doesn't allow chaining or combining of tasks
- No built-in support for callbacks or completion handlers
- `get()` method is blocking

## Edge Cases and Considerations

1. Blocking nature of `get()` method can lead to deadlocks if not used carefully
2. Exceptions during execution are wrapped in `ExecutionException`
3. Cancelling a Future doesn't guarantee immediate task termination
4. Proper exception handling is crucial
5. Uncompleted Futures can potentially cause memory leaks

## Best Practices

1. Always handle or propagate exceptions
2. Use timeouts with `get()` to avoid indefinite waiting
3. Properly manage and shut down `ExecutorService`
4. Consider using more advanced alternatives for complex scenarios
