package com.pat.s1ac.domain.validator;

import com.pat.s1ac.domain.model.util.Audit;
import com.pat.s1ac.domain.shared.error.DomainExceptionCauses;
import com.pat.s1ac.domain.shared.util.DatetimeHandler;
import com.pat.s1ac.domain.shared.util.IdHandler;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class AuditValidator extends AbstractValidator<Audit> {
    private final Predicate<String> personExists;

    public AuditValidator(Predicate<String> personExists) {
        this.personExists = personExists;
    }

    public String validatePersonId(String personId) {
        return IdHandler.validateId(personExists, "Person Id", personId);
    }

    @Override
    protected List<Function<Audit, String>> fullValidations() {
        return List.of(
                audit -> validatePersonId(audit.write_uid()),
                audit -> !DatetimeHandler.isValidDatetime(audit.write_at())
                        ? DomainExceptionCauses.invalidDatetimeFormat("write_at") : null,
                audit -> validatePersonId(audit.create_uid()),
                audit -> !DatetimeHandler.isValidDatetime(audit.create_at())
                        ? DomainExceptionCauses.invalidDatetimeFormat("create_at") : null
        );
    }

    @Override
    protected List<Function<Audit, String>> partialValidations() {
        return List.of(
                audit -> audit.write_uid() != null ? validatePersonId(audit.write_uid()) : null,
                audit -> audit.write_at() != null && !DatetimeHandler.isValidDatetime(audit.write_at())
                        ? DomainExceptionCauses.invalidDatetimeFormat("write_at") : null,
                audit -> audit.create_uid() != null ? validatePersonId(audit.create_uid()) : null,
                audit -> audit.create_at() != null && !DatetimeHandler.isValidDatetime(audit.create_at())
                        ? DomainExceptionCauses.invalidDatetimeFormat("create_at") : null
        );
    }
}
