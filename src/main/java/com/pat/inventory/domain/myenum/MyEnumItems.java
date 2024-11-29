package com.pat.inventory.domain.myenum;

import lombok.Data;

@Data
public class MyEnumItems {
    private final int myEnumItemsId;
    private final String key;
    private final int value;
    private final int order;
    private final boolean isInactivate;
    private final int myEnumId;
}
