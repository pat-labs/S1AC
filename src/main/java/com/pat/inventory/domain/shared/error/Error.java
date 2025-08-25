package com.pat.inventory.domain.shared.error;

public record Error(
    int type,
    String message
) {}
