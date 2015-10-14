/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.filter.pattern;

import org.junit.Test;

/**
 *
 * @author Adam
 */
public class PatternValidatorTest {
   
    /**
     * Test of validate method, of class PatternValidator.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetMismatchDevices() throws Exception {
        String brand = "samsung";
        String vid="04E8";
        PatternValidator validator = new PatternValidator();
        validator.getMismatchDevices(brand,vid);
    }
    
}
