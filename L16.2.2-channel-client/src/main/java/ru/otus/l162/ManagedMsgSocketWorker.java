package ru.otus.l162;

import ru.otus.l162.channel.SocketMsgWorker;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by tully.
 */
class ManagedMsgSocketWorker extends SocketMsgWorker {

    private final Socket socket;

    ManagedMsgSocketWorker(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private ManagedMsgSocketWorker(Socket socket) throws IOException {
        super(socket);
        this.socket = socket;
    }

    public void close() {
        super.close();
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
