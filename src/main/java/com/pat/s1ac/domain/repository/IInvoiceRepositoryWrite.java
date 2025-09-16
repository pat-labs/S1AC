package com.pat.s1ac.domain.repository;

import com.pat.s1ac.domain.model.Invoice;
import com.pat.s1ac.domain.model.util.Audit;

public interface IInvoiceRepositoryWrite {
    boolean create(Audit audit, Invoice invoice);

//    boolean update(Audit audit, Invoice invoice, List<InvoiceItem> items, InvoicePaymentDetail paymentDetail);
}
