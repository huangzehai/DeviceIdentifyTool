/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.util;

import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.List;

/**
 *
 * @author Adam
 */
public final class CsvUtils {

    private CsvUtils() {

    }

    public static String toCsv(List<List<AndroidDeviceRanking>> deviceList) {
        StringBuilder csvBuilder=new StringBuilder();
        for (List<AndroidDeviceRanking> modelList : deviceList) {
            for (AndroidDeviceRanking model : modelList) {
                csvBuilder.append(model.getProductId());
                csvBuilder.append(",");
                csvBuilder.append(model.getRoProductModel());
                csvBuilder.append(",");
                csvBuilder.append(model.getCount());
                csvBuilder.append(System.getProperty("line.separator"));
            }
        }
        return csvBuilder.toString();
    }
    
}
