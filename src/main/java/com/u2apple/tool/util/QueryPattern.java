/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.util;

import java.util.regex.Pattern;

/**
 *
 * @author Adam
 */
public class QueryPattern {

    private static final String MAC_ADDRESS_PATTERN = "^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$";
    
     private static final String QQ_PATTERN = "^\\d{6,10}$";

    public static boolean isMacAddress(String query) {
        return Pattern.matches(MAC_ADDRESS_PATTERN, query);
    }
    
    public static boolean isQQ(String query) {
        return Pattern.matches(QQ_PATTERN, query);
    }

}
