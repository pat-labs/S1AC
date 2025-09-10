package com.pat.s1ac.domain.model;

import com.pat.s1ac.domain.model.util.Money;

public record PaymentDetails(
    String payment_details_id,
    int payment_method_enum,
    Money subtotal,
    Money igv,
    Money total_amount
) {}
