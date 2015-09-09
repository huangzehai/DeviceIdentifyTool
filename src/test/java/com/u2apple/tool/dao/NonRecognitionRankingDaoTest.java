/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import com.u2apple.tool.dao.NonRecognitionRankingDao;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Adam
 */
public class NonRecognitionRankingDaoTest {
    
    public NonRecognitionRankingDaoTest() {
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
     * Test of getWhiteListNonRecognizedDevicesRanking method, of class NonRecognitionRankingDao.
     */
    @Test
    @Ignore
    public void testGetWhiteListNonRecognizedDevicesRanking() throws Exception {
        System.out.println("getWhiteListNonRecognizedDevicesRanking");
        int days = 0;
        List<AndroidDeviceRanking> expResult = null;
        List<AndroidDeviceRanking> result = new NonRecognitionRankingDao().getWhiteListNonRecognizedDevicesRanking(days);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
