package com.pat.s1ac.infrastructure.mymongo.error;

public final class MongoExceptionCauses {

    private MongoExceptionCauses() {
        // prevent instantiation
    }

    public static String requiredField(String fieldName) {
        return String.format("Field '%s' cannot be null or blank", fieldName);
    }

    public static String connectionFailed(String host, int port) {
        return String.format("Failed to connect to MongoDB at %s:%d", host, port);
    }

    public static String authenticationFailed(String user) {
        return String.format("Authentication failed for user '%s'", user);
    }

    public static String timeout(String operation, long millis) {
        return String.format("Operation '%s' timed out after %d ms", operation, millis);
    }

    public static String duplicateKey(String keyName, Object keyValue) {
        return String.format("Duplicate key error on field '%s' with value '%s'", keyName, keyValue);
    }

    public static String writeFailed(String collection, String reason) {
        return String.format("Failed to write to collection '%s': %s", collection, reason);
    }

    public static String readFailed(String collection, String reason) {
        return String.format("Failed to read from collection '%s': %s", collection, reason);
    }

    public static String notFound(String resource, String id) {
        return String.format("%s with ID '%s' not found", resource, id);
    }

    public static String unknown(Throwable ex) {
        return String.format("Unknown MongoDB error: %s", ex.getMessage());
    }
}
