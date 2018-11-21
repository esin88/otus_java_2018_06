package ru.otus.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InterruptedLockExample {

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        lock.lock();

        Thread interrupted = new InterruptedThread(lock, "InterruptedThread");
        interrupted.start();

        TimeUnit.SECONDS.sleep(2);
        interrupted.interrupt();
    }
}

class InterruptedThread extends Thread {
    final Lock lock;

    InterruptedThread(Lock lock, String name) {
        super(name);
        this.lock = lock;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + ": try to acquire lock");
        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        lock.unlock();
    }
}
