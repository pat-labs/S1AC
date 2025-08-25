package com.pat.inventory.application.shared.dao;

import com.pat.inventory.domain.factory.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    List<Product> findAll();

    Optional<Product> findById(String id);

    void deleteById(String id);

    void create(Product product);

    void update(Product product);
}
