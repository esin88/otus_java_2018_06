package ru.otus.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tully.
 */
@Service
public class TimeService {


    private static final String DEFAULT_PATTERN = "HH:mm:ss";

    private final String pattern;

    public TimeService() {
        this.pattern = DEFAULT_PATTERN;
    }

    public TimeService(String pattern) {
        this.pattern = pattern;
    }

    String getTime() {
        Date date = new Date();
        date.getTime();
        DateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
}
