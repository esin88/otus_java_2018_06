package ru.otus.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadFactoryExample {

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 5;
    private static final long THREAD_TTL = 60L;
    private static final int QUEUE_CAPACITY = 100;

    public static void main(String[] args) {
        ExecutorService executorService = createOtusThreadPool();
        executorService.shutdown();
    }

    public static ThreadPoolExecutor createOtusThreadPool() {
        final ThreadPoolExecutor otusThreadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                THREAD_TTL, TimeUnit.SECONDS, new LinkedBlockingQueue<>(QUEUE_CAPACITY));

        return otusThreadPoolExecutor;
    }
}
