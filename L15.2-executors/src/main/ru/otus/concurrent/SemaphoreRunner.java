package ru.otus.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SemaphoreRunner {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        IntStream.range(0, 10)
                 .forEach(i -> new Thread(new SemaphoreWorker(semaphore), "Worker-" + i).start());

    }
}

class SemaphoreWorker implements Runnable {
    final Semaphore semaphore;

    SemaphoreWorker(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName()
                    + ": do some job");
            TimeUnit.SECONDS.sleep(1);
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

