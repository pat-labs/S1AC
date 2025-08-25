package com.pat.inventory.domain.shared.util;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class StringHandler {
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return (collection == null) || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(String text) {
        return (text == null) || text.trim().isBlank();
    }

    public static boolean isValidStringLength(String str, int maxLength) {
        return str.length() <= maxLength;
    }

    public static boolean isValidUTF8String(String str) {
        try {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            String decodedStr = new String(bytes, StandardCharsets.UTF_8);
            return str.equals(decodedStr);
        } catch (Exception e) {
            return false;
        }
    }
}
