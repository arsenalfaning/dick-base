package com.dick.base.util;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class TypeUtil {

    public static <T> T convertFromString(String value, Class<T> type) throws ParseException {
        if (type == String.class) {
            return (T) value;
        }
        if (type == Boolean.class || type == Boolean.TYPE) {
            return (T) Boolean.valueOf(value);
        }
        if (type == Byte.class || type == Byte.TYPE) {
            return (T) Byte.valueOf(value);
        }
        if (type == Short.class || type == Short.TYPE) {
            return (T) Short.valueOf(value);
        }
        if (type == Integer.class || type == Integer.TYPE) {
            return (T) Integer.valueOf(value);
        }
        if (type == Long.class || type == Long.TYPE) {
            return (T) Long.valueOf(value);
        }
        if (type == Float.class || type == Float.TYPE) {
            return (T) Float.valueOf(value);
        }
        if (type == Double.class || type == Double.TYPE) {
            return (T) Double.valueOf(value);
        }
        if (type == BigDecimal.class) {
            return (T) new BigDecimal(value);
        }
        if (type == BigInteger.class) {
            return (T) new BigInteger(value);
        }
        if (type == Date.class) {
            return (T) DateUtils.parseDate(value, StdDateFormat.DATE_FORMAT_STR_ISO8601, DateUtil.Format_yyyyMMddHHmmss);
        }
        if (type == LocalDateTime.class) {
            return (T) DateUtil.parse(value);
        }
        if (type == LocalDate.class) {
            return (T) DateUtil.parseDate(value);
        }
        if (type == LocalTime.class) {
            return (T) DateUtil.parseTime(value);
        }
        return (T) value;
    }
}
