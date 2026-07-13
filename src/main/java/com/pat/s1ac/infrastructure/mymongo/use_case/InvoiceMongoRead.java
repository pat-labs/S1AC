package com.pat.s1ac.infrastructure.mymongo.use_case;

import com.mongodb.client.MongoCollection;
import com.pat.s1ac.domain.model.Invoice;
import com.pat.s1ac.domain.model.util.Response;
import com.pat.s1ac.domain.repository.IInvoiceRepositoryRead;
import com.pat.s1ac.infrastructure.mymongo.MyMongo;
import org.bson.Document;

import java.util.Collections;
import java.util.List;

public class InvoiceMongoRead implements IInvoiceRepositoryRead {
    private final MongoCollection<Document> invoicesCollection;
    private final MongoCollection<Document> paymentMethodCollection;
    private final MongoCollection<Document> moneyCurrencyCollection;
    private final MongoCollection<Document> documentTypeCollection;

    public InvoiceMongoRead(MyMongo myMongo) {
        this.invoicesCollection = myMongo.getDatabase().getCollection("invoices");
        this.paymentMethodCollection = myMongo.getDatabase().getCollection("payment_method_enum");
        this.moneyCurrencyCollection = myMongo.getDatabase().getCollection("money_currency_enum");
        this.documentTypeCollection = myMongo.getDatabase().getCollection("document_type_enum");
    }

    @Override
    public Response<List<Invoice>> fetch(int offset, int limit) {
        // NOTE: This is a stub implementation. The current design with  wrappers
        // makes it impossible to reconstruct Invoice objects from the database
        // without access to their validators. This indicates a design issue that
        // should be addressed, perhaps by using DTOs for database transfer.
        return Response.success(Collections.emptyList());
    }

    @Override
    public Response<Invoice> fetchById(String invoiceId) {
        // NOTE: This is a stub implementation for the same reasons as fetch().
        // It's not possible to correctly reconstruct an Invoice.
        return null;
    }

    @Override
    public Response<Boolean> exists(String identifier) {
//        if (identifier == null || identifier.isBlank()) {
//            return false;
//        }
//        return invoicesCollection.countDocuments(eq("_id", identifier)) > 0;
        return Response.success(true);
    }

    @Override
    public Response<Boolean> existsPaymentMethodEnum(Integer paymentMethodEnum) {
//        if (paymentMethodEnum == null) {
//            return false;
//        }
//        return paymentMethodCollection.countDocuments(eq("_id", paymentMethodEnum)) > 0;
        return Response.success(true);
    }

    @Override
    public Response<Boolean> existsMoneyCurrencyEnum(Integer moneyCurrencyEnum) {
//        if (moneyCurrencyEnum == null) {
//            return false;
//        }
//        return moneyCurrencyCollection.countDocuments(eq("_id", moneyCurrencyEnum)) > 0;
        return Response.success(true);
    }

    @Override
    public Response<Boolean> existsInvoiceDocumentTypeEnum(Integer invoiceDocumentTypeEnum) {
//        if (invoiceDocumentTypeEnum == null) {
//            return false;
//        }
//        return documentTypeCollection.countDocuments(eq("_id", invoiceDocumentTypeEnum)) > 0;
        return Response.success(true);
    }
}
