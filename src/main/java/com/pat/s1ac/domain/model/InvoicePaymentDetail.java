package com.pat.s1ac.domain.model;

import com.pat.s1ac.domain.prototype.IInvoicePaymentDetail;

public record InvoicePaymentDetail(
        Integer payment_method_enum,
        Integer money_currency_enum,
        Double subtotal,
        Double igv,
        Double total_amount
) implements IInvoicePaymentDetail {
}
