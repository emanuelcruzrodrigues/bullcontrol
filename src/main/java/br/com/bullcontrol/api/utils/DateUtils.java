package br.com.bullcontrol.api.utils;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

public class DateUtils {
    public static String toString(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String toString(LocalDate localDate) {
        if (localDate == null) return null;
        return localDate.toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
    }
}
