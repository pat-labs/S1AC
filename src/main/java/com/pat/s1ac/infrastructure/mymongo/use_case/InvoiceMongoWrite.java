package com.pat.s1ac.infrastructure.mymongo.use_case;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.pat.s1ac.domain.model.Invoice;
import com.pat.s1ac.domain.model.util.Audit;
import com.pat.s1ac.domain.repository.IInvoiceRepositoryWrite;
import com.pat.s1ac.infrastructure.mymongo.MyMongo;
import com.pat.s1ac.infrastructure.mymongo.error.MongoExceptionCauses;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.Instant;

public class InvoiceMongoWrite implements IInvoiceRepositoryWrite {
    private final MongoCollection<Document> invoicesCollection;

    public InvoiceMongoWrite(MyMongo myMongo) {
        this.invoicesCollection = myMongo.getDatabase().getCollection("invoices");
    }

    @Override
    public String create(Audit audit, Invoice invoice) {
        Document invoiceDoc = new Document("_id", invoice.invoice_id());

        Document auditDoc = new Document()
                .append("write_uid", audit.write_uid())
                .append("write_at", audit.write_at())
                .append("create_uid", audit.create_uid())
                .append("create_at", audit.create_at());
        invoiceDoc.put("audit", auditDoc);

        Document invoiceDataDoc = new Document()
                .append("document_type_enum", invoice.invoice_document_type_enum())
                .append("issue_at", invoice.issue_at())
                .append("company_id", invoice.company_branch_id())
                .append("person_id", invoice.person_id());
        invoiceDoc.put("invoice", invoiceDataDoc);

        try {
            invoicesCollection.insertOne(invoiceDoc);
            return null;
        } catch (MongoWriteException e) {
            return MongoExceptionCauses.writeFailed("invoices", e.getMessage());
        }
    }

    @Override
    public String update(Audit audit, Invoice invoice) {
        try {
            Document filter = new Document("_id", invoice.invoice_id());

            Bson update = Updates.combine(
                    Updates.set("audit.write_uid", audit.write_uid()),
                    Updates.set("audit.write_at", audit.write_at()),
                    Updates.set("invoice.document_type_enum", invoice.invoice_document_type_enum()),
                    Updates.set("invoice.issue_at", invoice.issue_at()),
                    Updates.set("invoice.company_id", invoice.company_branch_id()),
                    Updates.set("invoice.person_id", invoice.person_id())
            );

            UpdateResult result = invoicesCollection.updateOne(filter, update);

            if (result.getModifiedCount() == 0) {
                return MongoExceptionCauses.notFound("Invoice", invoice.invoice_id());
            }

            return null; // Success
        } catch (MongoException e) {
            return MongoExceptionCauses.writeFailed("invoices", e.getMessage());
        }
    }

    @Override
    public String delete(String writeUId, String invoiceId) {
        try {
            Document filter = new Document("_id", invoiceId);
            DeleteResult result = invoicesCollection.deleteOne(filter);

            if (result.getDeletedCount() == 0) {
                return MongoExceptionCauses.notFound("Invoice", invoiceId);
            }

            return null; // Success
        } catch (MongoException e) {
            return MongoExceptionCauses.writeFailed("invoices", e.getMessage());
        }
    }
}
