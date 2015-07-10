/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import com.shuame.wandoujia.bean.Device;
import com.shuame.wandoujia.bean.Modal;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

/**
 *
 * @author Adam
 */
public interface DeviceXmlDao {

    void addDevice(Device device);

    int deviceCount();

    void addModel(String[] vids, Modal model);

    void format(String vid);

    void flush() throws PropertyException, JAXBException;

    boolean modelExists(String vid, String text);

    boolean modelExists(String text);

    boolean productIdExists(String productId);

}
