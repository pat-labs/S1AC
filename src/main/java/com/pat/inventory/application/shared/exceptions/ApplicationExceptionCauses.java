package com.pat.inventory.application.shared.exceptions;

public class ApplicationExceptionCauses {
    public ApplicationExceptionCauses() {
    }

    public static String invalidDataAccess(String message) {
        String PATTERN = "Database error: %s";
        return String.format(PATTERN, message);
    }
}
