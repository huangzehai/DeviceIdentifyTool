package com.u2apple.tool.model;

import com.u2apple.tool.constant.Condition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model {

    private List<String> values;
    private List<ProductId> productIds;

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<ProductId> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<ProductId> productIds) {
        this.productIds = productIds;
    }

    public Model(List<String> values, List<ProductId> productIds) {
        this.values = values;
        this.productIds = productIds;
    }

    public Model(String value, ProductId productId) {
        values = new ArrayList<>();
        values.add(value);
        productIds = new ArrayList<>();
        productIds.add(productId);
    }

    @Deprecated
    public Model(String value, String productId) {
        values = new ArrayList<>();
        values.add(value);
        productIds = new ArrayList<>();
        productIds.add(new ProductId(productId));
    }

    @Deprecated
    public Model(String[] values, String productId) {
        this.values = new ArrayList<>();
        this.values.addAll(Arrays.asList(values));
        productIds = new ArrayList<>();
        productIds.add(new ProductId(productId));
    }

    @Deprecated
    public void setBrand(String brand) {
        if (this.getProductIds() != null) {
            for (ProductId productId : this.getProductIds()) {
                productId.addCondition(Condition.BRAND, brand);
            }
        }
    }
}
