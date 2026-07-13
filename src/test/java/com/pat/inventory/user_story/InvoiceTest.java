package com.pat.inventory.user_story;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pat.s1ac.application.use_case.InvoiceUseCase;
import com.pat.s1ac.domain.model.Invoice;
import com.pat.s1ac.domain.model.InvoiceItem;
import com.pat.s1ac.domain.model.InvoicePaymentDetail;
import com.pat.s1ac.domain.model.util.Audit;
import com.pat.s1ac.infrastructure.bootstrap.injector.AppInjector;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

//doc/user_story/invoice.md
public class InvoiceTest {
    private InvoiceUseCase invoiceUseCase;
    private Audit audit;
    private Invoice invoice;
    private List<InvoiceItem> invoiceItems;
    private InvoicePaymentDetail invoicePaymentDetail;

    public static void main(String[] args) throws Exception {
        InvoiceTest test = new InvoiceTest();
        test.setUp();
        test.deleteInvoice();
        test.createInvoice();
    }

    public void setUp() throws Exception {
        // Build Guice injector
        Injector injector = Guice.createInjector(new AppInjector());

        // Get the main use case (all deps are injected automatically)
        invoiceUseCase = injector.getInstance(InvoiceUseCase.class);

        // Load test data
        ObjectMapper objectMapper = new ObjectMapper();
        String baseDir = System.getProperty("user.dir");
        String resourcesPath = "src/test/java/resources/";

        audit = objectMapper.readValue(
                Files.readAllBytes(Paths.get(baseDir, resourcesPath, "audit.json")),
                Audit.class
        );
        invoice = objectMapper.readValue(
                Files.readAllBytes(Paths.get(baseDir, resourcesPath, "invoice.json")),
                Invoice.class
        );
        invoiceItems = objectMapper.readValue(
                Files.readAllBytes(Paths.get(baseDir, resourcesPath, "invoice_items.json")),
                new TypeReference<List<InvoiceItem>>() {}
        );
        invoicePaymentDetail = objectMapper.readValue(
                Files.readAllBytes(Paths.get(baseDir, resourcesPath, "invoice_payment_details.json")),
                InvoicePaymentDetail.class
        );
    }

    public void deleteInvoice() {
        System.out.println("Attempting to create invoice...");
        String invoiceError = invoiceUseCase.delete(audit.write_uid(), invoice.invoice_id());
        System.out.println("Invoice creation result: " + invoiceError);
    }

    public void createInvoice() {
        System.out.println("Attempting to create invoice...");
        String invoiceError = invoiceUseCase.create(audit, invoice);
        System.out.println("Invoice creation result: " + invoiceError);
    }
}
