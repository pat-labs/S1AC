package com.pat.s1ac.domain.model.util;

public record Response<T>(
        T data,
        ErrorType errorType,
        String error
) {

    public enum ErrorType {
        NONE,
        APPLICATION,
        DOMAIN,
        INFRASTRUCTURE
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(data, ErrorType.NONE, null);
    }

    public static <T> Response<T> applicationError(String applicationError) {
        return new Response<>(null, ErrorType.APPLICATION, applicationError);
    }

    public static <T> Response<T> domainError(String domainError) {
        return new Response<>(null, ErrorType.DOMAIN, domainError);
    }

    public static <T> Response<T> infrastructureError(String infrastructureError) {
        return new Response<>(null, ErrorType.INFRASTRUCTURE, infrastructureError);
    }
}
