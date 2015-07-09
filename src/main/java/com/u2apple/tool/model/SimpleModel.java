/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.model;

/**
 *
 * @author Adam
 */
public class SimpleModel {
    private String value;
    private String productId;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public SimpleModel(String value, String productId) {
        this.value = value;
        this.productId = productId;
    }
    
}
