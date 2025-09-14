package com.pat.s1ac.domain.validator;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public abstract class AbstractValidator<T> {

    public void isValid(T model) throws IllegalArgumentException {
        runValidations(model, fullValidations());
    }

    public void isPartialValid(T model) throws IllegalArgumentException {
        runValidations(model, partialValidations());
    }

    protected abstract List<Function<T, String>> fullValidations();

    protected abstract List<Function<T, String>> partialValidations();

    protected void runValidations(T model, List<Function<T, String>> rules) {
        List<String> errors = rules.stream()
                .map(rule -> rule.apply(model))
                .filter(Objects::nonNull)
                .toList();

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }
}
