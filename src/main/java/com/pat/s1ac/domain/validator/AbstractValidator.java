package com.pat.s1ac.domain.validator;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public abstract class AbstractValidator<T> {

    public String isValid(T model) {
        return runValidations(model, fullValidations());
    }

    public String isPartialValid(T model) {
        return runValidations(model, partialValidations());
    }

    protected abstract List<Function<T, String>> fullValidations();

    protected abstract List<Function<T, String>> partialValidations();

    protected String runValidations(T model, List<Function<T, String>> rules) {
        List<String> errors = rules.stream()
                .map(rule -> rule.apply(model))
                .filter(Objects::nonNull)
                .toList();

        if (!errors.isEmpty()) {
            return String.join(", ", errors);
        }
        return null;
    }
}
