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
@XmlRootElement(name = "Name")
@XmlType(name = "Name", namespace = "http://www.wandoujia.com/schema/1.0.0")
public class Name {

	/**  */
	@XmlElement(name = "Value")
	private List<String> _values;

	/**  */
	@XmlElement(name = "ProductId")
	private List<String> _productIds;

	public List<String> getValues() {
		if (_values == null) {
			_values = new ArrayList<String>();
		}
		return _values;
	}

	public void setValues(List<String> values) {
		_values = values;
	}

	public List<String> getProductIds() {
		if (_productIds == null) {
			_productIds = new ArrayList<String>();
		}
		return _productIds;
	}

	public void setProductIds(List<String> productIds) {
		_productIds = productIds;
	}

	public boolean hasProductId() {
		return _productIds != null && !_productIds.isEmpty();
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
