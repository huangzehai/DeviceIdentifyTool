package com.shuame.wandoujia.bean;

import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import org.apache.commons.lang3.StringUtils;
;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Modal下的Value节点.
 *
 * @author Adam
 * @date Jun 29, 2015 11:35:44 AM
 * @version 1.0
 *
 */


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Value")
@XmlType(name = "Value", namespace = "http://www.wandoujia.com/schema/1.0.0")
public class Value {

    @XmlAttribute(name = "m")
    private String _method;
    @XmlValue
    private String _value;

    private class Method {

        public static final String EQUALS = "equals";
        public static final String REGEXP = "regexp";
    }

    public String getMethod() {
        return _method;
    }

    public void setMethod(String method) {
        _method = method;
    }

    public String getValue() {
        return _value;
    }

    public void setValue(String value) {
        _value = value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        if (this._value != null) {
            hash += this._value.toLowerCase().hashCode();
        }
        if (this._method != null) {
            hash += this._method.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Value other = (Value) obj;
        if (!Objects.equals(this._method, other._method)) {
            return false;
        }
        if (!StringUtils.equalsIgnoreCase(this._value, other._value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
