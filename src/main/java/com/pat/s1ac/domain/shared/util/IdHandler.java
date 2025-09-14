package com.pat.s1ac.domain.shared.util;

import com.pat.s1ac.domain.shared.error.DomainExceptionCauses;

import java.util.function.Predicate;

public class IdHandler {
    public static String validateId(Predicate<String> exists, String fieldName, String identifier) {
        if (!StringHandler.isNullOrEmpty(identifier)) {
            return DomainExceptionCauses.requiredField(fieldName);
        }
        if (!exists.test(identifier)) {
            return DomainExceptionCauses.resourceNotFound(fieldName);
        }
        return null;
    }
}
