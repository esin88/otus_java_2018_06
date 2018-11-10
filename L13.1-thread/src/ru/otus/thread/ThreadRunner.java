package ru.otus.thread;

public class ThreadRunner {

    public static void main(String[] args) throws InterruptedException {
       new ThreadRunner().runThreads();
    }

    public void runThreads() throws InterruptedException {
        Thread ht = new HeavyThread();

        ht.join();

        ht.start();
    }

    class HeavyThread extends Thread {
        public void run() {
            while (true) {

            }
        }
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            Thread.currentThread().getName();
            System.out.println("I am thread child");
        }
    }

    class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("I am runnable impl");
        }
    }
}
