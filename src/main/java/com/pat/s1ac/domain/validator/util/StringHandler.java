package com.pat.s1ac.domain.validator.util;

import com.pat.s1ac.domain.error.DomainExceptionCauses;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringHandler {
    public static boolean isNotNullOrEmpty(Collection<?> collection) {
        return (collection != null) && !collection.isEmpty();
    }

    public static boolean isNotNullOrEmpty(String text) {
        return (text != null) && !text.isBlank();
    }

    public static boolean isNotValidStringLength(String str, int maxLength) {
        if (str == null) return true; // null is invalid
        return str.length() > maxLength;
    }

    public static String validateString(int maxLength, String fieldName, String description) {
        if (!StringHandler.isNotNullOrEmpty(description)) {
            return DomainExceptionCauses.requiredField(fieldName);
        }
        List<String> errors = Stream.of(
                        StringHandler.isNotValidStringLength(description, maxLength) ? DomainExceptionCauses.invalidStringLength(description, maxLength) : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!errors.isEmpty()) {
            return String.join(", ", errors);
        }
        return null;
    }
}
