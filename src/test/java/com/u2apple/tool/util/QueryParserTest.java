/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.util;

import com.u2apple.tool.util.QueryPattern;
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
public class QueryParserTest {
    
    public QueryParserTest() {
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
     * Test of isMacAddress method, of class QueryParser.
     */
    @Test
    public void testIsMacAddress() {
        System.out.println("isMacAddress");
        String query = "74-27-EA-B0-4D-2C";
        boolean expResult = true;
        boolean result = QueryPattern.isMacAddress(query);
        assertEquals(expResult, result);
    }

    /**
     * Test of isQQ method, of class QueryParser.
     */
    @Test
    public void testIsQQ() {
        System.out.println("isQQ");
        String query = "123456789";
        boolean expResult = true;
        boolean result = QueryPattern.isQQ(query);
        assertEquals(expResult, result);
    }
    
}
