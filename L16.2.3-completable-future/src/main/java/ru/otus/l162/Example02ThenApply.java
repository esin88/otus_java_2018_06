package ru.otus.l162;

import java.util.concurrent.CompletableFuture;

public class Example02ThenApply {
    public static void main(String[] args) {
        final CompletableFuture<String> future0 = CompletableFuture.supplyAsync(() -> {
            // heavy computation task
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End of task");
            return "result";
        });

        final CompletableFuture<String> future1 = future0.thenApply(value -> {
            System.out.println("Got value of length " + value.length());
            return value;
        });

        final CompletableFuture<Void> future2 = future1.thenAccept(value -> System.out.println("Got value \"" + value + '"'));

        future2.join();
    }
}
