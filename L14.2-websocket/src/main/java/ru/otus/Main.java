package ru.otus;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.ajax.AjaxTimerServlet;
import ru.otus.async.AsyncServlet;
import ru.otus.polling.MessengerServlet;
import ru.otus.timer.TimerServlet;
import ru.otus.websocket.WebSocketChatServlet;

/**
 * @author v.chibrikov
 */
public class Main {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "public_html";

    public static void main(String[] args) throws Exception {

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        //page reloaded by the timer
        context.addServlet(TimerServlet.class, "/timer");
        //part of a page reloaded by the timer
        context.addServlet(AjaxTimerServlet.class, "/server-time");
        //long-polling waits till a message
        context.addServlet(MessengerServlet.class, "/messenger");
        //long-polling async servlet
        ServletHolder asyncHolder = context.addServlet(AsyncServlet.class, "/async");
        asyncHolder.setAsyncSupported(true);
        //web chat
        context.addServlet(WebSocketChatServlet.class, "/chat");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }
}