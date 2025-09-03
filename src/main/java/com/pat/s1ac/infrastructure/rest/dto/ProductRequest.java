package com.pat.s1ac.infrastructure.rest.dto;

public record ProductRequest(
        String productId,
        String description,
        int statusValue) {
    public ProductRequest(String productId, String description) {
        this(productId, description, 1);
    }
}
