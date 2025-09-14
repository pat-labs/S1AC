package com.pat.s1ac.domain.shared.util;

import com.pat.s1ac.domain.shared.error.DomainExceptionCauses;

import java.util.function.Predicate;

public class EnumHandler {
    public static String validateEnum(Predicate<Integer> exists, String fieldName, Integer productUnitEnumValue) {
        if (!IntegerHandler.isValid(productUnitEnumValue)) {
            return DomainExceptionCauses.requiredField(fieldName);
        }
        if (!exists.test(productUnitEnumValue)) {
            return DomainExceptionCauses.resourceNotFound(fieldName);
        }
        return null;
    }
}
