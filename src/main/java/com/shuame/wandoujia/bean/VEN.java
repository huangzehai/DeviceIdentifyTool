package com.shuame.wandoujia.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "VEN")
@XmlType(name = "VEN", namespace = "http://www.wandoujia.com/schema/1.0.0")
public class VEN {

	/**  */
	@XmlAttribute(name = "Value")
	private String _value;

	/**  */
	@XmlElement(name = "PROD")
	private List<PROD> _PRODs;

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		_value = value;
	}

	public List<PROD> getPRODs() {
		return _PRODs;
	}

	public void setPRODs(List<PROD> PRODs) {
		_PRODs = PRODs;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
