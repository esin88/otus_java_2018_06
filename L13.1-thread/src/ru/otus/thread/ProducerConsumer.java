package ru.otus.thread;

public class ProducerConsumer {
    int jobCounter = 0;

    public synchronized void consume() {
        while (true) {
            try {
                wait();
                System.out.println("Consume a new job-" + jobCounter);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("");
            }
        }
    }

    public void produce() {
        while (true) {
            try {
                System.out.println("Produce a new job-" + ++jobCounter);
                synchronized (this) {
                    notify();
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();

        Thread consumer = new Thread(() -> pc.consume(), "Thread-Consumer");
        Thread producer = new Thread(() -> pc.produce(), "Thread-Producer");

        consumer.start();
        producer.start();

    }

}
