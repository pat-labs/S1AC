package com.pat.s1ac.domain.model.util;

import com.pat.s1ac.domain.prototype.IAudit;
import com.pat.s1ac.domain.error.DomainExceptionCauses;
import com.pat.s1ac.domain.validator.util.DatetimeHandler;
import com.pat.s1ac.domain.validator.util.StringHandler;

public record Audit(
        String write_uid,
        String write_at,
        String create_uid,
        String create_at
) implements IAudit {

    public static Audit create(String userId) {
        if (StringHandler.isNotNullOrEmpty(userId)) {
            throw new IllegalArgumentException(DomainExceptionCauses.requiredField("write_uid"));
        }

        String now = DatetimeHandler.now();
        return new Audit(userId, now, userId, now);
    }

    public static Audit update(String userId, Audit oldAudit) {
        if (StringHandler.isNotNullOrEmpty(userId)) {
            throw new IllegalArgumentException(DomainExceptionCauses.requiredField("write_uid"));
        }
        String now = DatetimeHandler.now();
        return new Audit(userId, now, oldAudit.create_uid(), oldAudit.create_at());
    }
}
