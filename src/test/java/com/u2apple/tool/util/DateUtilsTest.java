/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.util;

import com.u2apple.tool.util.SqlUtils;
import junit.framework.Assert;
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
public class DateUtilsTest {
    
    public DateUtilsTest() {
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
     * Test of dateSub method, of class DateUtils.
     */
    @Test
    public void testDateSub() {
        System.out.println("dateSub");
        int days = 1;
        String expResult = "";
        String result = SqlUtils.dateSub(days);
//        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetMonthTable(){
        String tableName="user";
        String monthTableName=SqlUtils.getMonthlyTable(tableName);
//        Assert.assertEquals("a", "user_201501", monthTableName);
    }
    
}
