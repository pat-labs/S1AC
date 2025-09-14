package com.pat.s1ac.domain.prototype;

import com.pat.s1ac.domain.model.util.Money;

public interface IInvoicePaymentDetail {
    Integer payment_method_enum();
    Money subtotal();
    Money igv();
    Money total_amount();
}
