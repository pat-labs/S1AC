package com.pat.s1ac.domain.shared.error;

import com.pat.s1ac.domain.shared.util.DatetimeHandler;

public class DomainExceptionCauses {
    public DomainExceptionCauses() {
    }

    public static String requiredField(String fieldName) {
        String PATTERN = "Field %s cannot be null or blank";
        return String.format(PATTERN, fieldName);
    }

    public static String invalidDatetimeFormat(String fieldName) {
        String PATTERN = "Field %s not have the valid format, the format required is %s";
        return String.format(PATTERN, fieldName, DatetimeHandler.datetimeFormat);
    }

    public static String invalidUTF8String(String fieldValue) {
        String PATTERN = "String contain especial chacters: %s";
        return String.format(PATTERN, fieldValue);
    }

    public static String invalidStringLength(String fieldValue, int maxLength) {
        String PATTERN = "Field %s length exceeds the maximum limit(%s).";
        return String.format(PATTERN, fieldValue, maxLength);
    }

    public static String resourceNotFound(String identifier) {
        String PATTERN = "Resource not found for identifier: %s";
        return String.format(PATTERN, identifier);
    }

    public static String illegalArgument(String fieldValue) {
        String PATTERN = "Invalid argument:  %s";
        return String.format(PATTERN, fieldValue);
    }
}
