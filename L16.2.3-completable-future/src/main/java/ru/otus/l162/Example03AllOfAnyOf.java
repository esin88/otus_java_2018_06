package ru.otus.l162;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("Duplicates")
public class Example03AllOfAnyOf {
    private static final Random RND = new Random();

    public static void main(String[] args) {
//        allOf();
        anyOf();
    }

    private static void allOf(){
        final CompletableFuture<Long> future0 = CompletableFuture.supplyAsync(() -> {
            // heavy computation task
            try {
                Thread.sleep(RND.nextInt(5000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End of task 0");
            return 10L;
        });
        final CompletableFuture<Long> future1 = CompletableFuture.supplyAsync(() -> {
            // heavy computation task
            try {
                Thread.sleep(RND.nextInt(5000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End of task 1");
            return 20L;
        });
        final CompletableFuture<Long> future2 = CompletableFuture.supplyAsync(() -> {
            // heavy computation task
            try {
                Thread.sleep(RND.nextInt(5000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End of task 2");
            return 30L;
        });

        final CompletableFuture<Void> allFutures = CompletableFuture.allOf(future0, future1, future2)
                .thenAccept(aVoid -> {
                    try {
                        final long sum = future0.get() + future1.get() + future2.get();
                        System.out.println("Sum: " + sum);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });

        allFutures.join();
    }

    private static void anyOf(){
        final CompletableFuture<Long> future0 = CompletableFuture.supplyAsync(() -> {
            // heavy computation task
            try {
                Thread.sleep(RND.nextInt(5000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End of task 0");
            return 10L;
        });
        final CompletableFuture<Long> future1 = CompletableFuture.supplyAsync(() -> {
            // heavy computation task
            try {
                Thread.sleep(RND.nextInt(5000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End of task 1");
            return 20L;
        });
        final CompletableFuture<Long> future2 = CompletableFuture.supplyAsync(() -> {
            // heavy computation task
            try {
                Thread.sleep(RND.nextInt(5000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End of task 2");
            return 30L;
        });

        final CompletableFuture<Void> anyFutures = CompletableFuture.anyOf(future0, future1, future2)
                .thenAccept(o -> System.out.println("First: " + o));

        anyFutures.join();
    }
}
