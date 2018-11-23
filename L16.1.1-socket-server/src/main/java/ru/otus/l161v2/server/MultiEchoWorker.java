package ru.otus.l161v2.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.l161v2.message.Message;
import ru.otus.l161v2.workers.SocketWorker;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiEchoWorker {
    private static final Logger LOG = LoggerFactory.getLogger(MultiEchoWorker.class);
    private static final int THREADS_NUMBER = 1;
    private static final int SLEEP_TIME_MS = 100;

    private final ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUMBER);
    private final List<SocketWorker> workers = new CopyOnWriteArrayList<>();

    public MultiEchoWorker() {
    }

    public void start() {
        executor.submit(this::echoLoop);
    }

    public void addWorker(SocketWorker worker) {
        workers.add(worker);
        worker.start();
    }

    private void echoLoop() {
        while (true) {
            for (var worker : workers) {
                Message message = worker.poll();
                while (message != null) {
                    System.out.println("Mirroring the message: " + message.toString());
                    worker.offer(message);
                    message = worker.poll();
                }
            }
            try {
                Thread.sleep(SLEEP_TIME_MS);
            } catch (InterruptedException e) {
                LOG.warn(e.getMessage());
            }
        }
    }
}
