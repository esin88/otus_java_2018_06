package ru.otus.thread;

public class SynchronizedObject {

    public static void main(String[] args) {
        Object sync = new Object();
        synchronized (sync) {
            // ....
        }
    }

    public synchronized void syncMethod() {
        // ..
    }

    public void syncMethod2() {
        synchronized (this) {
            // ...
        }
    }

    public static synchronized void staticSync() {
        // ...
    }

    public static void staticSync2() {
        synchronized (SynchronizedObject.class) {
            // ...
        }
    }

}
