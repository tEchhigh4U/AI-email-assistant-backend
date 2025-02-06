package com.email.assistant.util;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

public class DateTimeUtil {

    public static String getFormattedTimestamp(){
        long currentMillis = System.currentTimeMillis();
        Instant instant = Instant.ofEpochMilli(currentMillis);
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault()); // Use the system's default time zone
        return formatter.format(instant);
    }
}
