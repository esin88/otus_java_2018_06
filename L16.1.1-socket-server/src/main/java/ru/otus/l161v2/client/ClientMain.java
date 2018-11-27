package ru.otus.l161v2.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.l161v2.message.PingMessage;

import java.io.IOException;

public class ClientMain {
    private static final Logger LOG = LoggerFactory.getLogger(ClientMain.class);

    private static final String HOST = "localhost";
    private static final int PORT = 5050;

    private static final int MAX_MESSAGES_COUNT = 3;
    private static final int PAUSE_MS = 1000;

    public static void main(String[] args) throws IOException, InterruptedException {
        final ClientWorker clientWorker = new ClientWorker(HOST, PORT);
        clientWorker.start();

        for (int i = 0; i < MAX_MESSAGES_COUNT; i++) {
            final PingMessage message = new PingMessage();
            clientWorker.offer(message);
            LOG.info("Message sent: " + message);
            Thread.sleep(PAUSE_MS);
        }

        clientWorker.close();
    }
}
