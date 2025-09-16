package com.pat.s1ac.domain.validator.util;

import com.pat.s1ac.domain.error.DomainExceptionCauses;

import java.util.function.Predicate;

public class IdHandler {
    public static String validateId(Predicate<String> exists, String fieldName, String identifier) {
        if (StringHandler.isNotNullOrEmpty(identifier)) {
            return DomainExceptionCauses.requiredField(fieldName);
        }
        if (!exists(identifier)) {
            return DomainExceptionCauses.resourceNotFound(fieldName);
        }
        return null;
    }
}
