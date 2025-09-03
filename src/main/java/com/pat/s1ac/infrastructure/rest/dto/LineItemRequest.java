package com.pat.s1ac.infrastructure.rest.dto;

public record LineItemRequest(
        String itemId,
        String description,
        double price) {
}
