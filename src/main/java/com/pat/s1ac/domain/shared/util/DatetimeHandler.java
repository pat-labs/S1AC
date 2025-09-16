package com.pat.s1ac.domain.shared.util;

import com.pat.s1ac.domain.shared.constant.Constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DatetimeHandler {

    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(Constant.DATETIME_FORMAT);

    public static String now() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }

    public static boolean isValidDatetime(String datetime) {
        try {
            LocalDateTime.parse(datetime, DATETIME_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
