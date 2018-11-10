package ru.otus.thread;

public class WaitNotify {

    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
        // t1
        synchronized (o) {
            //
            o.wait();
            // code
        }

        // t2
        synchronized (o) {
            o.notifyAll();
        }
    }
}
