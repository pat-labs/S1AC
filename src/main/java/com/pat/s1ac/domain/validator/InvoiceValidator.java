package com.pat.s1ac.domain.validator;

import com.pat.s1ac.domain.error.DomainExceptionCauses;
import com.pat.s1ac.domain.model.Invoice;
import com.pat.s1ac.domain.model.util.Response;
import com.pat.s1ac.domain.repository.IInvoiceRepositoryRead;
import com.pat.s1ac.domain.third_party.ICompanyService;
import com.pat.s1ac.domain.third_party.IPersonService;
import com.pat.s1ac.domain.util.DatetimeHandler;

import java.util.List;
import java.util.function.Function;

public class InvoiceValidator extends AbstractValidator<Invoice> {
    private final IInvoiceRepositoryRead invoiceRepositoryRead;
    private final ICompanyService companyService;
    private final IPersonService personService;

    public InvoiceValidator(IInvoiceRepositoryRead invoiceRepositoryRead, ICompanyService companyService, IPersonService personService) {
        this.invoiceRepositoryRead = invoiceRepositoryRead;
        this.companyService = companyService;
        this.personService = personService;
    }

    public Response<Boolean> isValidId(String invoiceId) {
        return invoiceRepositoryRead.exists(invoiceId);
    }

    public Response<Boolean> validateDocumentTypeEnum(Integer documentTypeEnumValue) {
        return invoiceRepositoryRead.existsInvoiceDocumentTypeEnum(documentTypeEnumValue);
    }

    public Response<Boolean> validateCompanyId(String companyId) {
        return companyService.exists(companyId);
    }

    public Response<Boolean> validatePersonId(String personId) {
        return personService.exists(personId);
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
