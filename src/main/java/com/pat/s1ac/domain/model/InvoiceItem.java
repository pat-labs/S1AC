package com.pat.s1ac.domain.model;

public record Item(
    String item_id,
    String description,
    int quantity,
    int unit,
    int unit_price_currency,
    double unit_price,
    int total_price_currency,
    double total_price
) {}
