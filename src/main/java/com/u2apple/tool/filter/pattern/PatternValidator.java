/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.filter.pattern;

import com.u2apple.tool.dao.DeviceDao;
import com.u2apple.tool.filter.DevicePatternFilter;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Adam
 */
public class PatternValidator {

    public void getMismatchDevices(String brand,String vid) throws SQLException {
        DeviceDao deviceDao = new DeviceDao();
        List<AndroidDeviceRanking> devices = deviceDao.queryByBrand(brand);
        for (AndroidDeviceRanking device : devices) {
            device.setRoProductBrand(brand);
            DevicePatternFilter filter = new DevicePatternFilter();
            if (!filter.matches(device)) {
                if (vid.equals(device.getVid())) {
                    System.out.println(device);
                }
            }
        }
    }
    
    public void getmatchDevices(String brand,String vid) throws SQLException {
        DeviceDao deviceDao = new DeviceDao();
        List<AndroidDeviceRanking> devices = deviceDao.queryByBrand(brand);
        for (AndroidDeviceRanking device : devices) {
            device.setRoProductBrand(brand);
            DevicePatternFilter filter = new DevicePatternFilter();
            if (filter.matches(device)) {
                if (vid.equals(device.getVid())) {
                    System.out.println(device);
                }
            }
        }
    }

}
