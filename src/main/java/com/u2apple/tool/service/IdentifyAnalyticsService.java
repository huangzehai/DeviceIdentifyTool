/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.service;

import com.u2apple.tool.dao.DeviceDao;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Adam
 */
public class IdentifyAnalyticsService {

    public List<List<AndroidDeviceRanking>> analytics() throws SQLException {
        DeviceDao dao = new DeviceDao();
        List<AndroidDeviceRanking> devices = dao.listModes();
        Map<String, List<AndroidDeviceRanking>> map = new HashMap<>();
        String productId;
        for (AndroidDeviceRanking device : devices) {
            productId = device.getProductId();
            if (map.containsKey(productId)) {
                map.get(productId).add(device);
            } else {
                List<AndroidDeviceRanking> list = new ArrayList<>();
                list.add(device);
                map.put(productId, list);
            }
        }

        List<List<AndroidDeviceRanking>> deviceList = new ArrayList<>();
        for (List<AndroidDeviceRanking> models : map.values()) {
            if (models.size() > 1) {
                deviceList.add(models);
            }
        }

        //
        Collections.sort(deviceList, new Comparator<List<AndroidDeviceRanking>>() {

            @Override
            public int compare(List<AndroidDeviceRanking> list1, List<AndroidDeviceRanking> list2) {
                return list2.get(0).getCount() - list1.get(0).getCount();
            }
        });
        
        return deviceList;
    }

}
