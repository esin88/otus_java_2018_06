package ru.otus.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class FutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService threadPool = Executors.newCachedThreadPool();

        Callable callable = () -> {
            TimeUnit.SECONDS.sleep(1);
            return "result from thread";
        };

        Future<String> future = threadPool.submit(callable);

        System.out.println(future.get());
        threadPool.shutdown();
    }
}
