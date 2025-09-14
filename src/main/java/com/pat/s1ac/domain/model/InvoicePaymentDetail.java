package com.pat.s1ac.domain.model;

import com.pat.s1ac.domain.model.util.Money;
import com.pat.s1ac.domain.prototype.IInvoicePaymentDetail;

public record InvoicePaymentDetail(
        Integer payment_method_enum,
        Money subtotal,
        Money igv,
        Money total_amount
) implements IInvoicePaymentDetail {
}
