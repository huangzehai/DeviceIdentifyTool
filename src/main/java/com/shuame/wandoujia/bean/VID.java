package com.shuame.wandoujia.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "VID")
@XmlType(name = "VID", namespace = "http://www.wandoujia.com/schema/1.0.0")
public class VID {

	/**  */
	@XmlAttribute(name = "Value")
	private String _value;

	/**  */
	@XmlElement(name = "ProductId")
	private String _productId;

	/**  */
	@XmlElementWrapper(name = "PIDs")
	@XmlElement(name = "PID")
	private List<PID> _PIDs;

	/**  */
	@XmlElementWrapper(name = "Names")
	@XmlElement(name = "Name")
	private List<Name> _names;

	/**  */
	@XmlElementWrapper(name = "Modals")
	@XmlElement(name = "Modal")
	private List<Modal> _modals;

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

	public List<PID> getPIDs() {
		if (_PIDs == null) {
			_PIDs = new ArrayList<PID>();
		}
		return _PIDs;
	}

	public void setPIDs(List<PID> PIDs) {
		_PIDs = PIDs;
	}

	public List<Name> getNames() {
		if (_names == null) {
			_names = new ArrayList<Name>();
		}
		return _names;
	}

	public void setNames(List<Name> names) {
		_names = names;
	}

	public List<Modal> getModals() {
		if (_modals == null) {
			_modals = new ArrayList<Modal>();
		}
		return _modals;
	}

	public void setModals(List<Modal> modals) {
		_modals = modals;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
