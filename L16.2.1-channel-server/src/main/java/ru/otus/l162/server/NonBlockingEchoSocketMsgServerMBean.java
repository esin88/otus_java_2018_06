package ru.otus.l162.server;

/**
 * Created by tully.
 */
public interface NonBlockingEchoSocketMsgServerMBean {
    boolean getRunning();

    void setRunning(boolean running);
}
