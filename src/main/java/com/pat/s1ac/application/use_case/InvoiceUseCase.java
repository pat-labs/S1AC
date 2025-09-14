package com.pat.s1ac.application.use_case;


import com.pat.s1ac.domain.broker.IBrokerProducer;
import com.pat.s1ac.domain.model.Invoice;
import com.pat.s1ac.domain.model.InvoiceItem;
import com.pat.s1ac.domain.model.InvoicePaymentDetail;
import com.pat.s1ac.domain.model.util.Audit;
import com.pat.s1ac.domain.repository.IInvoiceRepository;
import com.pat.s1ac.domain.validator.AuditValidator;
import com.pat.s1ac.domain.validator.InvoiceItemValidator;
import com.pat.s1ac.domain.validator.InvoicePaymentDetailValidator;
import com.pat.s1ac.domain.validator.InvoiceValidator;

import java.util.List;

public class InvoiceUseCase {
    private AuditValidator auditValidator;
    private InvoiceValidator invoiceValidator;
    private InvoiceItemValidator invoiceItemValidator;
    private InvoicePaymentDetailValidator invoicePaymentDetailValidator;
    private final IInvoiceRepository invoiceRepository;
    private final IBrokerProducer invoiceBrokerProducer;

    public InvoiceUseCase(AuditValidator auditValidator,
                          InvoiceValidator invoiceValidator,
                          InvoiceItemValidator invoiceItemValidator,
                          InvoicePaymentDetailValidator invoicePaymentDetailValidator,
                          IInvoiceRepository invoiceRepository,
                          IBrokerProducer invoiceBrokerProducer) {

        this.auditValidator = auditValidator;
        this.invoiceValidator = invoiceValidator;
        this.invoiceItemValidator = invoiceItemValidator;
        this.invoicePaymentDetailValidator = invoicePaymentDetailValidator;
        this.invoiceRepository = invoiceRepository;
        this.invoiceBrokerProducer = invoiceBrokerProducer;
    }

    public boolean create(Audit audit,
                          Invoice invoice,
                          List<InvoiceItem> invoiceItems,
                          InvoicePaymentDetail invoicePaymentDetail) {
        auditValidator.isValid(audit);
        invoiceValidator.isValid(invoice);
        for (InvoiceItem item : invoiceItems) {
            invoiceItemValidator.isValid(item);
        }
        invoicePaymentDetailValidator.isValid(invoicePaymentDetail);
        boolean success = invoiceRepository.create(audit, invoice, invoiceItems, invoicePaymentDetail);
        if (success) {
            invoiceBrokerProducer.sendMessage("Invoice created: " + invoice.invoice_id());
        }
        return success;
    }

    public boolean update(Audit audit,
                          Invoice invoice,
                          List<InvoiceItem> invoiceItems,
                          InvoicePaymentDetail invoicePaymentDetail) {
        auditValidator.isPartialValid(audit);
        invoiceValidator.isPartialValid(invoice);
        for (InvoiceItem item : invoiceItems) {
            invoiceItemValidator.isPartialValid(item);
        }
        invoicePaymentDetailValidator.isPartialValid(invoicePaymentDetail);
        return invoiceRepository.update(audit, invoice, invoiceItems, invoicePaymentDetail);
    }

    public List<Invoice> fetch() {
        return invoiceRepository.fetch();
    }

    public Invoice fetchById(String invoiceId) {
        return invoiceRepository.fetchById(invoiceId);
    }
}
