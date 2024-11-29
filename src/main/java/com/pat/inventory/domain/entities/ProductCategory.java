package com.pat.inventory.domain.entities;

public class ProductCategory {
    private final String productCategoryId;
    private final String description;

    public ProductCategory(String productCategoryId, String description) {
        this.productCategoryId = productCategoryId;
        this.description = description;
    }
}
