package com.pat.inventory.application.usecase.product;

import com.pat.inventory.application.shared.Broker;
import com.pat.inventory.application.shared.dao.ProductDao;
import com.pat.inventory.domain.entities.Product;
import com.pat.inventory.infrastructure.shared.exceptions.InfrastructureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ProductUseCase {
    private static final Logger log = LoggerFactory.getLogger(ProductUseCase.class);
    private final String writeUId;
    private final ProductDao productRepository;
    private final Broker productBroker;

    public ProductUseCase(String writeUId, ProductDao productRepository, Broker productBroker) {
        this.writeUId = writeUId;
        this.productRepository = productRepository;
        this.productBroker = productBroker;
    }

    public List<Product> findAll() throws InfrastructureException {
        return this.productRepository.findAll();
    }

    public Optional<Product> findById(String identifier) throws InfrastructureException {
        log.error("---USECASE");
        return this.productRepository.findById(identifier);
    }

    public void delete(String identifier) throws InfrastructureException {
        this.productRepository.deleteById(identifier);
    }

    public void create(Product product) throws InfrastructureException {
        this.productRepository.create(product);
    }

    public void update(Product product) throws InfrastructureException {
        this.productRepository.update(product);
    }
}
