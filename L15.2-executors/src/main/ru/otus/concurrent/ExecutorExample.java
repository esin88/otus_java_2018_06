package ru.otus.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ExecutorExample {

    public static void main(String[] args) {
        new ExecutorExample().createThreadPool();
    }

    public void createScheduledExecutor() {
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.schedule(() -> System.out.println("delayed task"), 2, TimeUnit.SECONDS);
        scheduledExecutor.scheduleAtFixedRate(() -> System.out.println("periodic task"), 3,1, TimeUnit.SECONDS);
    }

    public void createThreadPool() {

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + ": start job");
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + ": job is done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        IntStream.range(0, 10)
                 .forEach(i -> singleThreadPool.execute(task));

        singleThreadPool.shutdown();
    }
}
