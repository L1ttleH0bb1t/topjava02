package ru.javawebinar.topjava.util;

import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.to.DateTimeFilter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    public static final DateTimeFormatter DATE_TME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static String toString(LocalDateTime ldt) {
        return toString(ldt, DATE_TME_FORMATTER);
    }

    public static String toString(LocalDateTime ldt, DateTimeFormatter formatter) {
        return ldt == null ? "" : ldt.format(formatter);
    }

    public static LocalDateTime toDateTime(String str) {
        return toDateTime(str, DATE_TME_FORMATTER);
    }

    public static LocalDateTime toDateTime(String str, DateTimeFormatter formatter) {
        return StringUtils.isEmpty(str) ? LocalDateTime.now() : LocalDateTime.parse(str, formatter);
    }

    public static LocalDateTime parseStartDate(DateTimeFilter filter) {
        return StringUtils.isEmpty(filter.getStartTime()) ? LocalDateTime.parse(filter.getStartDate() + " " + TIME_FORMATTER.format(LocalTime.MIN), DATE_TME_FORMATTER):
                LocalDateTime.parse(filter.getStartDate() + " " + filter.getStartTime(), DATE_TME_FORMATTER);
    }

    public static LocalDateTime parseEndDate(DateTimeFilter filter) {
        return StringUtils.isEmpty(filter.getEndTime()) ? LocalDateTime.parse(filter.getEndDate() + " " + TIME_FORMATTER.format(LocalTime.MAX), DATE_TME_FORMATTER):
                LocalDateTime.parse(filter.getEndDate() + " " + filter.getEndTime(), DATE_TME_FORMATTER);
    }
}
