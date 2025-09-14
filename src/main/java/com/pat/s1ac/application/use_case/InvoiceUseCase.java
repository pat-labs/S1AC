package com.pat.s1ac.application.use_case;

import com.pat.s1ac.application.entity.AuditEntity;
import com.pat.s1ac.application.entity.InvoiceEntity;
import com.pat.s1ac.application.entity.InvoiceItemEntity;
import com.pat.s1ac.application.entity.InvoicePaymentDetailEntity;
import com.pat.s1ac.domain.broker.IBrokerProducer;
import com.pat.s1ac.domain.repository.IInvoiceRepository;

import java.util.List;

public class InvoiceUseCase {
    private final IInvoiceRepository invoiceRepository;
    private final IBrokerProducer invoiceBrokerProducer;

    public InvoiceUseCase(IInvoiceRepository invoiceRepository, IBrokerProducer invoiceBrokerProducer) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceBrokerProducer = invoiceBrokerProducer;
    }

    public boolean create(AuditEntity auditEntity, InvoiceEntity invoiceEntity, List<InvoiceItemEntity> invoiceItemsEntity, InvoicePaymentDetailEntity invoicePaymentDetailEntity) {
        boolean success = invoiceRepository.create(auditEntity, invoiceEntity, invoiceItemsEntity, invoicePaymentDetailEntity);
        if (success) {
            invoiceBrokerProducer.sendMessage("Invoice created: " + invoiceEntity.model().invoice_id());
        }
        return success;
    }

    public List<InvoiceEntity> fetch() {
        return invoiceRepository.fetch();
    }

    public boolean update(InvoiceEntity invoiceEntity) {
        return invoiceRepository.update(invoiceEntity);
    }

    public InvoiceEntity fetchById(String invoiceId) {
        return invoiceRepository.fetchById(invoiceId);
    }
}
