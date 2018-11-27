package ru.otus.l161v2.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.l161v2.message.Message;
import ru.otus.l161v2.workers.BaseSocketWorker;
import ru.otus.l161v2.workers.Blocks;

import java.io.IOException;
import java.net.Socket;

public class ClientWorker extends BaseSocketWorker {
    private static final Logger LOG = LoggerFactory.getLogger(ClientWorker.class);
    private static final int SLEEP_TIME_MS = 100;

    private final Thread worker = new Thread(this::workLoop);
//    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ClientWorker(String host, int port) throws IOException {
        super(new Socket(host, port));
    }

    @Override
    public void start() {
        super.start();
//        executor.submit(this::workLoop);
        worker.setName("ClientWorker");
        worker.start();
    }

    @Override
    public void close() {
        super.close();
        worker.interrupt();
//        executor.shutdownNow();
    }

    @Blocks
    private void workLoop() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                final Message receivedMessage = take();
                LOG.info("Message received: " + receivedMessage);
                Thread.sleep(SLEEP_TIME_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
