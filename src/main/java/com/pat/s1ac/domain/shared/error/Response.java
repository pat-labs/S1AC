package com.pat.s1ac.domain.shared.error;

public record Response<T>(
        T data,
        Error error
) {
}
