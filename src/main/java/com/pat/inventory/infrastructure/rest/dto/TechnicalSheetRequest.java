package com.pat.inventory.infrastructure.rest.dto;

public record TechnicalSheetRequest(
        String brand,
        String model,
        String weight,
        String dimensions,
        int depreciation,
        String color,
        String material,
        String linkedListCategory) {
}
