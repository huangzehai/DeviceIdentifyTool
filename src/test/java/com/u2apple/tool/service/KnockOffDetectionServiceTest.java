/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.service;

import com.jcraft.jsch.JSchException;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.io.IOException;
import java.util.List;
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
public class KnockOffDetectionServiceTest {
    
    public KnockOffDetectionServiceTest() {
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
     * Test of analyticsByCpu method, of class KnockOffDetectionService.
     * @throws java.io.IOException
     * @throws com.jcraft.jsch.JSchException
     */
    @Test
    public void testAnalyticsByCpu() throws IOException, JSchException{
        FakeDetectionService instance = new FakeDetectionServiceImpl();
        List<AndroidDeviceRanking> devices = instance.analyticsByCpu();
        Assert.assertNotNull(devices);
        Assert.assertTrue(devices.size() > 0);
    }
    
}
