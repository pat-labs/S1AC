package com.pat.s1ac.domain.validator;

import com.pat.s1ac.domain.model.Invoice;
import com.pat.s1ac.domain.shared.error.DomainExceptionCauses;
import com.pat.s1ac.domain.shared.util.DatetimeHandler;
import com.pat.s1ac.domain.shared.util.EnumHandler;
import com.pat.s1ac.domain.shared.util.IdHandler;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class InvoiceValidator extends AbstractValidator<Invoice> {
    private final Predicate<Integer> existsInvoiceDocumentTypeEnum;
    private final Predicate<String> companyExists;
    private final Predicate<String> personExists;

    public InvoiceValidator(
            Predicate<Integer> existsInvoiceDocumentTypeEnum,
            Predicate<String> companyExists,
            Predicate<String> personExists) {

        this.existsInvoiceDocumentTypeEnum = existsInvoiceDocumentTypeEnum;
        this.companyExists = companyExists;
        this.personExists = personExists;
    }

    public boolean validateInvoiceId(String invoiceId){
        return true;
    }

    public String validateDocumentTypeEnum(Integer documentTypeEnumValue) {
        return EnumHandler.validateEnum(existsInvoiceDocumentTypeEnum, "Document Type Enum", documentTypeEnumValue);
    }

    public String validateCompanyId(String companyId) {
        return IdHandler.validateId(companyExists, "Company Id", companyId);
    }

    public String validatePersonId(String personId) {
        return IdHandler.validateId(personExists, "Person Id", personId);
    }

    @Override
    protected List<Function<Invoice, String>> fullValidations() {
        return List.of(
                inv -> validateDocumentTypeEnum(inv.document_type_enum()),
                inv -> DatetimeHandler.isValidDatetime(inv.issue_at())
                        ? DomainExceptionCauses.invalidDatetimeFormat("issue_at") : null,
                inv -> validateCompanyId(inv.company_id()),
                inv -> validatePersonId(inv.person_id())
        );
    }

    @Override
    protected List<Function<Invoice, String>> partialValidations() {
        return List.of(
                inv -> inv.invoice_id() == null
                        ? DomainExceptionCauses.requiredField("invoice_id") : null,
                inv -> inv.document_type_enum() != null
                        ? validateDocumentTypeEnum(inv.document_type_enum()) : null,
                inv -> inv.issue_at() != null && DatetimeHandler.isValidDatetime(inv.issue_at())
                        ? DomainExceptionCauses.invalidDatetimeFormat("issue_at") : null,
                inv -> inv.company_id() != null ? validateCompanyId(inv.company_id()) : null,
                inv -> inv.person_id() != null ? validatePersonId(inv.person_id()) : null
        );
    }
}
