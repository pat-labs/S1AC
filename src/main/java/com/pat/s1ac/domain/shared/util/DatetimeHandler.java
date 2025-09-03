package com.pat.s1ac.domain.shared.util;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DatetimeHandler {
    private static final String dateFormat = "yyyy-MM-dd";
    private static final String timeFormat = "HH:mm";
    @Getter
    private static final String datetimeFormat = dateFormat + " " + timeFormat;

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

    public static boolean isValidDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
