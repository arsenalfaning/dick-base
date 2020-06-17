package com.dick.base.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final String Format_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER_FULL = DateTimeFormatter.ofPattern(Format_yyyyMMddHHmmss);
    public static final DateTimeFormatter DATE_TIME_FORMATTER_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATE_TIME_FORMATTER_TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static String format(LocalDateTime date) {
        if (date == null) return null;
        return DATE_TIME_FORMATTER_FULL.format(date);
    }

    public static String format(LocalDate date) {
        if (date == null) return null;
        return DATE_TIME_FORMATTER_DATE.format(date);
    }

    public static String format(LocalTime time) {
        if (time == null) return null;
        return DATE_TIME_FORMATTER_TIME.format(time);
    }

    public static LocalDateTime parse(String value) {
        return LocalDateTime.parse(value, DATE_TIME_FORMATTER_FULL);
    }

    public static LocalDate parseDate(String value) {
        return LocalDate.parse(value, DATE_TIME_FORMATTER_DATE);
    }

    public static LocalTime parseTime(String value) {
        return LocalTime.parse(value, DATE_TIME_FORMATTER_TIME);
    }
}
