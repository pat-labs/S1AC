package com.pat.inventory.domain.shared.util;

import java.util.stream.Stream;

public enum EnumValues {
    PRODUCT_STATUS(0);

    private final int value;

    EnumValues(int value) {
        this.value = value;
    }

    public static EnumValues of(int value) {
        return Stream.of(EnumValues.values())
                .filter(p -> p.getValue() == value)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public int getValue() {
        return value;
    }
}
