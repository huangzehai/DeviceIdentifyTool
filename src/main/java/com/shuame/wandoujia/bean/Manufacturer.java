/**
 * 
 */
package com.shuame.wandoujia.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.apache.commons.lang3.StringUtils;

/**
 * Description:
 * 
 * @author yangxu
 * @date 2014年3月6日 下午3:29:41
 * @version 1.0
 */
@XmlType(name = "Manufacturer", namespace = "http://www.shuame.com/schema/1.0.0")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item")
public class Manufacturer {

    @XmlAttribute(name = "brand")
    private String _brand;
    @XmlAttribute(name = "manufacturer")
    private String _manufacturer;
    @XmlAttribute(name = "vid")
    private String _vid;
    @XmlValue
    private long _amount;

    /**
     * brand 和 manufacturer不能同时为空
     * 
     * @return
     */
    public boolean isValid() {
        return StringUtils.isNotBlank(_brand) || StringUtils.isNotBlank(_manufacturer);
    }

    public String getBrand() {
        return _brand;
    }

    public void setBrand(String brand) {
        _brand = brand;
    }

    public String getManufacturer() {
        return _manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        _manufacturer = manufacturer;
    }

    public String getVid() {
        return _vid;
    }

    public void setVid(String vid) {
        _vid = vid;
    }

    public long getAmount() {
        return _amount;
    }

    public void setAmount(long amount) {
        _amount = amount;
    }

}
