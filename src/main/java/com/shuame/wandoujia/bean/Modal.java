package com.shuame.wandoujia.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Modal")
@XmlType(name = "Modal", namespace = "http://www.wandoujia.com/schema/1.0.0")
public class Modal {

    /**  */
    @XmlElement(name = "Value")
    private List<Value> _values;

    /**  */
    @XmlElement(name = "ProductId")
    // private String _productId;
    private List<ProductId> _productIds;

    public List<Value> getValues() {
        if (_values == null) {
            _values = new ArrayList<Value>();
        }
        return _values;
    }

    public void setValues(List<Value> values) {
        _values = values;
    }

    public List<ProductId> getProductId() {
        if (_productIds == null) {
            _productIds = new ArrayList<ProductId>();
        }
        return _productIds;
    }

    public void setProductId(List<ProductId> productIds) {
        _productIds = productIds;
    }

    // public String getProductId() {
    // return _productId;
    // }
    //
    // public void setProductId(String productId) {
    // _productId = productId;
    // }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
