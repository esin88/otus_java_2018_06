package ru.otus.timer;

import ru.otus.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static ru.otus.util.TimeUtil.getTime;

public class TimerServlet extends HttpServlet {

    private static final String REFRESH_VARIABLE_NAME = "refreshPeriod";
    private static final String TIME_VARIABLE_NAME = "time";
    private static final String TIMER_PAGE_TEMPLATE = "timer.html";

    private static final int PERIOD_MS = 1000;

    private final TemplateProcessor templateProcessor;

    public TimerServlet() throws IOException {
        this.templateProcessor = new TemplateProcessor();
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(REFRESH_VARIABLE_NAME, String.valueOf(PERIOD_MS));
        pageVariables.put(TIME_VARIABLE_NAME, getTime());

        response.getWriter().println(templateProcessor.getPage(TIMER_PAGE_TEMPLATE, pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }


}
