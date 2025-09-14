package com.pat.s1ac.application.entity;

import com.pat.s1ac.domain.model.Invoice;
import com.pat.s1ac.domain.prototype.IInvoice;
import com.pat.s1ac.domain.validator.IValidator;

public class InvoiceEntity implements IInvoice {
    private Invoice invoice;

    public InvoiceEntity(IValidator validator, Invoice invoice) throws IllegalArgumentException{
        validator.isValid(invoice);
        this.invoice = invoice;
    }
}
