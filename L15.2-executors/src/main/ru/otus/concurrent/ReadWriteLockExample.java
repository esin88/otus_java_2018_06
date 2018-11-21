package ru.otus.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {

    public static void main(String[] args) {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        new ReaderThread(readWriteLock, "Reader-1").start();
        new ReaderThread(readWriteLock, "Reader-2").start();
        new WriterThread(readWriteLock, "Writer-1").start();
    }

}

class ReaderThread extends Thread {
    final ReadWriteLock lock;

    ReaderThread(ReadWriteLock lock, String name) {
        super(name);
        this.lock = lock;
    }

    public void run() {
        while (true) {
            try {
                lock.readLock().lock();
                System.out.println(Thread.currentThread().getName() + ": start reading data");
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + ": end reading data");
                lock.readLock().unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class WriterThread extends Thread {
    final ReadWriteLock lock;

    WriterThread(ReadWriteLock lock, String name) {
        super(name);
        this.lock = lock;
    }

    public void run() {
        while (true) {
            try {
                lock.writeLock().lock();
                System.out.println(Thread.currentThread().getName() + ": start writing data");
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + ": end writing data");
                lock.writeLock().unlock();

                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
