package com.pat.inventory.domain.shared.error;

public record Response<T>(
    T data,
    Error error
) {}
