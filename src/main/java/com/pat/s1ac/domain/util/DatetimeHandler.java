package com.pat.s1ac.domain.util;

import com.pat.s1ac.domain.error.DomainExceptionCauses;
import com.pat.s1ac.domain.model.util.Response;
import com.pat.s1ac.infrastructure.bootstrap.Constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DatetimeHandler {

    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(Constant.DATETIME_FORMAT);

    public static String now() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }

    public static Response<Boolean> isNotValidDatetime(String datetime, String fieldName) {
        try {
            LocalDateTime.parse(datetime, DATETIME_FORMATTER);
            return Response.domainError(DomainExceptionCauses.invalidDatetimeFormat(fieldName));
        } catch (DateTimeParseException e) {
            return Response.success(true);
        }
    }
}
