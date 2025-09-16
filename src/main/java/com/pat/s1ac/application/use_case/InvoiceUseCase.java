package com.pat.s1ac.application.use_case;


import com.pat.s1ac.domain.broker.IBrokerProducer;
import com.pat.s1ac.domain.model.Invoice;
import com.pat.s1ac.domain.model.util.Audit;
import com.pat.s1ac.domain.repository.IInvoiceRepositoryWrite;
import com.pat.s1ac.domain.validator.AuditValidator;
import com.pat.s1ac.domain.validator.InvoiceValidator;

public class InvoiceUseCase {
    private final IInvoiceRepositoryWrite invoiceRepository;
    private final IBrokerProducer invoiceBrokerProducer;
    private final AuditValidator auditValidator;
    private final InvoiceValidator invoiceValidator;

    public InvoiceUseCase(AuditValidator auditValidator,
                          InvoiceValidator invoiceValidator,
                          IInvoiceRepositoryWrite invoiceRepositoryWrite,
                          IBrokerProducer invoiceBrokerProducer) {

        this.auditValidator = auditValidator;
        this.invoiceValidator = invoiceValidator;
        this.invoiceRepository = invoiceRepositoryWrite;
        this.invoiceBrokerProducer = invoiceBrokerProducer;
    }

    public boolean create(Audit audit,
                          Invoice invoice) {
        auditValidator.isValid(audit);
        invoiceValidator.isValid(invoice);
//        for (InvoiceItem item : invoiceItems) {
//            invoiceItemValidator.isValid(item);
//        }
//        invoicePaymentDetailValidator.isValid(invoicePaymentDetail);
        boolean success = invoiceRepository.create(audit, invoice);
        if (success) {
            invoiceBrokerProducer.sendMessage("Invoice created: " + invoice.invoice_id());
        }
        return success;
    }

//    public boolean update(Audit audit,
//                          Invoice invoice,
//                          List<InvoiceItem> invoiceItems,
//                          InvoicePaymentDetail invoicePaymentDetail) {
//        auditValidator.isPartialValid(audit);
//        invoiceValidator.isPartialValid(invoice);
//        for (InvoiceItem item : invoiceItems) {
//            invoiceItemValidator.isPartialValid(item);
//        }
//        invoicePaymentDetailValidator.isPartialValid(invoicePaymentDetail);
//        return invoiceRepository.update(audit, invoice, invoiceItems, invoicePaymentDetail);
//    }

//    public List<Invoice> fetch() {
//        return invoiceRepository.fetch();
//    }
//
//    public Invoice fetchById(String invoiceId) {
//        return invoiceRepository.fetchById(invoiceId);
//    }
}
