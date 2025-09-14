package com.pat.s1ac.domain.prototype;

public interface IInvoiceItem {
    String product_id();
    Integer product_unit_enum();
    String description();
    Double quantity();
    Double unit_price();
    Double total_price();
}
