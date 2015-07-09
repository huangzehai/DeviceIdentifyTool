/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import com.u2apple.tool.model.AndroidDevice;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

/**
 *
 * @author Adam
 */
public class DeviceDaoTest {

    public DeviceDaoTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testQueryByVidAndModel() {
        for (int i = 0; i < 20; i++) {
            DeviceDao dao = new DeviceDao();
            try {
                List<AndroidDevice> androidDevices = dao.queryByVidAndModel("12D1", "PE-TL20", 10);
                System.out.println(androidDevices.size());
//            for (AndroidDevice device : androidDevices) {
//                System.out.println(device);
//            }
            } catch (SQLException ex) {
                Logger.getLogger(DeviceDaoTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Test
    @Ignore
    public void testQueryAllDetailByVidAndModel() {
        DeviceDao dao = new DeviceDao();
        List<AndroidDevice> androidDevices;
        try {
            androidDevices = dao.queryAllDetailByVidAndModel("0BB4", "ELIYA S898", 10);
            for (AndroidDevice device : androidDevices) {
                System.out.println(device);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeviceDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testQueryByBrand() throws SQLException {
        DeviceDao dao = new DeviceDao();
        List<AndroidDeviceRanking> devices = dao.queryByBrand("samsung");
        for (AndroidDeviceRanking device : devices) {
            System.out.println(device);
        }
    }

    @Test
    @Ignore
    public void testQueryByMacAddress() throws SQLException {
        DeviceDao dao = new DeviceDao();
        List<AndroidDevice> devices = dao.queryByMacAddress("5C-AC-4C-99-02-1E", 2);
        for (AndroidDevice device : devices) {
            System.out.println(device);
        }
    }

    @Test
    public void testGetMacAddressByQQ() throws SQLException {
        DeviceDao dao = new DeviceDao();
        String macAddress = dao.getMacAddressByQQ("8557040");
        Assert.assertEquals("The actual mac addrss should be the same as expected.", "5C-AC-4C-99-02-1E", macAddress);
    }
    
    @Test
    public void testListModels() throws SQLException{
        DeviceDao dao = new DeviceDao();
       List<AndroidDeviceRanking> devices= dao.listModes();
       for(AndroidDeviceRanking device:devices){
           System.out.println(device);
       }
    }

}
