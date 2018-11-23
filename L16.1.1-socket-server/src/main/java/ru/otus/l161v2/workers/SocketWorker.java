package ru.otus.l161v2.workers;

import ru.otus.l161v2.message.Message;

public interface SocketWorker {
    void start();

    boolean offer(Message message);

    @Blocks
    void put(Message message) throws InterruptedException;

    Message poll();

    @Blocks
    Message take() throws InterruptedException;
}
