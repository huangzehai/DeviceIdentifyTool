/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adam
 */
public class DeviceI18nDaoImplTest {
    
    public DeviceI18nDaoImplTest() {
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
     * Test of getChineseBrands method, of class DeviceI18nDaoImpl.
     */
    @Test
    public void testGetChineseBrands() {
         DeviceI18nDao dao = new DeviceI18nDaoImpl();
       String brand= dao.getChineseBrand("xiaomi");
       Assert.assertEquals("小米", brand);
    }

    /**
     * Test of getEnglishBrands method, of class DeviceI18nDaoImpl.
     */
    @Test
    public void testGetEnglishBrands() {
        DeviceI18nDao dao = new DeviceI18nDaoImpl();
       String brand= dao.getEnglishBrand("xiaomi");
       Assert.assertEquals("Xiaomi", brand);
    }

    /**
     * Test of getChineseProductNames method, of class DeviceI18nDaoImpl.
     */
    @Test
    public void testGetChineseProductNames() {

    }

    /**
     * Test of getEnglishProductNames method, of class DeviceI18nDaoImpl.
     */
    @Test
    public void testGetEnglishProductNames() {

    }

    /**
     * Test of getChineseAliases method, of class DeviceI18nDaoImpl.
     */
    @Test
    public void testGetChineseAliases() {

    }

    /**
     * Test of getEnglishAliases method, of class DeviceI18nDaoImpl.
     */
    @Test
    public void testGetEnglishAliases() {

    }

    /**
     * Test of addBrand method, of class DeviceI18nDaoImpl.
     */
    @Test
    public void testAddBrand() {
       
    }

    /**
     * Test of addProductName method, of class DeviceI18nDaoImpl.
     */
    @Test
    public void testAddProductName() {
        
    }

    /**
     * Test of addAlias method, of class DeviceI18nDaoImpl.
     */
    @Test
    public void testAddAlias() {
        
    }

    /**
     * Test of brandExists method, of class DeviceI18nDaoImpl.
     */
    @Test
    public void testBrandExists() {
        DeviceI18nDao dao = new DeviceI18nDaoImpl();
        boolean exists=dao.brandExists("xiaomi");
        Assert.assertTrue(exists); 
        Assert.assertFalse(dao.brandExists("abc"));
    }

    /**
     * Test of getBrand method, of class DeviceI18nDaoImpl.
     */
    @Test
    public void testGetBrand() {
       
    }

    /**
     * Test of getProductName method, of class DeviceI18nDaoImpl.
     */
    @Test
    public void testGetProductName() {
       
    }

    /**
     * Test of getAlias method, of class DeviceI18nDaoImpl.
     */
    @Test
    public void testGetAlias() {
       
    }
    
}
