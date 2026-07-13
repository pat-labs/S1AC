package com.pat.s1ac.domain.validator;

import com.pat.s1ac.domain.error.DomainExceptionCauses;
import com.pat.s1ac.domain.model.util.Audit;
import com.pat.s1ac.domain.model.util.Response;
import com.pat.s1ac.domain.third_party.IPersonService;
import com.pat.s1ac.domain.util.DatetimeHandler;

import java.util.List;
import java.util.function.Function;

public class AuditValidator extends AbstractValidator<Audit> {
    private final IPersonService personService;

    public AuditValidator(IPersonService personService) {
        this.personService = personService;
    }

    public Response<Boolean> isValidId(String writeUId){
        return validatePersonId(writeUId);
    }

    public Response<Boolean> validatePersonId(String personId) {
        var response = personService.exists(personId);
        if (response.error() != null){
            return response.error();
        }
        return null;
    }

    @Override
    protected List<Function<Audit, String>> fullValidations() {
        return List.of(
                audit -> validatePersonId(audit.write_uid()),
                audit -> DatetimeHandler.isNotValidDatetime(audit.write_at())
                        ? DomainExceptionCauses.invalidDatetimeFormat("write_at") : null,
                audit -> validatePersonId(audit.create_uid()),
                audit -> DatetimeHandler.isNotValidDatetime(audit.create_at())
                        ? DomainExceptionCauses.invalidDatetimeFormat("create_at") : null
        );
    }

    @Override
    protected List<Function<Audit, String>> partialValidations() {
        return List.of(
                audit -> validatePersonId(audit.write_uid()),
                audit -> audit.write_at() != null && !DatetimeHandler.isNotValidDatetime(audit.write_at())
                        ? DomainExceptionCauses.invalidDatetimeFormat("write_at") : null,
                audit -> audit.create_uid() != null ? validatePersonId(audit.create_uid()) : null,
                audit -> audit.create_at() != null && !DatetimeHandler.isNotValidDatetime(audit.create_at())
                        ? DomainExceptionCauses.invalidDatetimeFormat("create_at") : null
        );
    }
}
