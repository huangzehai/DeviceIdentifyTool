/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import com.shuame.wandoujia.bean.Device;
import com.shuame.wandoujia.bean.Modal;
import com.shuame.wandoujia.bean.ProductId;
import com.shuame.wandoujia.bean.StaticMapFile;
import com.shuame.wandoujia.bean.Value;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.junit.Test;

/**
 *
 * @author Adam
 */
public class DeviceXmlDaoJaxbImplTest {

    public DeviceXmlDaoJaxbImplTest() {
    }

    @Test
    public void testAddDevice() throws JAXBException {
        DeviceXmlDao dao = new DeviceXmlDaoJaxbImpl();
        Device device = new Device();
        device.setBrand("百加");
        device.setProduct("100LTE");
        device.setAlias("移动4G");
        device.setProductId("100jia-100lte");
        dao.addDevice(device);
        dao.flush();
    }

    @Test
    public void testAddModel() throws JAXBException {
        DeviceXmlDao dao = new DeviceXmlDaoJaxbImpl();
        String[] vids = {"0408"};
        Modal model = new Modal();
        ProductId productId = new ProductId();
        productId.setValue("100jia-100lte");
        productId.setCondition("brand=100jia");
        List<ProductId> productIds = new ArrayList<>();
        productIds.add(productId);
        model.setProductId(productIds);
        Value value = new Value();
        value.setMethod("equals");
        value.setValue("N100LTEAB");
        List<Value> values = new ArrayList<>();
        values.add(value);
        model.setValues(values);
        dao.addModel(vids, model);
        dao.flush();
    }
  
}
