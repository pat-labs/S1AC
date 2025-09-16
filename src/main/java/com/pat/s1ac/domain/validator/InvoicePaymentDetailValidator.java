package com.pat.s1ac.domain.validator;

import com.pat.s1ac.domain.model.InvoicePaymentDetail;
import com.pat.s1ac.domain.error.DomainExceptionCauses;
import com.pat.s1ac.domain.validator.util.EnumHandler;
import com.pat.s1ac.domain.validator.util.IntegerHandler;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class InvoicePaymentDetailValidator extends AbstractValidator<InvoicePaymentDetail> {
    private final Predicate<Integer> paymentMethodEnumExists;
    private final Predicate<Integer> moneyCurrencyEnumExists;

    public InvoicePaymentDetailValidator(Predicate<Integer> paymentMethodEnumExists, Predicate<Integer> moneyCurrencyEnumExists) {
        this.paymentMethodEnumExists = paymentMethodEnumExists;
        this.moneyCurrencyEnumExists = moneyCurrencyEnumExists;
    }

    public String validatePaymentMethodEnum(Integer paymentMethodEnum) {
        return EnumHandler.validateEnum(paymentMethodEnumExists, "Payment Method Enum", paymentMethodEnum);
    }

    public String validateMoneyCurrencyEnum(Integer moneyCurrencyEnum) {
        return EnumHandler.validateEnum(moneyCurrencyEnumExists, "Money Currency Enum", moneyCurrencyEnum);
    }

    public String validateMoney(String fieldName, Double amount) {
        if (!IntegerHandler.isNotGreaterThanZero(amount)) {
            return DomainExceptionCauses.illegalArgument(fieldName);
        }
        return null;
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
