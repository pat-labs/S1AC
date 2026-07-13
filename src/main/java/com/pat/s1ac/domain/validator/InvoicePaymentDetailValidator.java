package com.pat.s1ac.domain.validator;

import com.pat.s1ac.domain.error.DomainExceptionCauses;
import com.pat.s1ac.domain.model.InvoicePaymentDetail;
import com.pat.s1ac.domain.repository.IInvoiceRepositoryRead;
import com.pat.s1ac.domain.util.IntegerHandler;

import java.util.List;
import java.util.function.Function;

public class InvoicePaymentDetailValidator extends AbstractValidator<InvoicePaymentDetail> {
    private final IInvoiceRepositoryRead invoiceRepositoryRead;

    public InvoicePaymentDetailValidator(IInvoiceRepositoryRead invoiceRepositoryRead) {
        this.invoiceRepositoryRead = invoiceRepositoryRead;
    }

    public String validatePaymentMethodEnum(Integer paymentMethodEnum) {
        return invoiceRepositoryRead.existsPaymentMethodEnum(paymentMethodEnum) ? null : DomainExceptionCauses.resourceNotFound("payment_method_enum");
    }

    public String validateMoneyCurrencyEnum(Integer moneyCurrencyEnum) {
        return invoiceRepositoryRead.existsMoneyCurrencyEnum(moneyCurrencyEnum) ? null : DomainExceptionCauses.resourceNotFound("money_currency_enum");
    }

    public String validateMoney(String fieldName, Double amount) {
        return !IntegerHandler.isNotGreaterThanZero(amount) ? null : DomainExceptionCauses.resourceNotFound(fieldName);
    }

    @Override
    protected List<Function<InvoicePaymentDetail, String>> fullValidations() {
        return List.of(
                pd -> validatePaymentMethodEnum(pd.payment_method_enum()),
                pd -> validateMoneyCurrencyEnum(pd.money_currency_enum()),
                pd -> validateMoney("subtotal", pd.subtotal()),
                pd -> validateMoney("igv", pd.igv()),
                pd -> validateMoney("total_amount", pd.total_amount())
        );
    }

    @Override
    protected List<Function<InvoicePaymentDetail, String>> partialValidations() {
        return List.of(
                pd -> pd.payment_method_enum() != null ? validatePaymentMethodEnum(pd.payment_method_enum()) : null,
                pd -> pd.money_currency_enum() != null ? validateMoneyCurrencyEnum(pd.money_currency_enum()) : null,
                pd -> pd.subtotal() != null ? validateMoney("subtotal", pd.subtotal()) : null,
                pd -> pd.igv() != null ? validateMoney("igv", pd.igv()) : null,
                pd -> pd.total_amount() != null ? validateMoney("total_amount", pd.total_amount()) : null
        );
    }
}
