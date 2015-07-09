/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Adam
 */
public final class SqlUtils {
    
    public static String dateSub(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(calendar.getTime());
    }
    
    public static String getMonthlyTable(String tableName) {
        DateFormat df = new SimpleDateFormat("yyyyMM");
        return new StringBuilder(tableName).append("_").append(df.format(new Date())).toString();
    }
    
    public static String createMonthlyQuery(String query,String table){
          return String.format(query, SqlUtils.getMonthlyTable(table));
    }
    
}
