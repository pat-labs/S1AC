package com.pat.inventory.application.shared.dao;

import com.pat.inventory.domain.entities.Product;
import com.pat.inventory.infrastructure.shared.exceptions.InfrastructureException;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    List<Product> findAll() throws InfrastructureException;

    Optional<Product> findById(String id) throws InfrastructureException;

    void deleteById(String id) throws InfrastructureException;

    void create(Product product) throws InfrastructureException;

    void update(Product product) throws InfrastructureException;
}
