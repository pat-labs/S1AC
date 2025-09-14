package com.pat.s1ac.domain.shared.util;

import com.pat.s1ac.domain.shared.error.DomainExceptionCauses;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static String validateString(int maxLength, String fieldName, String description) {
        if (!StringHandler.isNullOrEmpty(description)) {
            return DomainExceptionCauses.requiredField(fieldName);
        }
        List<String> errors = Stream.of(
                        !StringHandler.isValidUTF8String(description) ? DomainExceptionCauses.invalidUTF8String(description) : null,
                        !StringHandler.isValidStringLength(description, maxLength) ? DomainExceptionCauses.invalidStringLength(description, maxLength) : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!errors.isEmpty()) {
            return String.join(", ", errors);
        }
        return null;
    }
}
