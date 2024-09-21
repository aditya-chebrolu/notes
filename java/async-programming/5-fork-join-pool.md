# Fork/Join Framework in Java

## Overview
The **Fork/Join Framework** in Java is designed for parallel execution of tasks, allowing tasks to be divided into smaller subtasks (forking) and their results to be combined (joining). It efficiently utilizes multiple cores for parallel computation.

### Key Concepts:
- **Fork**: Split a large task into smaller subtasks.
- **Join**: Combine the results of subtasks to produce the final result.
- **Divide and Conquer**: The framework is based on the divide-and-conquer algorithm paradigm.

---

## ForkJoinPool
The `ForkJoinPool` is a specialized `ExecutorService` designed for managing and executing tasks in parallel. It uses a **work-stealing** algorithm, where idle worker threads can steal tasks from others, ensuring efficient use of available cores.

### Key Points:
- **Parallelism**: By default, the pool size is based on the number of available processors/cores.
- **Work-Stealing**: Threads steal tasks to minimize idle time and balance workload.
- **Usage**: Ideal for recursive, computationally intensive tasks that can be broken down into smaller units.
- **Common Pool**: Java 8 introduced a common ForkJoinPool that's used by parallel streams, making it easier to leverage parallelism without explicitly creating a pool.

### What Happens if More Threads are Instantiated than Available Cores?
If you create a `ForkJoinPool` with more threads than cores (e.g., 4 threads on a single-core CPU), the following happens:
- **Time-Slicing**: The operating system uses time-slicing to switch between threads since only one can run at a time.
- **Thread Context Switching**: This introduces overhead as the CPU must save and restore thread states, potentially degrading performance.
- **Work-Stealing Limited**: Work-stealing has limited benefits since only one thread can be active at a time.
- **Overhead Risks**: Managing multiple threads on a single core can result in slower performance compared to sequential execution due to the overhead of task forking and joining.
- **I/O-Bound vs CPU-Bound Tasks**: 
  - **CPU-bound tasks** (e.g., computations) won't benefit from extra threads on a single core.
  - **I/O-bound tasks** (e.g., file access) might see some benefit, as threads can work during I/O waits.

### Example:
```java
// Create a ForkJoinPool with default parallelism
ForkJoinPool pool = new ForkJoinPool();

// Or specify the parallelism level
// ForkJoinPool pool = new ForkJoinPool(4);

// Execute a task
Future<Result> result = pool.submit(new MyRecursiveTask());

// Or invoke directly
Result result = pool.invoke(new MyRecursiveTask());
```

---

## ForkJoinTask
`ForkJoinTask` is an abstract class representing tasks that can be split and executed by a `ForkJoinPool`. It provides methods:
- **`fork()`**: Submits a task for asynchronous execution.
- **`join()`**: Waits for the task to complete and retrieves its result (if applicable).
- **`invoke()`**: Starts the task and waits for its completion, combining fork() and join().

### Subclasses:
1. **RecursiveTask<V>**: Used when the task **returns a result**.
2. **RecursiveAction**: Used when the task **does not return a result**.
3. **CountedCompleter**: Used for tasks where completion triggers actions in parent tasks.

---

## RecursiveTask
- **Purpose**: For tasks that return a result after computation.
- **Usage**: When you need to combine results from multiple subtasks, e.g., calculating the sum of an array.

### Example:
```java
class SumTask extends RecursiveTask<Long> {
    private final long[] array;
    private final int start;
    private final int end;
    private static final int THRESHOLD = 1000;

    // Constructor

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            return computeDirectly();
        } else {
            int middle = (start + end) / 2;
            SumTask leftTask = new SumTask(array, start, middle);
            SumTask rightTask = new SumTask(array, middle, end);
            leftTask.fork();
            long rightResult = rightTask.compute();
            long leftResult = leftTask.join();
            return leftResult + rightResult;
        }
    }

    private long computeDirectly() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += array[i];
        }
        return sum;
    }
}
```

---

## RecursiveAction
- **Purpose**: For tasks that do **not return a result**.
- **Usage**: Used for operations like modifying data structures or performing side effects without returning any value.

### Example:
```java
class IncrementTask extends RecursiveAction {
    private final int[] array;
    private final int start;
    private final int end;
    private static final int THRESHOLD = 1000;

    // Constructor

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            incrementArray();
        } else {
            int middle = (start + end) / 2;
            IncrementTask leftTask = new IncrementTask(array, start, middle);
            IncrementTask rightTask = new IncrementTask(array, middle, end);
            invokeAll(leftTask, rightTask);
        }
    }

    private void incrementArray() {
        for (int i = start; i < end; i++) {
            array[i]++;
        }
    }
}
```

---

## RecursiveTask vs RecursiveAction

| **Feature**            | **RecursiveTask**                          | **RecursiveAction**                        |
|------------------------|--------------------------------------------|--------------------------------------------|
| **Return Value**        | Yes (generic type `V`)                     | No (void)                                  |
| **Use Case**            | Need to compute and return a result        | Perform actions with no return value       |
| **Example**             | Summing elements in an array               | Incrementing elements in an array          |
| **Combining Results**   | Requires explicit result combination       | No result combination needed               |

---

## Best Practices
1. **Task Granularity**: Choose an appropriate threshold for splitting tasks to balance parallelism and overhead.
2. **Avoid Synchronization**: Minimize synchronization between subtasks to maximize parallel execution.
3. **Use Common Pool Judiciously**: Be cautious when using the common ForkJoinPool for long-running tasks, as it may impact other parts of the application.
4. **Exception Handling**: Properly handle exceptions in tasks, as they may be executed on different threads.
5. **Profiling and Tuning**: Use profiling tools to optimize the parallelism level and task splitting strategy.
