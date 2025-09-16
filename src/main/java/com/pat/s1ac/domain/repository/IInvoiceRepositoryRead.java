package com.pat.s1ac.domain.repository;

import com.pat.s1ac.domain.model.Invoice;

import java.util.List;

public interface IInvoiceRepositoryRead {
    List<Invoice> fetch();

    Invoice fetchById(String invoiceId);

    boolean exists(String identifier);

    boolean existsPaymentMethodEnum(Integer paymentMethodEnum);

    boolean existsMoneyCurrencyEnum(Integer moneyCurrencyEnum);

    boolean existsInvoiceDocumentTypeEnum(Integer invoiceDocumentTypeEnum);
}
