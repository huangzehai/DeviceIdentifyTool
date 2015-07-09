package com.u2apple.tool.model;

import java.util.HashMap;
import java.util.Map;

public class ProductId {

    private String value;
    private Map<String, String> conditions;

    public ProductId(String value) {
        super();
        this.value = value;
    }

    public ProductId(String value, Map<String, String> conditions) {
        this.value = value;
        this.conditions = conditions;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Map<String, String> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, String> conditions) {
        this.conditions = conditions;
    }

    public void addCondition(String name, String value) {
        if (conditions == null) {
            conditions = new HashMap<>();
        }
        conditions.put(name, value);
    }

}
