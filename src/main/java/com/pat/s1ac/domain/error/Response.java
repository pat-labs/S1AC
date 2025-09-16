package com.pat.s1ac.domain.error;

public record Response<T>(
        T data,
        Error error
) {
}
