package com.pat.s1ac.domain.prototype;

import com.pat.s1ac.domain.model.InvoicePaymentDetail;
import com.pat.s1ac.domain.model.util.Audit;

import java.util.List;

public interface IInvoice {
    String invoice_id();
    Integer document_type_enum();
    String issue_at();
    String company_id();
    String person_id();
    List<String> invoice_items_id();
    InvoicePaymentDetail payment_details();
    Audit audit();
}
