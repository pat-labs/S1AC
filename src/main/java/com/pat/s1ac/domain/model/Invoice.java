package com.pat.s1ac.domain.model;

import com.pat.s1ac.domain.model.util.Audit;
import com.pat.s1ac.domain.prototype.IInvoice;

import java.util.List;

public record Invoice(
        String invoice_id,
        Integer document_type_enum,
        String issue_at,
        String company_id,
        String person_id,
        List<String> invoice_items_id,
        InvoicePaymentDetail payment_details,
        Audit audit
) implements IInvoice {
}
