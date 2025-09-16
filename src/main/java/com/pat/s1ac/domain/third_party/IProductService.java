package com.pat.s1ac.domain.third_party;

public interface IProductService {
    boolean exists(String productId);

    boolean existsProductUnitEnum(Integer productUnitEnumValue);
}
