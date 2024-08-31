# Java Threads: Comprehensive Notes

1. [Overview](#1-overview)

2. [Key Characteristics](#2-key-characteristics)

3. [Thread Creation](#3-thread-creation)

   3.1 [Extending the Thread class](#31-extending-the-thread-class)

   3.2 [Implementing the Runnable interface](#32-implementing-the-runnable-interface)

4. [Thread Methods](#4-thread-methods)
   - [start()](#start)
   - [run()](#run)
   - [sleep(long millis)](#sleeplong-millis)
   - [join()](#join)
   - [interrupt()](#interrupt)
   - [currentThread()](#currentthread)
   - [setName(String name) / getName()](#setnamestring-name--getname)
   - [setPriority(int priority) / getPriority()](#setpriorityint-priority--getpriority)
   - [isAlive()](#isalive)

5. [Thread States](#5-thread-states)

6. [Theory](#6-theory)
   [Threads and Concurrency](#threads-and-concurrency)

7. [Pitfalls and Edge Cases](#7-pitfalls-and-edge-cases)

8. [Best Practices](#8-best-practices)

## 1. Overview

Threads in Java are part of the `java.lang` package and are represented by the `Thread` class. They allow for concurrent execution within a Java program, enabling multiple tasks to run simultaneously and improving overall performance. Threads are fundamental to Java's concurrency model, providing a way to execute code in parallel on multi-core processors.

## 2. Key Characteristics

- Threads share the same memory space within a process
- Each thread has its own call stack
- Java provides built-in support for thread synchronization and communication
- Threads can be in various states throughout their lifecycle
- Threads allow for both concurrency (handling multiple tasks by switching between them) and potential parallelism (executing truly simultaneously on multi-core systems)

## 3. Thread Creation

There are two main approaches to creating threads in Java:

### 3.1 Extending the Thread class

```java
class MyThread extends Thread {
    public void run() {
        // Code to be executed in this thread
    }
}

MyThread thread = new MyThread();
thread.start();
```

### 3.2 Implementing the Runnable interface

```java
class MyRunnable implements Runnable {
    public void run() {
        // Code to be executed in this thread
    }
}

Thread thread = new Thread(new MyRunnable());
thread.start();
```

The Runnable interface is often preferred as it allows for better separation of task logic from thread management and doesn't prevent the class from extending other classes.

## 4. Thread Methods

### start()
Begins the execution of a thread. It performs necessary setup, calls the private native method `start0()`, which creates the new thread of execution, and then the JVM calls the `run()` method in the new thread context.

### run()
Contains the code to be executed in the thread. It's called automatically by the JVM after `start()` creates the new thread.

### sleep(long millis)
Causes the currently executing thread to pause for a specified number of milliseconds.

```java
try {
    Thread.sleep(1000); // Sleep for 1 second
} catch (InterruptedException e) {
    // Handle interruption
}
```

### join()
Waits for the thread to die. It's a blocking operation that causes the current thread to pause execution until the thread it's joined to completes.

```java
Thread thread = new Thread(() -> {
    // Some long running operation
});
thread.start();
thread.join(); // Wait for thread to complete
```

### interrupt()
Interrupts the thread. It sets the interrupted status flag of the thread.

```java
Thread thread = new Thread(() -> {
    while (!Thread.currentThread().isInterrupted()) {
        // Do work
    }
});
thread.start();
thread.interrupt();
```

### currentThread()
Returns a reference to the currently executing thread object.

```java
Thread currentThread = Thread.currentThread();
System.out.println("Current thread: " + currentThread.getName());
```

### setName(String name) / getName()
Sets or gets the name of the thread.

```java
Thread thread = new Thread(() -> {});
thread.setName("WorkerThread");
System.out.println(thread.getName()); // Outputs: WorkerThread
```

### setPriority(int priority) / getPriority()
Sets or gets the priority of the thread. Priority is between 1 (lowest) and 10 (highest).

```java
Thread thread = new Thread(() -> {});
thread.setPriority(Thread.MAX_PRIORITY);
System.out.println(thread.getPriority()); // Outputs: 10
```

### isAlive()
Tests if the thread is alive. A thread is alive if it has been started and has not yet died.

```java
Thread thread = new Thread(() -> {
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {}
});
thread.start();
System.out.println(thread.isAlive()); // Outputs: true
```

## 5. Thread States

1. **NEW**: The thread has been created but not yet started.
   ```java
   Thread thread = new Thread(() -> {});
   // thread is in NEW state here
   ```

2. **RUNNABLE**: The thread is executing or ready to execute when it gets CPU time.
   ```java
   thread.start();
   // thread is now in RUNNABLE state
   ```

3. **BLOCKED**: The thread is waiting to acquire a lock to enter or re-enter a synchronized block/method.
   ```java
   synchronized(object) {
       // If another thread holds the lock on 'object',
       // the current thread will be in BLOCKED state
   }
   ```

4. **WAITING**: The thread is waiting indefinitely for another thread to perform a particular action.
   ```java
   object.wait();
   // Thread is now in WAITING state
   ```

5. **TIMED_WAITING**: The thread is waiting for another thread to perform an action for up to a specified waiting time.
   ```java
   Thread.sleep(1000);
   // Thread is in TIMED_WAITING state for 1 second
   ```

6. **TERMINATED**: The thread has completed execution or has been terminated.
   ```java
   // After the run() method completes or an uncaught exception occurs
   // the thread will be in TERMINATED state
   ```

## 6. Theory

### Threads and Concurrency

_1. Concurrency: The ability to handle multiple tasks by switching between them_

_2. Parallelism: The ability to execute multiple tasks simultaneously_

- Primary purpose of threads is to provide concurrency
- Multiple threads can exist within a process, sharing resources
- The system rapidly switches between threads, giving the illusion of simultaneous execution
- Threads can achieve true parallelism on multi-core processors
- Different threads can run on different CPU cores simultaneously
- The JVM and operating system manage the distribution of threads across available cores

## 7. Pitfalls and Edge Cases

1. Calling `run()` instead of `start()`: No new thread is created.
2. Starting a thread multiple times: Throws `IllegalThreadStateException`.
3. Race conditions: When multiple threads access shared data concurrently.
4. Deadlocks: When two or more threads are blocked forever, waiting for each other.
5. Thread interference: Unexpected results due to interleaved operations of multiple threads.
6. Memory consistency errors: When different threads have inconsistent views of the same data.

## 8. Best Practices

1. Use `Runnable` over extending `Thread`:
   ```java
   Runnable task = () -> System.out.println("Task running");
   new Thread(task).start();
   ```

2. Use thread pools for better resource management:
   ```java
   ExecutorService executor = Executors.newFixedThreadPool(5);
   executor.submit(() -> System.out.println("Task running"));
   ```

3. Properly handle InterruptedException:
   ```java
   try {
       Thread.sleep(1000);
   } catch (InterruptedException e) {
       Thread.currentThread().interrupt(); // Restore interrupted status
   }
   ```

4. Use synchronized collections or concurrent collections for thread-safety:
   ```java
   List<String> list = Collections.synchronizedList(new ArrayList<>());
   // or
   List<String> concurrentList = new CopyOnWriteArrayList<>();
   ```

5. Use atomic classes for simple thread-safe operations:
   ```java
   AtomicInteger counter = new AtomicInteger(0);
   counter.incrementAndGet(); // Thread-safe increment
   ```

6. Use explicit locks for more flexible locking:
   ```java
   private final ReentrantLock lock = new ReentrantLock();
   public void someMethod() {
       lock.lock();
       try {
           // Critical section
       } finally {
           lock.unlock();
       }
   }
   ```

7. Prefer using higher-level concurrency utilities:
   ```java
   CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Result");
   future.thenAccept(System.out::println);
   ```

8. Always document thread-safety of your classes and methods.

9. Use thread-safe lazy initialization with double-checked locking:
   ```java
   private volatile Object instance;
   public Object getInstance() {
       if (instance == null) {
           synchronized (this) {
               if (instance == null) {
                   instance = new Object();
               }
           }
       }
       return instance;
   }
   ```

10. Use `java.util.concurrent.atomic` package for lock-free thread-safe programming:
    ```java
    AtomicReference<String> atomicRef = new AtomicReference<>("Initial");
    atomicRef.updateAndGet(s -> s + " Updated");
    ```
