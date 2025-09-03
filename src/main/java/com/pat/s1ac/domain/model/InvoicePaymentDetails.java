package com.pat.s1ac.domain.model;

public record InvoicePaymentDetails(
    String invoice_payment_details_id,
    int method,
    int subtotal_currency,
    double subtotal,
    int igv_currency,
    double igv,
    int total_amount_currency,
    double total_amount 
) {}
