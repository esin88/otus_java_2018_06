package ru.otus.l162;

import java.util.concurrent.CompletableFuture;

/**
 * @see <a href="https://github.com/nurkiewicz/completablefuture/tree/master/src/test/java/com/nurkiewicz/reactive">examples</a>
 * <p>
 * https://github.com/nurkiewicz/completablefuture/tree/master/src/test/java/com/nurkiewicz/reactive
 */
public class Example01ThenAccept {
    public static void main(String[] args) {
        final CompletableFuture<Void> future0 = CompletableFuture.runAsync(() -> {
            // heavy computation task
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End of task");
        });

        final CompletableFuture<Void> future1 = future0.thenAccept(aVoid -> System.out.println("Finished"));

        future1.join();
    }
}
