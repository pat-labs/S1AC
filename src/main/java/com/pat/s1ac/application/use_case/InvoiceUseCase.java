package com.pat.s1ac.application.use_case;

import com.pat.s1ac.domain.broker.IBrokerProducer;
import com.pat.s1ac.domain.model.Invoice;
import com.pat.s1ac.domain.model.util.Audit;
import com.pat.s1ac.domain.model.util.Response;
import com.pat.s1ac.domain.repository.IInvoiceRepositoryRead;
import com.pat.s1ac.domain.repository.IInvoiceRepositoryWrite;
import com.pat.s1ac.domain.validator.AuditValidator;
import com.pat.s1ac.domain.validator.InvoiceValidator;

import java.util.List;

public class InvoiceUseCase {
    private final IInvoiceRepositoryWrite invoiceRepositoryWrite;
    private final IInvoiceRepositoryRead invoiceRepositoryRead;
    private final IBrokerProducer invoiceBrokerProducer;
    private final AuditValidator auditValidator;
    private final InvoiceValidator invoiceValidator;

    public InvoiceUseCase(AuditValidator auditValidator,
                          InvoiceValidator invoiceValidator,
                          IInvoiceRepositoryRead invoiceRepositoryRead,
                          IInvoiceRepositoryWrite invoiceRepositoryWrite,
                          IBrokerProducer invoiceBrokerProducer) {

        this.auditValidator = auditValidator;
        this.invoiceValidator = invoiceValidator;
        this.invoiceRepositoryRead = invoiceRepositoryRead;
        this.invoiceRepositoryWrite = invoiceRepositoryWrite;
        this.invoiceBrokerProducer = invoiceBrokerProducer;
    }

    public Response<String> create(Audit audit, Invoice invoice) {
        String auditError = auditValidator.isValid(audit);
        if (auditError != null) return Response.domainError(auditError);
        String invoiceError = invoiceValidator.isValid(invoice);
        if (invoiceError != null) return Response.domainError(invoiceError);

        String invoiceRepositoryError = invoiceRepositoryWrite.create(audit, invoice);
        if (invoiceRepositoryError != null) {
            return Response.infrastructureError(invoiceRepositoryError);
        }

        return Response.success(invoice.invoice_id());
    }

    public Response<Void> update(Audit audit, Invoice invoice) {
        String auditError = auditValidator.isPartialValid(audit);
        if (auditError != null) return Response.domainError(auditError);
        String invoiceError = invoiceValidator.isPartialValid(invoice);
        if (invoiceError != null) return Response.domainError(invoiceError);

        String invoiceRepositoryError = invoiceRepositoryWrite.update(audit, invoice);
        if (invoiceRepositoryError != null) {
            return Response.infrastructureError(invoiceRepositoryError);
        }

        return Response.success(null);
    }

    public Response<Void> delete(String auditId, String invoiceId) {
        var auditError = auditValidator.isValidId(auditId);
        if (auditError != null) return auditError;
        var invoiceError = invoiceValidator.isValidId(invoiceId);
        if (invoiceError != null) return invoiceError;

        String invoiceRepositoryError = invoiceRepositoryWrite.delete(auditId, invoiceId);
        if (invoiceRepositoryError != null) {
            return Response.infrastructureError(invoiceRepositoryError);
        }

        return Response.success(null);
    }

    public Response<List<Invoice>> fetch(Audit audit, int offset, int limit) {
        String auditError = auditValidator.isValid(audit);
        if (auditError != null) return Response.domainError(auditError);
        return invoiceRepositoryRead.fetch(offset, limit);
    }

    public Response<Invoice> fetchById(Audit audit, String invoiceId) {
        String auditError = auditValidator.isValid(audit);
        if (auditError != null) return Response.domainError(auditError);
        return invoiceRepositoryRead.fetchById(invoiceId);
    }
}
