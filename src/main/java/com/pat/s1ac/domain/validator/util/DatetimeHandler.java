package com.pat.s1ac.domain.validator.util;

import com.pat.s1ac.infrastructure.bootstrap.Constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DatetimeHandler {

    public static String now() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern(Constant.DATETIME_FORMAT);

        return now.format(datetimeFormatter);
    }

    public static boolean isNotValidDatetime(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATETIME_FORMAT);

        try {
            LocalDateTime.parse(datetime, formatter);
            return false;
        } catch (DateTimeParseException e) {
            return true;
        }
    }
}
