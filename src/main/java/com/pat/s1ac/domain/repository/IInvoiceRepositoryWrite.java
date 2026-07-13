package com.pat.s1ac.domain.repository;

import com.pat.s1ac.domain.model.Invoice;
import com.pat.s1ac.domain.model.util.Audit;

import java.util.List;

public interface IInvoiceRepositoryWrite {
    String delete(String writeUId, String invoiceId);
    String create(Audit audit, Invoice invoice);
    String update(Audit audit, Invoice invoice);
}
