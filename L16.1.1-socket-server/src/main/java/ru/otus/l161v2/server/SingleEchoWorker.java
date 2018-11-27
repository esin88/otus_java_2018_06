package ru.otus.l161v2.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.l161v2.message.Message;
import ru.otus.l161v2.workers.BaseSocketWorker;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleEchoWorker extends BaseSocketWorker {
    private static final Logger LOG = LoggerFactory.getLogger(SingleEchoWorker.class);
    private static final int SLEEP_TIME_MS = 100;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public SingleEchoWorker(Socket socket) {
        super(socket);
    }

    @Override
    public void start() {
        super.start();
        executorService.submit(this::workLoop);
    }

    @Override
    public void close() {
        super.close();
        executorService.shutdown();
    }

    private void workLoop() {
        while (!Thread.currentThread().isInterrupted()) {
            Message receivedMessage = poll();
            while (receivedMessage != null) {
                LOG.info("Echoing message: " + receivedMessage);
                offer(receivedMessage);
                receivedMessage = poll();
            }
            try {
                Thread.sleep(SLEEP_TIME_MS);
            } catch (InterruptedException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
