package com.pat.s1ac.domain.factory;

import com.pat.s1ac.domain.shared.entities.AbstractEntity;
import com.pat.s1ac.domain.shared.error.DomainExceptionCauses;
import com.pat.s1ac.domain.shared.util.DatetimeHandler;
import com.pat.s1ac.domain.shared.util.StringHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Audit extends AbstractEntity {
    private final String writeUId;
    private final String writeAt;
    private final String createUId;
    private final String createAt;

    public Audit(boolean entityExists, String writeUId, String writeAt, String createUId, String createAt, boolean isFromRepo) {
        this.writeUId = writeUId;
        this.writeAt = writeAt;
        this.createUId = createUId;
        this.createAt = createAt;

        this.entityExists = entityExists;
        this.validate();
    }

    public static Audit create(String writeUId) {
        if (StringHandler.isNullOrEmpty(writeUId)) {
            throw new Error(0, DomainExceptionCauses.requiredField("writeUId"));
        }

        String now = DatetimeHandler.now();
        return new Audit(false, writeUId, now, writeUId, now, false);
    }

    public static Audit existing(String writeUId) {
        if (StringHandler.isNullOrEmpty(writeUId)) {
            throw new Error(0, DomainExceptionCauses.requiredField("writeUId"));
        }

        String now = DatetimeHandler.now();
        return new Audit(true, writeUId, now, null, null, true);
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
    public void validate() {
        if (!entityExists) {
            List<String> errors = Stream.of(
                            !writeAt.isEmpty() && DatetimeHandler.isValidDatetime(writeAt) ? DomainExceptionCauses.invalidDatetimeFormat("writeAt") : null,
                            !createAt.isEmpty() && DatetimeHandler.isValidDatetime(createAt) ? DomainExceptionCauses.invalidDatetimeFormat("createAt") : null
                    )
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (!errors.isEmpty()) {
                throw new Error(0, String.join(", ", errors));
            }
        }
    }
}
