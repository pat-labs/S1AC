package com.pat.s1ac.domain.model;

import com.pat.s1ac.domain.model.util.Money;

public record InvoiceItem(
    String item_id,
    String product_id,
    double quantity,
    int unit_enum,
    Money unit_price,
    Money total_price
) {}
