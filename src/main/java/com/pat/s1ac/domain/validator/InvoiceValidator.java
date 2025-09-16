package com.pat.s1ac.domain.validator;

import com.pat.s1ac.domain.model.Invoice;
import com.pat.s1ac.domain.error.DomainExceptionCauses;
import com.pat.s1ac.domain.repository.IInvoiceRepositoryRead;
import com.pat.s1ac.domain.third_party.ICompanyService;
import com.pat.s1ac.domain.third_party.IPersonService;
import com.pat.s1ac.domain.validator.util.DatetimeHandler;
import com.pat.s1ac.domain.validator.util.EnumHandler;
import com.pat.s1ac.domain.validator.util.IdHandler;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class InvoiceValidator extends AbstractValidator<Invoice> {
    private final IInvoiceRepositoryRead invoiceRepositoryRead;
    private final ICompanyService companyService;
    private final IPersonService personService;

    public InvoiceValidator(
            Predicate<Integer> invoiceRepositoryRead,
            Predicate<String> companyService,
            Predicate<String> personService) {

        this.invoiceRepositoryRead = invoiceRepositoryRead;
        this.companyService = companyService;
        this.personService = personService;
    }

    public boolean validateInvoiceId(String invoiceId) {
        return true;
    }

    public String validateDocumentTypeEnum(Integer documentTypeEnumValue) {
        return EnumHandler.validateEnum(invoiceRepositoryRead, "Document Type Enum", documentTypeEnumValue);
    }

    public String validateCompanyId(String companyId) {
        return IdHandler.validateId(companyService, "Company Id", companyId);
    }

    public String validatePersonId(String personId) {
        return IdHandler.validateId(personService, "Person Id", personId);
    }

    @Override
    protected List<Function<Invoice, String>> fullValidations() {
        return List.of(
                inv -> validateDocumentTypeEnum(inv.invoice_document_type_enum()),
                inv -> DatetimeHandler.isNotValidDatetime(inv.issue_at())
                        ? DomainExceptionCauses.invalidDatetimeFormat("issue_at") : null,
                inv -> validateCompanyId(inv.company_branch_id()),
                inv -> validatePersonId(inv.person_id())
        );
    }

    @Override
    protected List<Function<Invoice, String>> partialValidations() {
        return List.of(
                inv -> inv.invoice_id() == null
                        ? DomainExceptionCauses.requiredField("invoice_id") : null,
                inv -> inv.invoice_document_type_enum() != null
                        ? validateDocumentTypeEnum(inv.invoice_document_type_enum()) : null,
                inv -> inv.issue_at() != null && DatetimeHandler.isNotValidDatetime(inv.issue_at())
                        ? DomainExceptionCauses.invalidDatetimeFormat("issue_at") : null,
                inv -> inv.company_branch_id() != null ? validateCompanyId(inv.company_branch_id()) : null,
                inv -> inv.person_id() != null ? validatePersonId(inv.person_id()) : null
        );
    }
}
