package com.pat.s1ac.domain.repository;

import com.pat.s1ac.domain.model.Invoice;
import com.pat.s1ac.domain.model.InvoiceItem;
import com.pat.s1ac.domain.model.InvoicePaymentDetail;
import com.pat.s1ac.domain.model.util.Audit;

import java.util.List;

public interface IInvoiceRepository {
    boolean create(Audit audit, Invoice invoice, List<InvoiceItem> items, InvoicePaymentDetail paymentDetail);
    List<Invoice> fetch();
    boolean update(Audit audit, Invoice invoice, List<InvoiceItem> items, InvoicePaymentDetail paymentDetail);
    Invoice fetchById(String invoiceId);
    boolean exists(String identifier);
    boolean existsPaymentMethodEnum(Integer paymentMethodEnum);
    boolean existsMoneyCurrencyEnum(Integer moneyCurrencyEnum);
    boolean existsInvoiceDocumentTypeEnum(Integer invoiceDocumentTypeEnum);
}
