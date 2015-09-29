/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.service;

import com.jcraft.jsch.JSchException;
import com.u2apple.tool.dao.AndroidDeviceDao;
import com.u2apple.tool.dao.AndroidDeviceDaoImpl;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Adam
 */
public class IdentifyAnalyticsService {

    public List<List<AndroidDeviceRanking>> analytics() throws SQLException, IOException, JSchException {
//        DeviceDao dao = new DeviceDao();
//        List<AndroidDeviceRanking> devices = dao.listModes();
        AndroidDeviceDao dao = new AndroidDeviceDaoImpl();
        List<AndroidDeviceRanking> devices = dao.listModelWithRanking(1);
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

        return map.values().parallelStream().filter(m -> m.size() > 1).sorted((List<AndroidDeviceRanking> list1, List<AndroidDeviceRanking> list2) -> list2.get(0).getCount() - list1.get(0).getCount()).collect(Collectors.toList());
        //Sort
//       return deviceList.parallelStream().sorted((List<AndroidDeviceRanking> list1, List<AndroidDeviceRanking> list2) -> list2.get(0).getCount() - list1.get(0).getCount()).collect(Collectors.toList());
//        Collections.sort(deviceList, (List<AndroidDeviceRanking> list1, List<AndroidDeviceRanking> list2) -> list2.get(0).getCount() - list1.get(0).getCount());
    }

}
