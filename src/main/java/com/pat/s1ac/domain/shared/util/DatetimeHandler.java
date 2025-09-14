package com.pat.s1ac.domain.shared.util;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DatetimeHandler {
    public static final String dateFormat = "yyyy-MM-dd";
    public static final String timeFormat = "HH:mm";
    public static final String datetimeFormat = dateFormat + " " + timeFormat;

    public static String now() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern(datetimeFormat);

        return now.format(datetimeFormatter);
    }

    public static boolean isValidDatetime(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datetimeFormat);

        try {
            LocalDateTime.parse(datetime, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
