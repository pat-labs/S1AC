package com.pat.s1ac.infrastructure.mymongo.use_case;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.pat.s1ac.application.entity.AuditEntity;
import com.pat.s1ac.application.entity.InvoiceEntity;
import com.pat.s1ac.application.entity.InvoiceItemEntity;
import com.pat.s1ac.application.entity.InvoicePaymentDetailEntity;
import com.pat.s1ac.domain.repository.IInvoiceRepository;
import com.pat.s1ac.infrastructure.mymongo.MyMongo;
import org.bson.Document;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

public class InvoiceMongo implements IInvoiceRepository {
    private final MongoCollection<Document> invoicesCollection;
    private final MongoCollection<Document> paymentMethodCollection;
    private final MongoCollection<Document> moneyCurrencyCollection;
    private final MongoCollection<Document> documentTypeCollection;

    public InvoiceMongo(MyMongo myMongo) {
        this.invoicesCollection = myMongo.getDatabase().getCollection("invoices");
        this.paymentMethodCollection = myMongo.getDatabase().getCollection("payment_method_enum");
        this.moneyCurrencyCollection = myMongo.getDatabase().getCollection("money_currency_enum");
        this.documentTypeCollection = myMongo.getDatabase().getCollection("document_type_enum");
    }

    @Override
    public boolean create(AuditEntity audit, InvoiceEntity invoice, List<InvoiceItemEntity> items, InvoicePaymentDetailEntity paymentDetail) {
        Document invoiceDoc = new Document();
        invoiceDoc.put("_id", invoice.invoice_id());

        Document auditDoc = new Document()
                .append("write_uid", audit.write_uid())
                .append("write_at", audit.write_at())
                .append("create_uid", audit.create_uid())
                .append("create_at", audit.create_at());
        invoiceDoc.put("audit", auditDoc);

        Document invoiceDataDoc = new Document()
                .append("document_type_enum", invoice.document_type_enum())
                .append("issue_at", invoice.issue_at())
                .append("company_id", invoice.company_id())
                .append("person_id", invoice.person_id());
        invoiceDoc.put("invoice", invoiceDataDoc);

        List<Document> itemDocs = items.stream()
                .map(item -> new Document()
                        .append("product_id", item.product_id())
                        .append("product_unit_enum", item.product_unit_enum())
                        .append("quantity", item.quantity())
                        .append("price", item.price())
                        .append("description", item.description()))
                .collect(Collectors.toList());
        invoiceDoc.put("items", itemDocs);

        Document paymentDetailDoc = new Document()
                .append("payment_method_enum", paymentDetail.payment_method_enum())
                .append("money_currency_enum", paymentDetail.money_currency_enum())
                .append("total", paymentDetail.total());
        invoiceDoc.put("payment_detail", paymentDetailDoc);

        InsertOneResult result = invoicesCollection.insertOne(invoiceDoc);
        return result.wasAcknowledged();
    }

    @Override
    public List<InvoiceEntity> fetch() {
        // NOTE: This is a stub implementation. The current design with entity wrappers
        // makes it impossible to reconstruct InvoiceEntity objects from the database
        // without access to their validators. This indicates a design issue that
        // should be addressed, perhaps by using DTOs for database transfer.
        return Collections.emptyList();
    }

    @Override
    public boolean update(InvoiceEntity invoiceEntity) {
        // NOTE: This is a stub implementation. A proper implementation would require
        // either a more complex update logic (using $set) or a way to reconstruct
        // the full document with all its nested parts, which is not straightforward
        // with the current method signature.
        return false;
    }

    @Override
    public InvoiceEntity fetchById(String invoiceId) {
        // NOTE: This is a stub implementation for the same reasons as fetch().
        // It's not possible to correctly reconstruct an InvoiceEntity.
        return null;
    }

    @Override
    public boolean exists(String identifier) {
        if (identifier == null || identifier.isBlank()) {
            return false;
        }
        return invoicesCollection.countDocuments(eq("_id", identifier)) > 0;
    }

    @Override
    public boolean existsPaymentMethodEnum(Integer paymentMethodEnum) {
        if (paymentMethodEnum == null) {
            return false;
        }
        return paymentMethodCollection.countDocuments(eq("_id", paymentMethodEnum)) > 0;
    }

    @Override
    public boolean existsMoneyCurrencyEnum(Integer moneyCurrencyEnum) {
        if (moneyCurrencyEnum == null) {
            return false;
        }
        return moneyCurrencyCollection.countDocuments(eq("_id", moneyCurrencyEnum)) > 0;
    }

    @Override
    public boolean existsInvoiceDocumentTypeEnum(Integer invoiceDocumentTypeEnum) {
        if (invoiceDocumentTypeEnum == null) {
            return false;
        }
        return documentTypeCollection.countDocuments(eq("_id", invoiceDocumentTypeEnum)) > 0;
    }
}
