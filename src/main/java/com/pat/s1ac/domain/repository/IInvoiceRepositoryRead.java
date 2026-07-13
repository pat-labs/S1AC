package com.pat.s1ac.domain.repository;

import com.pat.s1ac.domain.model.Invoice;
import com.pat.s1ac.domain.model.util.Response;

import java.util.List;

public interface IInvoiceRepositoryRead {
    Response<List<Invoice>> fetch(int offset, int limit);

    Response<Invoice> fetchById(String invoiceId);

    Response<Boolean> exists(String identifier);

    Response<Boolean> existsPaymentMethodEnum(Integer paymentMethodEnum);

    Response<Boolean> existsMoneyCurrencyEnum(Integer moneyCurrencyEnum);

    Response<Boolean> existsInvoiceDocumentTypeEnum(Integer invoiceDocumentTypeEnum);
}
