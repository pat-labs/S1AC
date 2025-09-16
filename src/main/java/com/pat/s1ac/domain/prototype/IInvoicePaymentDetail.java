package com.pat.s1ac.domain.prototype;

public interface IInvoicePaymentDetail {
    Integer payment_method_enum();

    Integer money_currency_enum();

    Double subtotal();

    Double igv();

    Double total_amount();
}
