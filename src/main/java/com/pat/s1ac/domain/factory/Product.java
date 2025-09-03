package com.pat.s1ac.domain.factory;

import com.pat.s1ac.domain.shared.entities.AbstractEntity;
import com.pat.s1ac.domain.shared.error.DomainExceptionCauses;
import com.pat.inventory.domain.shared.exceptions.DomainException;
import com.pat.s1ac.domain.shared.util.EnumValues;
import com.pat.s1ac.domain.shared.util.StringHandler;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class Product extends AbstractEntity {
    private final String productId;
    private final String description;
    private final Integer statusValue;

    public Product(
            boolean entityExists,
            String productId,
            String description,
            Integer statusValue) throws DomainException {
        this.entityExists = entityExists;
        this.productId = productId;
        this.description = description;
        this.statusValue = statusValue;

        validate();
    }

    public static int getIdStrategy() {
        return 0;
    }

    public static int getMyEnumStatus() {
        return EnumValues.PRODUCT_STATUS.getValue();
    }

    public static String getDefaultValues() {
        return "{statusValue: 1}";
    }

    public static Product create(
            String productId,
            String description,
            int statusValue) throws DomainException {
        List<String> errors = new ArrayList<>();

        if (StringHandler.isNullOrEmpty(productId)) {
            errors.add(DomainExceptionCauses.requiredField("productId"));
        }
        if (StringHandler.isNullOrEmpty(description)) {
            errors.add(DomainExceptionCauses.requiredField("description"));
        }

        if (!errors.isEmpty()) {
            throw new DomainException(String.join(", ", errors));
        }

        return new Product(
                false,
                productId,
                description,
                Integer.valueOf(statusValue));
    }

    public static Product existing(
            String productId,
            String description,
            Integer statusValue) throws DomainException {
        if (StringHandler.isNullOrEmpty(productId)) {
            throw new DomainException(DomainExceptionCauses.requiredField("productId"));
        }

        return new Product(
                true,
                productId,
                description,
                statusValue);
    }

    public static String validateDescription(String description) {
        List<String> errors = Stream.of(
                        !StringHandler.isValidUTF8String(description) ? DomainExceptionCauses.invalidUTF8String(description) : null,
                        !StringHandler.isValidStringLength(description, 200) ? DomainExceptionCauses.invalidStringLength(description, 200) : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!errors.isEmpty()) {
            return (String.join(", ", errors));
        }
        return null;
    }

    @Override
    public void validate() throws DomainException {
        List<String> errors = new ArrayList<>();

        validateField(description, Product::validateDescription, errors);

        if (!errors.isEmpty()) {
            throw new DomainException(String.join(", ", errors));
        }
    }
}
