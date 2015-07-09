/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.util;

/**
 *
 * @author Adam
 */
public final class ArrayUtils {

    private ArrayUtils() {

    }

    public static boolean containsIgoreCase(String[] array, String text) {
        if (array == null || array.length == 0 || text == null || text.length() == 0) {
            return false;
        }

        for (String element : array) {
            if (element.equalsIgnoreCase(text)) {
                return true;
            }
        }
        return false;
    }

}
