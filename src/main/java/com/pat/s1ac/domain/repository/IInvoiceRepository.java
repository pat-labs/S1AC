package com.pat.s1ac.domain.repository;

import com.pat.s1ac.application.entity.AuditEntity;
import com.pat.s1ac.application.entity.InvoiceEntity;
import com.pat.s1ac.application.entity.InvoiceItemEntity;
import com.pat.s1ac.application.entity.InvoicePaymentDetailEntity;

import java.util.List;

public interface IInvoiceRepository {
    boolean create(AuditEntity audit, InvoiceEntity invoice, List<InvoiceItemEntity> items, InvoicePaymentDetailEntity paymentDetail);
    List<InvoiceEntity> fetch();
    boolean update(InvoiceEntity invoiceEntity);
    InvoiceEntity fetchById(String invoiceId);
    boolean exists(String identifier);
    boolean existsPaymentMethodEnum(Integer paymentMethodEnum);
    boolean existsMoneyCurrencyEnum(Integer moneyCurrencyEnum);
    boolean existsInvoiceDocumentTypeEnum(Integer invoiceDocumentTypeEnum);
}
