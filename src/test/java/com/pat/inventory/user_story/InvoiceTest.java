package com.pat.inventory.user_story;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pat.s1ac.application.use_case.InvoiceUseCase;
import com.pat.s1ac.domain.model.Invoice;
import com.pat.s1ac.domain.model.InvoiceItem;
import com.pat.s1ac.domain.model.InvoicePaymentDetail;
import com.pat.s1ac.domain.model.util.Audit;
import com.pat.s1ac.domain.validator.AuditValidator;
import com.pat.s1ac.domain.validator.InvoiceItemValidator;
import com.pat.s1ac.domain.validator.InvoicePaymentDetailValidator;
import com.pat.s1ac.domain.validator.InvoiceValidator;
import com.pat.s1ac.infrastructure.bootstrap.Bootstrap;
import com.pat.s1ac.infrastructure.bootstrap.Env;
import com.pat.s1ac.infrastructure.mygrpc.service.CompanyService;
import com.pat.s1ac.infrastructure.mygrpc.service.PersonService;
import com.pat.s1ac.infrastructure.mygrpc.service.ProductService;
import com.pat.s1ac.infrastructure.mymongo.use_case.InvoiceMongoRead;
import com.pat.s1ac.infrastructure.mymongo.use_case.InvoiceMongoWrite;
import com.pat.s1ac.infrastructure.mymongo.MyMongo;
import com.pat.s1ac.infrastructure.myrabbitmq.use_case.InvoiceRabbitMQ;
import com.pat.s1ac.infrastructure.myrabbitmq.MyRabbitmq;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

//Feature: Register and Manage Invoices
//As a user, I want to easily register a new invoice and manage its details
//So that I can accurately track my expenses, ensure payments are made on time, and have a clear record for my financial reports.
//I want to be able to:
// - Input all relevant invoice details, such as the invoice number, vendor name, date, and total amount.
// - Edit an invoice's details if I make a mistake or if information changes.
// - Deactivate an invoice if it's no longer valid or if it was entered in error.
// - View a list of all my invoices.
//Is done when:
// - A new invoice is successfully registered and appears in my invoice list.
// - I can edit the details of an existing invoice and save the changes.
// - I can deactivate an invoice, and it no longer appears in my active invoice list.
// - The system sends me a confirmation message after I register or update an invoice.
public class InvoiceTest {
    private AuditValidator auditValidator;
    private ProductService productService;
    private PersonService personService;
    private CompanyService companyService;
    private Audit audit;
    private Invoice invoice;
    private List<InvoiceItem> invoiceItems;
    private InvoicePaymentDetail invoicePaymentDetail;
    private InvoiceItemValidator invoiceItemValidator;
    private InvoicePaymentDetailValidator invoicePaymentDetailValidator;
    private InvoiceValidator invoiceValidator;
    private InvoiceUseCase invoiceUseCase;

    public static void main(String[] args) throws Exception {
        InvoiceTest test = new InvoiceTest();
        test.setUp();
        test.createInvoice();
    }

    public void setUp() throws Exception {
        Bootstrap bootstrap = Env.fromFile(".env");
        MyMongo myMongo = new MyMongo(bootstrap.mongoConfig());
        InvoiceMongoWrite invoiceMongoWrite = new InvoiceMongoWrite(myMongo);
        InvoiceMongoRead invoiceMongoRead = new InvoiceMongoRead(myMongo);
        MyRabbitmq myRabbitmq = new MyRabbitmq(bootstrap.rabbitMQConfig());
        InvoiceRabbitMQ invoiceRabbitMQ = new InvoiceRabbitMQ(myRabbitmq);

        personService = new PersonService();
        companyService = new CompanyService();
        productService = new ProductService();

        auditValidator = new AuditValidator(personService::exists);
        invoiceItemValidator = new InvoiceItemValidator(
                productService
        );
        invoicePaymentDetailValidator = new InvoicePaymentDetailValidator(
                invoiceMongoRead
        );
        invoiceValidator = new InvoiceValidator(
                invoiceMongoRead,
                companyService,
                personService
        );

        invoiceUseCase = new InvoiceUseCase(auditValidator,
                invoiceValidator,
                invoiceMongoWrite,
                invoiceRabbitMQ);

        ObjectMapper objectMapper = new ObjectMapper();
        String baseDir = System.getProperty("user.dir");
        String resourcesPath = "src/test/java/resources/";

        String auditJson = new String(Files.readAllBytes(Paths.get(baseDir, resourcesPath, "audit.json")));
        audit = objectMapper.readValue(auditJson, Audit.class);
        String invoiceJson = new String(Files.readAllBytes(Paths.get(baseDir, resourcesPath, "invoice.json")));
        invoice = objectMapper.readValue(invoiceJson, Invoice.class);
        String invoiceItemsJson = new String(Files.readAllBytes(Paths.get(baseDir, resourcesPath, "invoice_items.json")));
        invoiceItems = objectMapper.readValue(invoiceItemsJson, new TypeReference<List<InvoiceItem>>() {});
        String invoicePaymentDetailsJson = new String(Files.readAllBytes(Paths.get(baseDir, resourcesPath, "invoice_payment_details.json")));
        invoicePaymentDetail = objectMapper.readValue(invoicePaymentDetailsJson, InvoicePaymentDetail.class);
    }

    public boolean createInvoice() {
        return invoiceUseCase.create(audit, invoice);
    }
}
