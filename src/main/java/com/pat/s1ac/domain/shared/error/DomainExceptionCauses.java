package com.pat.s1ac.domain.shared.error;

import com.pat.s1ac.domain.shared.util.DatetimeHandler;

public class DomainExceptionCauses {
    public DomainExceptionCauses() {
    }

    public static String requiredField(String fieldName) {
        String PATTERN = "Field %s is required.";
        return String.format(PATTERN, fieldName);
    }

    public static String invalidDatetimeFormat(String fieldName) {
        String PATTERN = "Field %s not have the valid format, the format required is %s";
        return String.format(PATTERN, fieldName, DatetimeHandler.getDatetimeFormat());
    }

    public static String invalidURL(String fieldValue) {
        String PATTERN = "The URL could not be acceded %s";
        return String.format(PATTERN, fieldValue);
    }

    public static String mustBeMoreThanZero(double fieldValue) {
        String PATTERN = "Number must be greater than 0, current value: %s";
        return String.format(PATTERN, fieldValue);
    }

    public static String invalidUTF8String(String fieldValue) {
        String PATTERN = "String contain especial chacters: %s";
        return String.format(PATTERN, fieldValue);
    }

    public static String invalidStringLength(String fieldValue, int maxLength) {
        String PATTERN = "Field %s length exceeds the maximum limit(%s).";
        return String.format(PATTERN, fieldValue, maxLength);
    }
}
