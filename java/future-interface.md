# Java Future Interface Notes

## Overview
- Part of `java.util.concurrent` package
- Represents result of asynchronous computation

## Methods

### 1. `get()`
- Retrieves the result, blocking if not ready
- Throws exceptions: InterruptedException, ExecutionException

```java
Future<String> future = executorService.submit(callableTask);
String result = future.get();
```

### 2. `get(long timeout, TimeUnit unit)`
- Retrieves result with a timeout
- Throws TimeoutException if the wait time elapses

```java
String result = future.get(5, TimeUnit.SECONDS);
```

### 3. `isDone()`
- Checks if task is completed

```java
if (future.isDone()) {
    System.out.println("Task completed");
}
```

### 4. `cancel(boolean mayInterruptIfRunning)`
- Attempts to cancel execution
- Returns boolean indicating if cancellation was successful

```java
boolean cancelled = future.cancel(true);
```

### 5. `isCancelled()`
- Checks if task was cancelled

```java
if (future.isCancelled()) {
    System.out.println("Task was cancelled");
}
```

## Usage Example

```java
import java.util.concurrent.*;

public class FutureExample {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        Future<Integer> future = executor.submit(() -> {
            Thread.sleep(2000);
            return 42;
        });
        
        System.out.println("Future created, waiting for result...");
        
        while (!future.isDone()) {
            System.out.println("Task still in progress...");
            Thread.sleep(500);
        }
        
        Integer result = future.get();
        System.out.println("Result: " + result);
        
        executor.shutdown();
    }
}
```

## Common Patterns

1. **Polling**
```java
while (!future.isDone()) {
    // Do other work
}
```

2. **Timeout handling**
```java
try {
    result = future.get(5, TimeUnit.SECONDS);
} catch (TimeoutException e) {
    future.cancel(true);
    // Handle timeout
}
```

3. **Exception handling**
```java
try {
    result = future.get();
} catch (InterruptedException e) {
    // Handle interruption
} catch (ExecutionException e) {
    // Handle exception from task
}
```

## Limitations
- Cannot chain operations
- No built-in callback mechanism
- Consider CompletableFuture for advanced async operations
