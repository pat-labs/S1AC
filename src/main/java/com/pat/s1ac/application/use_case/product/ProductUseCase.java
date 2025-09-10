package com.pat.s1ac.application.use_case.product;

import com.pat.s1ac.application.shared.BrokerProducer;
import com.pat.s1ac.application.shared.dao.ProductDao;
import com.pat.s1ac.domain.factory.Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ProductUseCase {
    private static final Logger log = LoggerFactory.getLogger(ProductUseCase.class);
    private final String writeUId;
    private final ProductDao productDao;
    private final BrokerProducer brokerProducer;

    public ProductUseCase(String writeUId, ProductDao productDao, BrokerProducer brokerProducer) {
        this.writeUId = writeUId;
        this.productDao = productDao;
        this.brokerProducer = brokerProducer;
    }

    public List<Product> findAll() {
        return this.productDao.findAll();
    }

    public Optional<Product> findById(String identifier) {
        log.error("---USECASE");
        return this.productDao.findById(identifier);
    }

    public void delete(String identifier) {
        this.productDao.deleteById(identifier);
    }

    public void create(Product product) {
        this.productDao.create(product);
    }

    public void update(Product product) {
        this.productDao.update(product);
    }
}
