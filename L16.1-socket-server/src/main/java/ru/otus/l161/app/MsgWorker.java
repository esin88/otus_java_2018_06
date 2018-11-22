package ru.otus.l161.app;

import ru.otus.l161.channel.Blocks;

import java.io.Closeable;

/**
 * Created by tully.
 */
public interface MsgWorker extends Closeable {
    void send(Msg msg);

    Msg poll();

    @Blocks
    Msg take() throws InterruptedException;

    void close();
}
