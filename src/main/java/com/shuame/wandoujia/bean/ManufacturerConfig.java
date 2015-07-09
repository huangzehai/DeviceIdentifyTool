/**
 * 
 */
package com.shuame.wandoujia.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Description:
 * 
 * @author yangxu
 * @date 2014年3月6日 下午4:10:50
 * @version 1.0
 */
@XmlType(name = "ManufacturerConfig", namespace = "http://www.shuame.com/schema/1.0.0")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Config")
public class ManufacturerConfig {

    @XmlElement(name = "item")
    private List<Manufacturer> _list = null;

    public List<Manufacturer> getList() {
        if (_list == null) {
            _list = new ArrayList<Manufacturer>();
        }
        return _list;
    }

    public void setList(List<Manufacturer> list) {
        _list = list;
    }

}
