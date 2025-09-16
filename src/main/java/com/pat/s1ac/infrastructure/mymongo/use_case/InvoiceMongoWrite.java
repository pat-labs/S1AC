package com.pat.s1ac.infrastructure.mymongo.use_case;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.pat.s1ac.domain.model.Invoice;
import com.pat.s1ac.domain.model.util.Audit;
import com.pat.s1ac.domain.repository.IInvoiceRepositoryWrite;
import com.pat.s1ac.infrastructure.mymongo.MyMongo;
import org.bson.Document;

public class InvoiceMongoWrite implements IInvoiceRepositoryWrite {
    private final MongoCollection<Document> invoicesCollection;

    public InvoiceMongoWrite(MyMongo myMongo) {
        this.invoicesCollection = myMongo.getDatabase().getCollection("invoices");
    }

    @Override
    public boolean create(Audit audit, Invoice invoice) {
        Document invoiceDoc = new Document();
        invoiceDoc.put("_id", invoice.invoice_id());

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

        InsertOneResult result = invoicesCollection.insertOne(invoiceDoc);
        return result.wasAcknowledged();
    }
}
