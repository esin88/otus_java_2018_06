package ru.otus.l162.messages;

import ru.otus.l162.app.Msg;

/**
 * Created by tully.
 */
public class PingMsg extends Msg {
    private final long time;
    private final String pid;

    public PingMsg(String pid) {
        this.pid = pid;
        time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

    public String getPid() {
        return pid;
    }

    @Override
    public String toString() {
        return "PingMsg{" + "pid='" + pid + ", time=" + time + '\'' + '}';
    }
}
