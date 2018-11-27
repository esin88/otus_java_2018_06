package ru.otus.l161v2.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.l161v2.workers.BaseSocketWorker;
import ru.otus.l161v2.workers.Blocks;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    private static final Logger LOG = LoggerFactory.getLogger(ServerMain.class);
    private static final int PORT = 5050;

    @Blocks
    public static void main(String[] args) throws IOException {
        try (final ServerSocket serverSocket = new ServerSocket(PORT)) {
            final MultiEchoWorker multiEchoWorker = new MultiEchoWorker();
            multiEchoWorker.start();
            LOG.info("Server started on port: " + serverSocket.getLocalPort());
            while (true) {
                final Socket socket = serverSocket.accept(); //blocks
                multiEchoWorker.addWorker(new BaseSocketWorker(socket));
//                final SocketWorker worker = new SingleEchoWorker(socket);
//                worker.start();
            }
        }
    }
}
