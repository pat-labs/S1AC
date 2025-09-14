package com.pat.s1ac.application.entity;

import com.pat.s1ac.domain.model.InvoicePaymentDetail;
import com.pat.s1ac.domain.validator.IValidator;

public class InvoicePaymentDetailEntity {
    private InvoicePaymentDetail invoicePaymentDetail;

    public InvoicePaymentDetailEntity(IValidator validator, InvoicePaymentDetail data) {
        validator.isValid(data);
        this.invoicePaymentDetail = data;
    }
}
