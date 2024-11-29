package com.pat.inventory.infrastructure.shared.exceptions;

public class InfrastructureExceptionCauses {
    public InfrastructureExceptionCauses() {
    }

    public static String failSendToBroker(String queque, String message) {
        String PATTERN = "The message: %s could not send to the queque: %s";
        return String.format(PATTERN, message, queque);
    }

    public static String ilegalArgument(String key, String value) {
        String PATTERN = "Invalid key: %s, value: %s";
        return String.format(PATTERN, key, value);
    }

    public static String decodeRowFail(String message) {
        String PATTERN = "Error creating Product from database row %s";
        return String.format(PATTERN, message);
    }

    public static String queryExecFail(String query, String message) {
        String PATTERN = "Error executing query: %s\nDetail:";
        return String.format(PATTERN, message);
    }
}
