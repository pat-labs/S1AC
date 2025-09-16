package com.pat.s1ac.application.error;

public class ApplicationExceptionCauses {
    public ApplicationExceptionCauses() {
    }

    public static String invalidDataAccess(String message) {
        String PATTERN = "Database error: %s";
        return String.format(PATTERN, message);
    }
}
