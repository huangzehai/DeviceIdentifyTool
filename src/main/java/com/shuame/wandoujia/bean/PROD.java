package com.shuame.wandoujia.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PROD")
@XmlType(name = "PROD", namespace = "http://www.wandoujia.com/schema/1.0.0")
public class PROD {

	/**  */
	@XmlAttribute(name = "Value")
	private String _value;

	/**  */
	@XmlElement(name = "ProductId")
	private String _productId;

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		_value = value;
	}

	public String getProductId() {
		return _productId;
	}

	public void setProductId(String productId) {
		_productId = productId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
