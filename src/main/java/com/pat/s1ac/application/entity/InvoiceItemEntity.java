package com.pat.s1ac.application.entity;

import com.pat.s1ac.domain.model.InvoiceItem;
import com.pat.s1ac.domain.validator.IValidator;

public class InvoiceItemEntity {
    private InvoiceItem invoiceItem;

    public InvoiceItemEntity(IValidator validator, InvoiceItem data) {
        validator.isValid(data);
        this.invoiceItem = data;
    }
}
