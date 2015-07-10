package com.u2apple.tool.core;

import com.u2apple.tool.cache.DeviceCache;
import com.u2apple.tool.constant.Configuration;
import com.u2apple.tool.model.Model;
import com.u2apple.tool.util.DeviceFactory;
import com.u2apple.tool.util.FreemarkerUtils;
import java.io.IOException;

import org.dom4j.DocumentException;

import com.u2apple.tool.dao.DeviceXmlDaoImpl;
import com.u2apple.tool.model.Device;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

public class RecognitionTool {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RecognitionTool.class);

    public static int getDeviceCount() throws DocumentException {
        DeviceXmlDaoImpl xmlEditor = new DeviceXmlDaoImpl(Configuration.getDevicesXml());
        return xmlEditor.getDeviceCount();
    }

    @Deprecated
    public static void addDevice(String productId, String brand, String product, String alias, int type) {
        DeviceXmlDaoImpl deviceXmlDao = new DeviceXmlDaoImpl(Configuration.getDevicesXml());
        Device device = DeviceFactory.createDevice(productId, brand, product, alias, type);
        try {
            deviceXmlDao.addDevice(device);
            DeviceCache.addProductId(device.getProductId());
            //Record log.
//            DeviceLogDao deviceLogDao=new DeviceLogDao();
//            deviceLogDao.addDeviceLog(device);
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(RecognitionTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * TODO throw exception.
     *
     * @param vids
     * @param model
     */
     @Deprecated
    public static void addModel(String[] vids, Model model) {
        DeviceXmlDaoImpl deviceXmlDao = new DeviceXmlDaoImpl(Configuration.getDevicesXml());
        try {
            deviceXmlDao.addModel(vids, model);
            DeviceCache.addModel(vids, model.getValues());
        } catch (DocumentException | IOException ex) {
            logger.error("Fail to add model {}", ex);
        }
    }

     @Deprecated
    public static void sortModel(String vid) {
        DeviceXmlDaoImpl xmlEditor = new DeviceXmlDaoImpl(Configuration.getDevicesXml());
        try {
            xmlEditor.sortModels(vid);
        } catch (DocumentException | IOException ex) {
            logger.error("Fail to sort model {}", ex);
        }
    }

    public static String getDevice(String productId, String brand, String product, String alias, int type) {
        Device device = DeviceFactory.createDevice(productId, brand, product, alias, type);
        return FreemarkerUtils.generate(device, "device.ftl");
    }

    public static boolean modelExists(String text) {
        if (StringUtils.isBlank(text)) {
            return false;
        }

        Map<String, Set<String>> models = DeviceCache.getModels();
        for (Set<String> ms : models.values()) {
            if (ms.contains(text.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean modelExists(String vid, String text) {
        if (StringUtils.isBlank(vid) || StringUtils.isBlank(text)) {
            return false;
        }
        Map<String, Set<String>> models = DeviceCache.getModels();
        Set<String> modelsOfVid = models.get(vid);
        return modelsOfVid == null ? false : modelsOfVid.contains(text.toLowerCase());
    }

    public static boolean isProductIdExist(String productId) {
        Set<String> productIds = DeviceCache.getProductIds();
        return productIds.contains(productId);
    }
}
