package com.pat.s1ac.infrastructure.mygrpc.service;

import com.pat.s1ac.domain.third_party.IProductService;

public class ProductService implements IProductService {
    public boolean exists(String productId) {
        return true;
    }

    public boolean existsUnitEnum(Integer productUnitEnumValue) {
        return true;
    }

}
