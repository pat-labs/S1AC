package com.pat.s1ac.domain.validator;

import com.pat.s1ac.domain.model.InvoiceItem;
import com.pat.s1ac.domain.error.DomainExceptionCauses;
import com.pat.s1ac.domain.third_party.IProductService;
import com.pat.s1ac.domain.validator.util.EnumHandler;
import com.pat.s1ac.domain.validator.util.IdHandler;
import com.pat.s1ac.domain.validator.util.IntegerHandler;
import com.pat.s1ac.domain.validator.util.StringHandler;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class InvoiceItemValidator extends AbstractValidator<InvoiceItem> {

    private final IProductService productService;

    public InvoiceItemValidator(IProductService productService) {
        this.productService = productService;
    }

    public String validateProductId(String productId) {
        if(!productService.exists(productId)){
            DomainExceptionCauses.resourceNotFound("product_id");
        }
        return null;
    }

    public String validateProductUnitEnum(Integer productUnitEnumValue) {
        if(!productService.existsProductUnitEnum(productUnitEnumValue)){
            DomainExceptionCauses.resourceNotFound("product_unit_enum_value");
        }
        return null;
    }

    public String validateDescription(String description) {
        return StringHandler.validateString(200, "Description", description);
    }

    public String validateQuantity(Double quantity) {
        if (quantity == null || !IntegerHandler.isNotGreaterThanZero(quantity)) {
            return DomainExceptionCauses.illegalArgument("Quantity");
        }
        return null;
    }

    public String validatePrice(Double price) {
        if (price == null || !IntegerHandler.isNotGreaterThanZero(price)) {
            return DomainExceptionCauses.illegalArgument("Price");
        }
        return null;
    }

    @Override
    protected List<Function<InvoiceItem, String>> fullValidations() {
        return List.of(
                item -> validateProductId(item.product_id()),
                item -> validateProductUnitEnum(item.product_unit_enum()),
                item -> validateDescription(item.description()),
                item -> validateQuantity(item.quantity()),
                item -> validatePrice(item.unit_price())
        );
    }

    @Override
    protected List<Function<InvoiceItem, String>> partialValidations() {
        return List.of(
                item -> item.product_id() != null ? validateProductId(item.product_id()) : null,
                item -> item.product_unit_enum() != null ? validateProductUnitEnum(item.product_unit_enum()) : null,
                item -> item.description() != null ? validateDescription(item.description()) : null,
                item -> item.quantity() != null ? validateQuantity(item.quantity()) : null,
                item -> item.unit_price() != null ? validatePrice(item.unit_price()) : null
        );
    }
}
