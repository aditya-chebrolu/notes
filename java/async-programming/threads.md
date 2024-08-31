# Java Threads: Comprehensive Notes

## 1. Thread Basics

### Overview
- Threads in Java are part of the `java.lang` package
- Represented by the `Thread` class
- Allow concurrent execution within a Java program
- Improve performance by enabling multiple tasks to run simultaneously

### Key Characteristics
- Threads share the same memory space within a process
- Each thread has its own call stack
- Java provides built-in support for thread synchronization and communication

## 2. Creating Threads

### Two Main Approaches

#### 1. Extending the Thread class
```java
class MyThread extends Thread {
    public void run() {
        // Code to be executed in this thread
    }
}

MyThread thread = new MyThread();
thread.start();
```

#### 2. Implementing the Runnable interface
```java
class MyRunnable implements Runnable {
    public void run() {
        // Code to be executed in this thread
    }
}

Thread thread = new Thread(new MyRunnable());
thread.start();
```

### Notes on Runnable Interface
- Functional interface with a single method `run()`
- Allows better separation of task logic from thread management
- Preferred when you need to extend another class (Java doesn't support multiple inheritance)
- Can be implemented using lambda expressions in Java 8+:
  ```java
  Runnable task = () -> System.out.println("Running in a thread");
  new Thread(task).start();
  ```

## 3. Thread Lifecycle and States

### Thread States
1. **NEW**: Created but not started
2. **RUNNABLE**: Executing or ready to execute
3. **BLOCKED**: Waiting to acquire a lock
4. **WAITING**: Waiting indefinitely for another thread
5. **TIMED_WAITING**: Waiting for another thread (with timeout)
6. **TERMINATED**: Completed execution or terminated

### Notes on Thread States
- Use `thread.getState()` to get the current state
- RUNNABLE includes both running and ready-to-run threads
- BLOCKED, WAITING, and TIMED_WAITING are "non-runnable" states

## 4. Key Thread Methods

### start() Method
- Begins the execution of a thread
- Creates a new thread of execution
- Calls the private native method `start0()`
- `start0()` is responsible for creating the actual thread at the OS level
- Can only be called once per thread object

### run() Method
- Contains the code to be executed in the thread
- Called by the JVM after `start0()` creates the new thread
- Can be called directly, but this doesn't create a new thread

### Other Important Methods
- `sleep(long millis)`: Pauses thread execution
- `join()`: Waits for the thread to die
- `interrupt()`: Interrupts the thread

## 5. start() vs run(): Key Differences

### Calling start()
- Creates a new thread of execution
- `run()` is executed in this new thread
- Proper way to begin thread execution

### Calling run() Directly
- Does not create a new thread
- Executes in the current thread, like a normal method call
- Typically not what you want for concurrent execution

### Detailed start() Method Execution
1. Performs checks (e.g., if the thread has already been started)
2. Adds the thread to a thread group if necessary
3. Calls the private native method `start0()`
4. `start0()` creates the new thread of execution
5. JVM calls the `run()` method of the thread in the new thread context

### Example
```java
public class ThreadExample extends Thread {
    public void run() {
        System.out.println("Thread: " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        ThreadExample thread1 = new ThreadExample();
        ThreadExample thread2 = new ThreadExample();

        thread1.start(); // New thread
        thread2.run();   // Main thread

        System.out.println("Main: " + Thread.currentThread().getName());
    }
}
```

Output (may vary):
```
Thread: Thread-0
Thread: main
Main: main
```

## 6. Common Pitfalls and Edge Cases

1. **Calling run() instead of start()**
   - Executes in current thread, not a new one
   - No concurrency achieved

2. **Starting a thread multiple times**
   - Throws IllegalThreadStateException
   ```java
   Thread thread = new Thread(() -> System.out.println("Running"));
   thread.start();
   thread.start(); // Throws exception
   ```

3. **Interrupting a thread**
   - `interrupt()` only sets a flag, doesn't stop the thread
   - Interrupted thread must check and respond to interruption
   ```java
   Thread thread = new Thread(() -> {
       while (!Thread.currentThread().isInterrupted()) {
           // Do work
       }
   });
   thread.start();
   thread.interrupt();
   ```

4. **Overriding start()**
   - Generally not recommended
   - Can lead to unexpected behavior if not careful
   ```java
   public class BadThreadExample extends Thread {
       @Override
       public void start() {
           System.out.println("start() was called");
           // super.start() is not called, so no new thread is created
       }

       public void run() {
           System.out.println("run() was called");
       }
   }
   ```

## 7. Best Practices

1. Use Runnable interface over extending Thread when possible so that the current class can extend another class (as JAVA supports only single inheritance)
2. Always use `start()` to begin thread execution, not `run()`
3. Handle InterruptedException appropriately
4. Use thread pools (ExecutorService) for efficient thread management
5. Be cautious with shared resources; use proper synchronization
6. Avoid deprecated methods (stop(), suspend(), resume())
7. Utilize java.util.concurrent package for advanced concurrency tools
8. Document thread safety in your classes and methods
9. Consider using `java.util.concurrent.Callable` for tasks that return a result

## 8. Thread State Demonstration

```java
public class ThreadStateDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000); // TIMED_WAITING state
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // NEW state
        System.out.println("After creation: " + thread.getState());

        thread.start(); // RUNNABLE state
        System.out.println("After starting: " + thread.getState());

        Thread.sleep(1000);
        System.out.println("While sleeping: " + thread.getState());

        thread.join(); // Wait for thread to die
        System.out.println("After completion: " + thread.getState());
    }
}
```

This example demonstrates various thread states throughout a thread's lifecycle.
```

These comprehensive notes now include all the relevant information from our previous discussions, including the detailed explanation of the `start()` method's execution process, more examples, and a demonstration of thread states. The structure remains clear and easy to follow, progressing from basic concepts to more advanced topics and practical examples.
