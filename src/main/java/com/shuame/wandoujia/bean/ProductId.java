package com.shuame.wandoujia.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 机型匹配中的ProductId节点，可以带有条件式。
 *
 * @author mwsxh
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ProductId")
@XmlType(name = "ProductId", namespace = "http://www.wandoujia.com/schema/1.0.0")
public class ProductId {

    private static final Logger _logger = LoggerFactory.getLogger(ProductId.class);

    /**
     * 条件，字符串形式
     */
    @XmlAttribute(name = "c")
    private String condition;

    /**
     * product id
     */
    @XmlValue
    private String value;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ProductId{" + "condition=" + condition + ", value=" + value + '}';
    }

}
