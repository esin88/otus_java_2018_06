package ru.otus.l162;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class Example04ErrorHandling {
    private static final Random RND = new Random();

    public static void main(String[] args) {
//        success();
        error();
    }

    private static void success() {
        final CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // heavy computation task
            try {
                Thread.sleep(RND.nextInt(5000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End of success task");
            return "some string";
        });
        final CompletableFuture<String> exceptionally = future.exceptionally(t -> {
                    System.out.println(t.getMessage());
                    return "error";
                }
        );
        final CompletableFuture<Void> result = exceptionally.thenAccept(s -> System.out.println("Value: \"" + s + '"'));

        result.join();
    }

    private static void error() {
        final CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // heavy computation task
            try {
                Thread.sleep(RND.nextInt(5000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End of error task");
            throw new IllegalStateException("Error :(");
        });
//        final CompletableFuture<String> exceptionally = future.exceptionally(t -> {
//                    System.out.println(t.getMessage());
//                    return "error";
//                }
//        );
        final CompletableFuture<String> handle = future.handle((s, throwable) -> {
            if (throwable != null) {
                System.err.println(throwable.getMessage());
                return "default value";
            } else {
                return s;
            }
        });
        final CompletableFuture<Void> result = handle.thenAccept(s -> System.out.println("Value: \"" + s + '"'));

        result.join();
    }
}
