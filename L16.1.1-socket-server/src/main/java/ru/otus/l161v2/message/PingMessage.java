package ru.otus.l161v2.message;

public class PingMessage extends Message {
    private final long time;

    public PingMessage() {
        time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "PingMessage{time=" + time + '}';
    }
}
