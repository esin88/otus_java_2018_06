package ru.otus.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class TimeUtil {
    private TimeUtil() {
    }

    public static String getTime() {
        Date date = new Date();
        date.getTime();
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(date);
    }
}
