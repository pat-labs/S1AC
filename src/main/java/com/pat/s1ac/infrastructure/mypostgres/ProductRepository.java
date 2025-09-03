package com.pat.s1ac.infrastructure.mypostgres;

import com.pat.s1ac.application.shared.dao.ProductDao;
import com.pat.s1ac.infrastructure.mypostgres.mapper.ProductRowMapper;
import com.pat.inventory.infrastructure.shared.error.InfrastructureException;
import com.pat.inventory.infrastructure.shared.error.InfrastructureExceptionCauses;
import com.pat.s1ac.domain.factory.Product;
import com.pat.s1ac.domain.shared.util.StringHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository implements ProductDao {
    private static final Logger log = LoggerFactory.getLogger(ProductRepository.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final MapSqlParameterSource paramSource;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.paramSource = new MapSqlParameterSource();
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        String sqlQuery = "SELECT product_id, description, status_value FROM product";
        try {
            return this.jdbcTemplate.query(sqlQuery, new ProductRowMapper());
        } catch (Exception e) {
            throw new InfrastructureException(InfrastructureExceptionCauses.queryExecFail(sqlQuery, e.getMessage()));
        }
    }

    @Override
    public Optional<Product> findById(String identifier) {
        if (StringHandler.isNullOrEmpty(identifier)) {
            throw new InfrastructureException(InfrastructureExceptionCauses.ilegalArgument("productId", identifier));
        }
        String sqlQuery = "SELECT product_id, description, status_value FROM product WHERE product_id = :product_id";
        this.paramSource.addValue("product_id", identifier);

        try {
            Product product = this.namedParameterJdbcTemplate.queryForObject(
                    sqlQuery,
                    this.paramSource,
                    new ProductRowMapper()
            );
            return Optional.ofNullable(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new InfrastructureException(InfrastructureExceptionCauses.queryExecFail(sqlQuery, e.getMessage()));
        }
    }

    @Override
    public void deleteById(String identifier) {
        if (StringHandler.isNullOrEmpty(identifier)) {
            throw new InfrastructureException(InfrastructureExceptionCauses.ilegalArgument("productId", identifier));
        }
        String sqlQuery = "DELETE FROM product WHERE product_id = :product_id";
        this.paramSource.addValue("product_id", identifier);

        try {
            this.namedParameterJdbcTemplate.update(sqlQuery, this.paramSource);
        } catch (Exception e) {
            throw new InfrastructureException(InfrastructureExceptionCauses.queryExecFail(sqlQuery, e.getMessage()));
        }
    }

    @Override
    public void create(Product product) {
        String sqlQuery = "INSERT INTO product (product_id, description, status_value) VALUES (:product_id, :description, :status_value)";
        this.paramSource.addValue("product_id", product.getProductId());
        this.paramSource.addValue("description", product.getDescription());
        this.paramSource.addValue("status_value", product.getStatusValue());

        try {
            this.namedParameterJdbcTemplate.update(sqlQuery, this.paramSource);
        } catch (Exception e) {
            throw new InfrastructureException(InfrastructureExceptionCauses.queryExecFail(sqlQuery, e.getMessage()));
        }

    }

    public void update(Product product) {
        StringBuilder setClause = new StringBuilder();
        if (product.getDescription() != null) {
            setClause.append("description = :description, ");
            this.paramSource.addValue("description", product.getDescription());
        }
        if (product.getStatusValue() != null) {
            setClause.append("status_value = :status_value, ");
            this.paramSource.addValue("status_value", product.getStatusValue());
        }

        if (setClause.length() > 0) {
            setClause.setLength(setClause.length() - 2); // Remove last ", "
        } else {
            throw new InfrastructureException("No fields to update for product ID: " + product.getProductId());
        }

        this.paramSource.addValue("product_id", product.getProductId());
        String sqlQuery = "UPDATE product SET " + setClause + " WHERE product_id = :product_id";
        try {
            this.namedParameterJdbcTemplate.update(sqlQuery, this.paramSource);
        } catch (Exception e) {
            throw new InfrastructureException(
                    InfrastructureExceptionCauses.queryExecFail(sqlQuery, e.getMessage())
            );
        }
    }
}