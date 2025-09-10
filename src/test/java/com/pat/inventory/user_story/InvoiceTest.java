package com.pat.inventory.user_story;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

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
    private Map<String, Object> invoiceRequest;

    public void setUp() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get("src/test/resources/invoice.json")));

        ObjectMapper objectMapper = new ObjectMapper();
        invoiceRequest = objectMapper.readValue(jsonContent, Map.class);
    }

    public void main(){
        createInvoice();
//        fetchInvoice();
//        updateInvoice();
//        fetchById();
    }

    public void createInvoice(){

    }
}
