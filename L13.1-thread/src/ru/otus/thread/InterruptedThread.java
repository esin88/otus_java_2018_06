package ru.otus.thread;

import java.util.concurrent.TimeUnit;

public class InterruptedThread {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                System.out.println("I am going to sleep");
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                System.out.println("OK wake up");
            }
        });

        Thread t2 = new Thread() {
            public void run() {
                while (!isInterrupted()) {
                    System.out.println("do job");
                }
                System.out.println("I was interrupted");
            }
        };

        t2.start();
        TimeUnit.SECONDS.sleep(1);
        t2.interrupt();

    }
}
