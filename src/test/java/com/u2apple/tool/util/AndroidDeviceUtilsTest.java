/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.util;

import com.u2apple.tool.model.AndroidDevice;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Adam
 */
public class AndroidDeviceUtilsTest {
    
    public AndroidDeviceUtilsTest() {
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
     * Test of parse method, of class AndroidDeviceUtils.
     */
    @Test
    @Ignore
    public void testParse() {
        System.out.println("parse");
        List<AndroidDevice> devices = null;
        List<AndroidDeviceRanking> expResult = null;
        List<AndroidDeviceRanking> result = AndroidDeviceUtils.parse(devices);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBrandByProductId method, of class AndroidDeviceUtils.
     */
    @Test
    public void testGetBrandByProductId() {
        System.out.println("getBrandByProductId");
        String productId = "huawei-g730u30";
        String expResult = "huawei";
        String result = AndroidDeviceUtils.getBrandByProductId(productId);
        assertEquals(expResult, result);
    }

    /**
     * Test of getProductName method, of class AndroidDeviceUtils.
     */
    @Test
    public void testGetProductName() {
        System.out.println("getProductName");
        String brand = "huawei";
        String model = "HUAWEI G730-U30";
        String expResult = "G730-U30";
        String result = AndroidDeviceUtils.getProductName(brand, model);
        assertEquals(expResult, result);
    }

    /**
     * Test of getProductId method, of class AndroidDeviceUtils.
     */
    @Test
    public void testGetProductId() {
        System.out.println("getProductId");
        String brand = "Samsung";
        String model = "SM-N9008";
        String expResult = "samsung-smn9008";
        String result = AndroidDeviceUtils.getProductId(brand, model);
        assertEquals(expResult, result);
    }

    /**
     * Test of formatBrand method, of class AndroidDeviceUtils.
     */
    @Test
    public void testFormatBrand() {
        System.out.println("formatBrand");
        String brand = "samsung";
        String expResult = "Samsung";
        String result = AndroidDeviceUtils.formatBrand(brand);
        assertEquals(expResult, result);
    }
    
}
