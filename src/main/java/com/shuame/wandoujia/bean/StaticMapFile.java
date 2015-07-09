package com.shuame.wandoujia.bean;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 解析自豌豆荚提供的配置文件。
 * 
 * @author mwsxh
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "StaticMapFile")
@XmlType(name = "StaticMapFile", namespace = "http://www.wandoujia.com/schema/1.0.0")
public class StaticMapFile {

	/** 配置文件版本号 */
	@XmlElement(name = "Version")
	private int _version;

	/** 设备列表 */
	@XmlElementWrapper(name = "Devices")
	@XmlElement(name = "Device")
	private List<Device> _devices;

	/** 硬件列表（VID） */
	@XmlElementWrapper(name = "VIDs")
	@XmlElement(name = "VID")
	private List<VID> _vids;

	/** VEN列表 */
	@XmlElementWrapper(name = "VENs")
	@XmlElement(name = "VEN")
	private List<VEN> _vens;

	public int getVersion() {
		return _version;
	}

	public void setVersion(int version) {
		_version = version;
	}

	public List<Device> getDevices() {
		if (_devices == null) {
			_devices = new ArrayList<Device>();
		}
		return _devices;
	}

	public void setDevices(List<Device> devices) {
		_devices = devices;
	}

	public void addDevice(Device device) {
		getDevices().add(device);
	}

	public List<VID> getVids() {
		if (_vids == null) {
			_vids = new ArrayList<VID>();
		}
		return _vids;
	}

	public void setVids(List<VID> vids) {
		_vids = vids;
	}

	public List<VEN> getVens() {
		if (_vens == null) {
			_vens = new ArrayList<VEN>();
		}
		return _vens;
	}

	public void setVens(List<VEN> vens) {
		_vens = vens;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	

}
