/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.filter;

import com.u2apple.tool.filter.DevicePatternFilter;
import com.u2apple.tool.model.AndroidDevice;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Adam
 */
public class DevicePatternFilterTest {

    public DevicePatternFilterTest() {
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
     * Test of filter method, of class DevicePatternFilter.
     */
    @Test
    public void testSamsungSMN9008V() {
        AndroidDevice androidDevice = new AndroidDevice();
        androidDevice.setRoProductBrand("Samsung");
        androidDevice.setVid("04E8");
        androidDevice.setRoProductModel("SM-N9008V");
        DevicePatternFilter instance = new DevicePatternFilter();
        boolean matches = instance.matches(androidDevice);
        Assert.assertTrue(matches);
    }

    @Test
    public void testSamsungGTI9500() {
        AndroidDevice androidDevice = new AndroidDevice();
        androidDevice.setRoProductBrand("Samsung");
        androidDevice.setVid("04E8");
        androidDevice.setRoProductModel("GT-I9500");
        DevicePatternFilter instance = new DevicePatternFilter();
        boolean matches = instance.matches(androidDevice);
        Assert.assertTrue(matches);
    }

    @Test
    public void testSamsungGTI9300() {
        AndroidDevice androidDevice = new AndroidDevice();
        androidDevice.setRoProductBrand("Samsung");
        androidDevice.setVid("04E8");
        androidDevice.setRoProductModel("GT-I9300");
        DevicePatternFilter instance = new DevicePatternFilter();
        boolean matches = instance.matches(androidDevice);
        Assert.assertTrue(matches);
    }

    @Test
    public void testSamsungGTB9388() {
        AndroidDevice androidDevice = new AndroidDevice();
        androidDevice.setRoProductBrand("Samsung");
        androidDevice.setVid("04E8");
        androidDevice.setRoProductModel("GT-B9388");
        DevicePatternFilter instance = new DevicePatternFilter();
        boolean matches = instance.matches(androidDevice);
        Assert.assertTrue(matches);
    }

    @Test
    public void testSamsungGTC9388() {
        AndroidDevice androidDevice = new AndroidDevice();
        androidDevice.setRoProductBrand("Samsung");
        androidDevice.setVid("04E8");
        androidDevice.setRoProductModel("GT-C9388");
        DevicePatternFilter instance = new DevicePatternFilter();
        boolean matches = instance.matches(androidDevice);
        Assert.assertFalse(matches);
    }

    @Test
    public void testPattern() {
        String pattern = "SM-N\\d{3,4}[a-zA-Z]?";
        String text = "Sm-N9008v";
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        boolean matches = p.matcher(text).matches();
        Assert.assertTrue(matches);
    }

    @Test
    public void testHuaweiWithBrand() {
        AndroidDevice androidDevice = new AndroidDevice();
        androidDevice.setRoProductBrand("Huawei");
        androidDevice.setVid("12D1");
        androidDevice.setRoProductModel("HUAWEI G750-T00");
        DevicePatternFilter filter = new DevicePatternFilter();
        boolean matches = filter.matches(androidDevice);
        Assert.assertTrue(matches);
    }
    
      @Test
    public void testHuaweWithoutBrand() {
        AndroidDevice androidDevice = new AndroidDevice();
        androidDevice.setRoProductBrand("Huawei");
        androidDevice.setVid("12D1");
        androidDevice.setRoProductModel("G750-T00");
        DevicePatternFilter filter = new DevicePatternFilter();
        boolean matches = filter.matches(androidDevice);
        Assert.assertTrue(matches);
    }

}
