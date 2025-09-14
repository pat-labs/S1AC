package com.pat.s1ac.application.entity;

import com.pat.s1ac.domain.model.util.Audit;
import com.pat.s1ac.domain.shared.error.DomainExceptionCauses;
import com.pat.s1ac.domain.shared.util.DatetimeHandler;
import com.pat.s1ac.domain.shared.util.StringHandler;
import com.pat.s1ac.domain.validator.IValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuditEntity {
    private Audit audit;

    public AuditEntity(IValidator validator, Audit data) {
        validator.isValid(data);
        this.audit = data;
    }
}
