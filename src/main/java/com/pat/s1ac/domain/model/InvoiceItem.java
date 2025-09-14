package com.pat.s1ac.domain.model;

import com.pat.s1ac.domain.model.util.Money;
import com.pat.s1ac.domain.prototype.IInvoiceItem;

public record InvoiceItem(
        String product_id,
        Integer product_unit_enum,
        String description,
        Double quantity,
        Double unit_price,
        Double total_price
) implements IInvoiceItem {
}
