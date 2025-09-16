package com.pat.s1ac.domain.validator.util;

import com.pat.s1ac.domain.error.DomainExceptionCauses;

import java.util.function.Predicate;

public class EnumHandler {
    public static String validateEnum(Predicate<Integer> exists, String fieldName, Integer productUnitEnumValue) {
        if (!IntegerHandler.isNotValid(productUnitEnumValue)) {
            return DomainExceptionCauses.requiredField(fieldName);
        }
        if (!exists.test(productUnitEnumValue)) {
            return DomainExceptionCauses.resourceNotFound(fieldName);
        }
        return null;
    }
}
