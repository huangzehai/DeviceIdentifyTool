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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
     */
    @Test
    public void testAnalyticsByCpu() throws Exception {
        System.out.println("analyticsByCpu");
        KnockOffDetectionService instance = new KnockOffDetectionServiceImpl();
        List<AndroidDeviceRanking> expResult = null;
        List<AndroidDeviceRanking> devices = instance.analyticsByCpu();
        devices.stream().forEach(System.out::println);
    }
    
}
