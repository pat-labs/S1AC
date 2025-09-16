package com.pat.s1ac.domain.prototype;

import com.pat.s1ac.domain.model.InvoicePaymentDetail;
import com.pat.s1ac.domain.model.util.Audit;

import java.util.List;

public interface IInvoice {
    String invoice_id();

    Integer invoice_document_type_enum();

    String issue_at();

    String company_branch_id();

    String person_id();
}
