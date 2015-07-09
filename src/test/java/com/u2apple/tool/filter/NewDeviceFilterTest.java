/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.filter;

import com.u2apple.tool.filter.NewDeviceFilter;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Adam
 */
public class NewDeviceFilterTest {
    
    public NewDeviceFilterTest() {
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
     * Test of filter method, of class DeviceExistenceFilter.
     */
    @Test
    public void testFilter() {
        AndroidDeviceRanking device=new AndroidDeviceRanking();
        device.setRoProductBrand("Samsung");
        device.setRoProductModel("SM-N9008V");
        device.setVid("04E8");
        List<AndroidDeviceRanking> devices = new ArrayList<>();
        devices.add(device);
        NewDeviceFilter instance = new NewDeviceFilter();
        List<AndroidDeviceRanking> expResult = null;
        List<AndroidDeviceRanking> result = instance.filter(devices);
        System.out.println(result);
        //assertEquals(expResult, result);
  
    }
    
}
