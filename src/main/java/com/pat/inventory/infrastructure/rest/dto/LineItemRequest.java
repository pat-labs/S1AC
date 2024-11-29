package com.pat.inventory.infrastructure.rest.dto;

public record LineItemRequest(
        String itemId,
        String description,
        double price) {
}
