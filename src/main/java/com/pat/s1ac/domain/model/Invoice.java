package com.pat.s1ac.domain.model;

import com.pat.s1ac.domain.model.util.Audit;

public record Invoice(
        String invoice_id,
        int document_type_enum,
        String issue_at,
        String company_id,
        String person_id,
        String invoice_payment_details,
        Audit audit
) {
}
