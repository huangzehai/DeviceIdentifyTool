/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Adam
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CheckedDevices")
@XmlType(name = "CheckedDevices", namespace = "http://www.wandoujia.com/schema/1.0.0")
public class CheckedDevices {

    @XmlElementWrapper(name = "Devices")
    @XmlElement(name = "Device")
    private List<AndroidDeviceRanking> devices;

    public List<AndroidDeviceRanking> getDevices() {
        return devices;
    }

    public void setDevices(List<AndroidDeviceRanking> devices) {
        this.devices = devices;
    }

    @Override
    public String toString() {
        return "CheckedDevices{" + "devices=" + devices + '}';
    }

}
