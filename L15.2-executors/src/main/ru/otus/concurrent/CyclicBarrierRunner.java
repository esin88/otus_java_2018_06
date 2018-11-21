package ru.otus.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierRunner {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        new Worker(cyclicBarrier, "Worker-1").start();
        new Worker(cyclicBarrier, "Worker-2").start();
    }
}

class Worker extends Thread {
    final CyclicBarrier barrier;

    Worker(CyclicBarrier barrier, String name) {
        super(name);
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()
                    + ": completed first part");
            barrier.await();
            System.out.println(Thread.currentThread().getName()
                    + ": completed second part");
            barrier.await();
            System.out.println(Thread.currentThread().getName() + ": job is done");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
