package async_programming;
import java.util.concurrent.*;

public class CompletableFutureExample {
    public static void main(String[] args) throws Exception {
        ex7();
    }

    // creating futures
    private static void ex1() throws Exception{
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            return "Hello Universe";
        });

        System.out.println("waiting");
        String res = future.get();
        System.out.println(res);
    }

    // chaining futures
    private static void ex2() throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(s -> s + ", CompletableFuture!")
                .thenApply(String::toUpperCase);


        System.out.println(future.get());
    }

    // combining futures (promise.all with two futures only) / can execute on different threads.
    private static void ex3() throws Exception {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "World");
        CompletableFuture<String> combined = future1.thenCombine(future2, (s1, s2) -> s1 + " " + s2);
        System.out.println(combined.get());
    }

    //error handling
    private static void ex4() throws Exception{
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (Math.random() < 0.5) {
                throw new RuntimeException("Oops, something went wrong!");
            }
            return "Success!";
        }).exceptionally(ex -> "Error: " + ex.getMessage());

        System.out.println(future.get());
    }

    // composing futures (sequential execution) / can execute on differ threads...
    private static void ex5() throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()-> "Hello")
                .thenCompose((s)-> CompletableFuture.supplyAsync(()-> s+", World!"));

        System.out.println(future.get());
    }

    // sequential execution using thenApply / the thenApplys can run on different thread pools.
    private static void ex6() throws Exception {
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

        /*
            First task: ForkJoinPool.commonPool-worker-1
            Second task: main
            Third task: main
            Final result: Result from first -> processed -> finalized
         */
    }

    //sequential execution using thenApply and custom executor / controlling the execution
    private static void ex7() throws Exception {
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
    }

    private static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
