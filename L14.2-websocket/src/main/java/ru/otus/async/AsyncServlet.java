package ru.otus.async;

import com.google.gson.Gson;
import ru.otus.polling.Message;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import static ru.otus.util.TimeUtil.getTime;

public class AsyncServlet extends HttpServlet {

    private final static Logger log = Logger.getLogger(AsyncServlet.class.getName());

    private final Queue<AsyncContext> ctxQueue = new LinkedBlockingQueue<>();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        final AsyncContext asyncContext = request.startAsync(request, response);
        asyncContext.setTimeout(1000 * 60 * 10); // 10 min
        log.info("add to queue");
        ctxQueue.add(asyncContext);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        while (!ctxQueue.isEmpty()) {
            log.info("write response");
            AsyncContext asyncContext = ctxQueue.poll();
            asyncContext.getResponse().getWriter().println(new Gson().toJson(new Message(getTime())));
            asyncContext.getResponse().setContentType("application/json");
            asyncContext.complete();
        }
    }

}
