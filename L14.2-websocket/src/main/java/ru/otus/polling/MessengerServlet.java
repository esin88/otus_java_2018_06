package ru.otus.polling;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.log.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import static ru.otus.util.TimeUtil.getTime;

/**
 * Created by tully.
 */
public class MessengerServlet extends HttpServlet {

    private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
    private final static Logger log = Logger.getLogger(MessengerServlet.class.getName());

    public MessengerServlet() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                messages.add(new Message("Time: " + getTime()));
            }
        }, 5000, 5000);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            log.info("lock thread");
            Message message = messages.take(); //this line blocks till messages.size() != 0.
            log.info("release thread");
            response.getWriter().println(new Gson().toJson(message));
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
