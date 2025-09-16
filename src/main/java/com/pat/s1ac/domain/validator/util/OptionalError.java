package com.pat.s1ac.domain.validator.util;

public final class OptionalError<T> {
    private final T value;
    private final String error;

    private OptionalError(T value, String error) {
        this.value = value;
        this.error = error;
    }

    public static <T> OptionalError<T> of(T value) {
        return new OptionalError<>(value, null);
    }

    public static <T> OptionalError<T> error(String error) {
        return new OptionalError<>(null, error);
    }

    public boolean hasError() {
        return error != null;
    }

    public String getError() {
        return error;
    }

    public T getValue() {
        return value;
    }

    public String hasErrorOrElse(String errorResult, String valueResult) {
        return hasError() ? errorResult : valueResult;
    }

    @Override
    public String toString() {
        return hasError() ? "Error: " + error : String.valueOf(value);
    }
}

