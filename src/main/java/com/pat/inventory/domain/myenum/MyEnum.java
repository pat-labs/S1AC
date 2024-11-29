package com.pat.inventory.domain.myenum;

import lombok.Data;

@Data
public class MyEnum {
    private final Integer myEnumId;
    private final String description;
    private final String tableName;
}
