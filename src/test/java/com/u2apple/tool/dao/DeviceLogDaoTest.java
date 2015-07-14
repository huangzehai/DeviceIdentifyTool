/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import com.shuame.wandoujia.bean.Device;

import com.u2apple.tool.model.Model;
import com.u2apple.tool.model.ProductId;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Adam
 */
public class DeviceLogDaoTest {

    public DeviceLogDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addDeviceLog method, of class DeviceLogDao.
     */
    @Test
    @Ignore
    public void testAddDeviceLog() {
        System.out.println("addDeviceLog");
        Device device = new Device();
        device.setProductId("samsung-g9006v");
        device.setBrand("Samsung");
        device.setProduct("GALAXY S5");
        device.setAlias("G9006V/联通版");
        device.setType(1);
        DeviceLogDao instance = new DeviceLogDao();
        instance.addDeviceLog(device);
    }

    @Test
    @Ignore
    public void testUpdateDeviceLog() {
        DeviceLogDao dao = new DeviceLogDao();
        String productId = "samsung-g9006v";
        String[] vids = {"04E8"};
        String modelString = "SM-G9006V";
        Map<String, String> conditions = new LinkedHashMap<>();
        ProductId aProductId = new ProductId(productId, conditions);
        Model model = new Model(modelString, aProductId);
        dao.updateDeviceLog(productId, vids, model);
    }

}
