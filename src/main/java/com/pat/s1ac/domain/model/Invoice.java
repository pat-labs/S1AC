package com.pat.s1ac.domain.model;

import com.pat.s1ac.domain.model.util.Audit;
import com.pat.s1ac.domain.prototype.IInvoice;

import java.util.List;

public record Invoice(
        String invoice_id,
        Integer invoice_document_type_enum,
        String issue_at,
        String company_branch_id,
        String person_id
) implements IInvoice {
}
