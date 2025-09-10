package com.pat.s1ac.domain.model.util;

public record Audit(
        String create_uid,
        String create_at,
        String write_uid,
        String write_at,
        boolean is_activate
) {
}
