package com.pat.inventory.domain.entities;

import com.pat.inventory.domain.shared.entities.AbstractEntity;
import com.pat.inventory.domain.shared.exceptions.DomainException;
import com.pat.inventory.domain.shared.exceptions.DomainExceptionCauses;
import com.pat.inventory.domain.shared.utils.DatetimeHandler;
import com.pat.inventory.domain.shared.utils.StringHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuditHandler extends AbstractEntity {
    private final String writeUId;
    private final String writeAt;
    private final String createUId;
    private final String createAt;

    public AuditHandler(boolean entityExists, String writeUId, String writeAt, String createUId, String createAt, boolean isFromRepo) throws DomainException {
        this.writeUId = writeUId;
        this.writeAt = writeAt;
        this.createUId = createUId;
        this.createAt = createAt;

        this.entityExists = entityExists;
        this.validate();
    }

    public static AuditHandler create(String writeUId) throws DomainException {
        if (StringHandler.isNullOrEmpty(writeUId)) {
            throw new DomainException(DomainExceptionCauses.requiredField("productId"));
        }

        String now = DatetimeHandler.now();
        return new AuditHandler(false, writeUId, now, writeUId, now, false);
    }

    public static AuditHandler existing(String writeUId) throws DomainException {
        if (StringHandler.isNullOrEmpty(writeUId)) {
            throw new DomainException(DomainExceptionCauses.requiredField("productId"));
        }

        String now = DatetimeHandler.now();
        return new AuditHandler(true, writeUId, now, null, null, true);
    }

    public Map<String, Object> asDict() {
        Map<String, Object> map = new HashMap<>();
        map.put("writeUId", writeUId);
        map.put("writeAt", writeAt);
        map.put("createUId", createUId);
        map.put("createAt", createAt);
        return map;
    }

    @Override
    public void validate() throws DomainException {
        if (!entityExists) {
            List<String> errors = Stream.of(
                            !writeAt.isEmpty() && DatetimeHandler.isValidDatetime(writeAt) ? DomainExceptionCauses.invalidDatetimeFormat("writeAt") : null,
                            !createAt.isEmpty() && DatetimeHandler.isValidDatetime(createAt) ? DomainExceptionCauses.invalidDatetimeFormat("createAt") : null
                    )
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (!errors.isEmpty()) {
                throw new DomainException(String.join(", ", errors));
            }
        }
    }
}
