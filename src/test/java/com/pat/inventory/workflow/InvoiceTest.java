package com.pat.inventory.workflow;

public class InvoiceTest {
    private Map<String, Object> invoiceResponse;

    @BeforeEach
    public void setUp() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get("src/test/resources/invoice.json")));

        ObjectMapper objectMapper = new ObjectMapper();
        invoiceResponse = objectMapper.readValue(jsonContent, Map.class);
    }

    public void 
}
