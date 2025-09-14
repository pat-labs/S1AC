package com.pat.inventory.user_story;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pat.s1ac.application.entity.AuditEntity;
import com.pat.s1ac.application.entity.InvoiceEntity;
import com.pat.s1ac.application.entity.InvoiceItemEntity;
import com.pat.s1ac.application.entity.InvoicePaymentDetailEntity;
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
import com.pat.s1ac.infrastructure.mygrpc.service.CompanyService;
import com.pat.s1ac.infrastructure.mygrpc.service.PersonService;
import com.pat.s1ac.infrastructure.mygrpc.service.ProductService;
import com.pat.s1ac.infrastructure.mymongo.use_case.InvoiceMongo;
import com.pat.s1ac.infrastructure.mymongo.MyMongo;
import com.pat.s1ac.infrastructure.myrabbitmq.use_case.InvoiceRabbitMQ;
import com.pat.s1ac.infrastructure.myrabbitmq.MyRabbitmq;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

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
public class  InvoiceTest {
    private Bootstrap bootstrap;
    private AuditValidator auditValidator;
    private ProductService productService;
    private PersonService personService;
    private CompanyService companyService;
    private Audit auditRequest;
    private Invoice invoiceRequest;
    private List<InvoiceItem> invoiceItemsRequest;
    private InvoicePaymentDetail invoicePaymentDetailRequest;
    private InvoiceItemValidator invoiceItemValidator;
    private InvoicePaymentDetailValidator invoicePaymentDetailValidator;
    private InvoiceValidator invoiceValidator;
    private MyMongo myMongo;
    private MyRabbitmq myRabbitmq;
    private InvoiceMongo invoiceRepository;
    private InvoiceRabbitMQ invoiceRabbitMQ;
    private InvoiceUseCase invoiceUseCase;


    public void setUp() throws IOException, TimeoutException {
        bootstrap = Bootstrap.fromEnv();

        auditValidator = new AuditValidator(personService::exists);
        invoiceItemValidator = new InvoiceItemValidator(
                productService::exists,
                productService::existsUnitEnum
        );
        invoicePaymentDetailValidator = new InvoicePaymentDetailValidator(
                invoiceRepository::existsPaymentMethodEnum,
                invoiceRepository::existsMoneyCurrencyEnum
        );
        invoiceValidator = new InvoiceValidator(
                invoiceRepository::existsInvoiceDocumentTypeEnum,
                companyService::exists,
                personService::exists
        );

        myMongo = new MyMongo(bootstrap.mongoConfig());
        invoiceRepository = new InvoiceMongo(myMongo);

        myRabbitmq = new MyRabbitmq(bootstrap.rabbitMQConfig());
        invoiceRabbitMQ = new InvoiceRabbitMQ(myRabbitmq);

        invoiceUseCase = new InvoiceUseCase(invoiceRepository, invoiceRabbitMQ);

        ObjectMapper objectMapper = new ObjectMapper();
        String auditJson = new String(Files.readAllBytes(Paths.get("src/test/resources/audit.json")));
        auditRequest = objectMapper.readValue(auditJson, Audit.class);
        String invoiceJson = new String(Files.readAllBytes(Paths.get("src/test/resources/invoice.json")));
        invoiceRequest = objectMapper.readValue(invoiceJson, Invoice.class);
        String invoiceItemsJson = new String(Files.readAllBytes(Paths.get("src/test/resources/invoice_items.json")));
        invoiceItemsRequest = objectMapper.readValue(invoiceItemsJson, List.class);
        String invoicePaymentDetailsJson = new String(Files.readAllBytes(Paths.get("src/test/resources/invoice_payment_details.json")));
        invoicePaymentDetailRequest = objectMapper.readValue(invoicePaymentDetailsJson, InvoicePaymentDetail.class);
    }

    public void main() throws IOException, TimeoutException {
        setUp();
        createInvoice();
        fetchInvoice();
        updateInvoice();
        fetchInvoiceById();
    }

    public boolean createInvoice(){
        var auditEntity = new AuditEntity(
                auditValidator,
                auditRequest
        );
        
        List<InvoiceItemEntity> invoiceItemsEntity = new ArrayList<>();
        for (InvoiceItem item : invoiceItemsRequest) {
            var invoiceItemEntity = new InvoiceItemEntity(
                    invoiceItemValidator,
                    item
            );
            invoiceItemsEntity.add(invoiceItemEntity);
        }

        var invoicePaymentDetailEntity = new InvoicePaymentDetailEntity(
                invoicePaymentDetailValidator,
                invoicePaymentDetailRequest
        );
        var invoiceEntity = new InvoiceEntity(
                invoiceValidator,
                invoiceRequest
            );

        return invoiceUseCase.create(auditEntity, invoiceEntity, invoiceItemsEntity, invoicePaymentDetailEntity);
    }

    public List<InvoiceEntity> fetchInvoice(){
        return invoiceUseCase.fetch();
    }

    public boolean updateInvoice(){
        var invoiceEntity = InvoiceEntity.update(
                invoiceValidator,
                invoiceRequest
            );
        return invoiceUseCase.update(invoiceEntity);
    }

    public InvoiceEntity fetchInvoiceById(){
        invoiceValidator.validateInvoiceId(invoiceRequest.invoice_id());
        return invoiceUseCase.fetchById(invoiceRequest.invoice_id());
    }
}
