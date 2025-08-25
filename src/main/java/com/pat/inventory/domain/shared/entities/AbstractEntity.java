package com.pat.inventory.domain.shared.entities;

import java.util.List;
import java.util.function.Function;

public abstract class AbstractEntity implements BaseEntity {
    protected boolean entityExists;

    protected <T> void validateField(T field, Function<T, String> validationFunction, List<String> errors) {
        if (field != null) {
            String error = validationFunction.apply(field);
            if (error != null) {
                errors.add(error);
            }
        }
    }
}
