package ru.javawebinar.topjava.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    public static final DateTimeFormatter DATE_TME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "null" : ldt.format(DATE_TME_FORMATTER);
    }

    public static String toString(Date date) {
        return formatter.format(date);
    }

}
