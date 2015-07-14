package com.u2apple.tool.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Device", namespace = "http://www.wandoujia.com/schema/1.0.0")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Device")
public class AndroidDeviceRanking extends AndroidDevice {

    @XmlTransient
    private int count;
    @XmlElement(name = "checkDate")
    private Date checkDate;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public AndroidDeviceRanking(String vid, String roProductModel, String roProductBrand, int count) {
        super(vid, roProductModel, roProductBrand);
        this.count = count;
    }

    public AndroidDeviceRanking(AndroidDevice androidDevice) {
        super(androidDevice.getVid(), androidDevice.getRoProductModel(), androidDevice.getRoProductBrand(),
                androidDevice.getProductId());
    }

    public AndroidDeviceRanking() {

    }

    @Override
    public String toString() {
        return "AndroidDeviceRanking [count=" + count + ", vid=" + getVid() + ", roProductModel=" + getRoProductModel()
                + ", roProductBrand=" + getRoProductBrand() + ", checkDate=" + checkDate + "]";
    }

}
