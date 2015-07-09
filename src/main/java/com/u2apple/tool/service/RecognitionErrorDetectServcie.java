/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.service;

import com.u2apple.tool.dao.RecentDeviceDao;
import com.u2apple.tool.model.AndroidDevice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Adam
 * 
 */
@Deprecated
public class RecognitionErrorDetectServcie {
    
    public static void main(String[] args)  {
        RecognitionErrorDetectServcie service = new RecognitionErrorDetectServcie();
        List<AndroidDevice> devices = service.errorDetect();
        for (AndroidDevice device : devices) {
            System.out.println(device);
        }
    }
    
    @Deprecated
    public List<AndroidDevice> errorDetect() {
        List<AndroidDevice> devices = new RecentDeviceDao().getRecentDevices();
        String expectedModel;
        Map<String, AndroidDevice> errorDevices = new HashMap<>();
        for (AndroidDevice device : devices) {
            expectedModel = getModelByProductId(device.getProductId());
            if (isSuspicious(device.getRoProductModel(), expectedModel)) {
                errorDevices.put(device.getRoProductModel(), device);
            }
        }
        return new ArrayList<>(errorDevices.values());
    }
    
    private boolean isSuspicious(String model, String expectedModel) {
        boolean isSuspicious = false;
        if (model != null && model.contains(expectedModel)) {
            model = model.toLowerCase();
            expectedModel = expectedModel.toLowerCase();
            int index = model.indexOf(expectedModel);
            isSuspicious = model.length() > index + expectedModel.length();
        }
        return isSuspicious;
    }  
    
    private String getModelByProductId(String productId) {
        String brand = null;
        if (StringUtils.isNotBlank(productId)) {
            brand = StringUtils.substringAfter(productId, "-");
        }
        return brand;
    }
    
}
