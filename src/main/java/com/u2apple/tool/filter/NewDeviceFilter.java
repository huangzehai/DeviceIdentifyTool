/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.filter;

import com.u2apple.tool.model.AndroidDeviceRanking;
import com.u2apple.tool.dao.DeviceXmlDao;
import com.u2apple.tool.dao.DeviceXmlDaoJaxbImpl;
import com.u2apple.tool.util.AndroidDeviceUtils;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Adam
 */
public class NewDeviceFilter implements Filter {

    private final DeviceXmlDao deviceXmlDao = new DeviceXmlDaoJaxbImpl();
    final Logger logger = LoggerFactory.getLogger(NewDeviceFilter.class);

    @Override
    public List<AndroidDeviceRanking> filter(List<AndroidDeviceRanking> devices) {
        List<AndroidDeviceRanking> newDevices = new ArrayList<>();
        if (devices != null) {
            String productId;
            for (AndroidDeviceRanking device : devices) {
                productId = AndroidDeviceUtils.getProductId(device.getRoProductBrand(), device.getRoProductModel());
                //When product id doesn't exist and model doesn't exist, it is a brand new device.
//                boolean productIdExists = RecognitionTool.isProductIdExist(productId);
                boolean productIdExists = deviceXmlDao.productIdExists(productId);
//                boolean modelExist = RecognitionTool.modelExists(device.getVid(), device.getRoProductModel());
                boolean modelExist = deviceXmlDao.modelExists(device.getVid(), device.getRoProductModel());
                if (!productIdExists || !modelExist) {
                    newDevices.add(device);
                }
            }
        }
        return newDevices;
    }
}
