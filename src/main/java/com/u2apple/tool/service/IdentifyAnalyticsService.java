/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.service;

import com.jcraft.jsch.JSchException;
import com.u2apple.tool.constant.Constants;
import com.u2apple.tool.dao.AndroidDeviceDao;
import com.u2apple.tool.dao.AndroidDeviceDaoImpl;
import com.u2apple.tool.model.AndroidDeviceRanking;
import com.u2apple.tool.util.AndroidDeviceUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Adam
 */
public class IdentifyAnalyticsService {

    private static final Map<String, String> modelProductIdMapping = loadModels();

    private static Map loadModels() {
        Map<String, String> map = new HashMap<>();
        Properties props = new Properties();
        try {
            props.load(IdentifyAnalyticsService.class.getResourceAsStream(Constants.MODELS));
            props.forEach((Object key, Object value) -> {
                String productId = (String) key;
                String model = AndroidDeviceUtils.formatModel((String) value);
                if (model.contains(",")) {
                    String[] models = model.split(",");
                    for (String m : models) {
                        map.put(m.trim(), productId);
                    }
                } else {
                    map.put(model, productId);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(IdentifyAnalyticsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    /**
     * 分析错误识别机型。
     *
     * @param devices
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws JSchException
     */
    public List<List<AndroidDeviceRanking>> analytics(List<AndroidDeviceRanking> devices) throws SQLException, IOException, JSchException {
        devices = devices.stream().filter(device -> matches(device.getRoProductModel(), device.getProductId())).collect(Collectors.toList());
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
    }

    /**
     * 列出刷机精灵PC版错误识别机型。
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws JSchException 
     */
    public List<List<AndroidDeviceRanking>> listErrorIdentifiedDevicesForShuamePC() throws SQLException, IOException, JSchException {
        AndroidDeviceDao dao = new AndroidDeviceDaoImpl();
        int days = 1;
        List<AndroidDeviceRanking> devices = dao.listModelWithRanking(days);
        return analytics(devices);
    }
    
    public List<List<AndroidDeviceRanking>> listErrorIdentifiedDevicesForShuameMobile() throws SQLException, IOException, JSchException {
        AndroidDeviceDao dao = new AndroidDeviceDaoImpl();
        int days = 1;
        List<AndroidDeviceRanking> devices = dao.listDevicesOfShuameMobile(days);
        return analytics(devices);
    }

    private boolean matches(String model, String productId) {
        boolean matches = false;
        if (StringUtils.isNotBlank(model) && StringUtils.isNotBlank(productId)) {
            String pureModel = AndroidDeviceUtils.formatModel(model);
            if (!isWhilteListModel(pureModel, productId)) {
                String[] brandAndModel = productId.split("-");
                if (brandAndModel.length >= 2) {
                    String expectedModel = brandAndModel[1];
                    if (StringUtils.containsIgnoreCase(pureModel, expectedModel)) {
                        String lowerPureModel = pureModel.toLowerCase();
                        String lowerExpectedModel = expectedModel.toLowerCase();
                        int index = lowerPureModel.indexOf(lowerExpectedModel);
                        if (pureModel.length() > index + expectedModel.length()) {
                            matches = true;
                        }
                    }
                }
            }
        }
        return matches;
    }

    private boolean isWhilteListModel(String model, String productId) {
        return StringUtils.equals(modelProductIdMapping.get(model), productId);
    }
}
