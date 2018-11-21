package ru.otus.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchRunner {

    public static void main(String[] args) throws InterruptedException {

        final int workerCount = 3;
        CountDownLatch latch = new CountDownLatch(workerCount);

        for (int i = 0; i < workerCount; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("The job is done");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        latch.await();
        System.out.println("All workers completed their job");

    }
}
