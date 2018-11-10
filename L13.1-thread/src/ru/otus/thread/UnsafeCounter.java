package ru.otus.thread;

public class UnsafeCounter {

    private volatile int value = 0;

    public synchronized void increment() {
        value++;
    }

    public int getValue() {
        return value;
    }

    public static void main(String[] args) throws InterruptedException {
        UnsafeCounter counter = new UnsafeCounter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) {
                counter.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) {
                counter.increment();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("counter value: " + counter.getValue());
    }
}
