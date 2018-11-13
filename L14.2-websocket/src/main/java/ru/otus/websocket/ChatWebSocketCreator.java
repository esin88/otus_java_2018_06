package ru.otus.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import ru.otus.async.AsyncServlet;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * @author v.chibrikov
 */
public class ChatWebSocketCreator implements WebSocketCreator {
    private final static Logger log = Logger.getLogger(ChatWebSocketCreator.class.getName());
    private Set<ChatWebSocket> users;

    public ChatWebSocketCreator() {
        this.users = ConcurrentHashMap.newKeySet();
        log.info("WebSocketCreator created");
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        ChatWebSocket socket = new ChatWebSocket(users);
        log.info("Socket created");
        return socket;
    }
}
