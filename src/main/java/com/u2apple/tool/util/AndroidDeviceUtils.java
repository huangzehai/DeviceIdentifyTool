/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.util;

import com.u2apple.tool.annotation.Key;
import com.u2apple.tool.constant.Constants;
import com.u2apple.tool.model.AndroidDevice;
import com.u2apple.tool.model.AndroidDeviceRanking;
import com.u2apple.tool.core.RecognitionTool;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Adam
 */
public final class AndroidDeviceUtils {

    private static final Properties brandProps;

    static {
        brandProps = new Properties();
        try {
            //init brands.
            InputStream is = RecognitionTool.class.getResourceAsStream(Constants.BRAND_CONF);
            brandProps.load(is);
        } catch (IOException ex) {
            Logger.getLogger(RecognitionTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private AndroidDeviceUtils() {

    }

    public static List<AndroidDeviceRanking> parse(List<AndroidDevice> devices) {
        List<AndroidDeviceRanking> androidDevices = new ArrayList<>();
        if (devices != null && !devices.isEmpty()) {
            for (AndroidDevice device : devices) {
                androidDevices.add(new AndroidDeviceRanking(device));
            }
        }
        return androidDevices;
    }

    public static String getBrandByProductId(String productId) {
        String brand = null;
        if (StringUtils.isNotBlank(productId)) {
            brand = StringUtils.substringBefore(productId, "-");
        }
        return brand;
    }

    public static String getProductName(String brand, String model) {
        if (StringUtils.isNotBlank(brand) && StringUtils.isNotBlank(model)) {
            String productName;
            if (StringUtils.containsIgnoreCase(model, brand)) {
                int index = model.toLowerCase().indexOf(brand.toLowerCase());
                productName = model.substring(index + brand.length());
            } else {
                productName = model;
            }
            //Remove prefix -.
            productName = StringUtils.removeStart(productName, "-");
            productName = StringUtils.removeStart(productName, "_");
            return productName.replace("_", "-").trim();
        } else {
            return null;
        }
    }

    private static String formatModel(String model) {
        return model == null ? null : model.replaceAll("-", "").replaceAll("\\s", "").replaceAll("_", "").replaceAll("\\(", "").replaceAll("\\)", "").trim();
    }

    public static String getProductId(String brand, String model) {
        String productId = null;
        if (StringUtils.isNotBlank(brand) && StringUtils.isNotBlank(model)) {
            brand = brand.toLowerCase();
            model = model.toLowerCase();
            String modelWithoutBrand;
            if (StringUtils.containsIgnoreCase(model, brand)) {
                modelWithoutBrand = StringUtils.substringAfter(model, brand);
            } else {
                modelWithoutBrand = model;
            }

            //预处理model.
            if ("samsung".equalsIgnoreCase(brand) && modelWithoutBrand.contains("-")) {
                int index = modelWithoutBrand.indexOf("-");
                //When "-" is the last char.
                if (index < modelWithoutBrand.length() - 1) {
                    modelWithoutBrand = modelWithoutBrand.substring(index + 1);
                }
            }

            String formattedModel = formatModel(modelWithoutBrand);
            //处理特殊品牌
            if ("vivo".equalsIgnoreCase(brand)) {
                productId = "bbk-vivo" + formattedModel;
            } else {
                productId = brand + "-" + formattedModel;
            }
        }
        return productId;
    }

    public static String formatBrand(String brand) {
        String formattedBrand = brand;
        if (brand != null) {
            String humanBrand = brandProps.getProperty(brand.toLowerCase());
            if (humanBrand != null) {
                formattedBrand = humanBrand;
            }
        }
        return formattedBrand;
    }

    public static String getPropertyByKey(AndroidDeviceRanking androidDevice, String key)  {
        if (androidDevice == null || key == null) {
            return null;
        }
        for (Field field : androidDevice.getClass().getSuperclass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Key.class)) {
                 Key keyAnno=field.getAnnotation(Key.class);
                 if(keyAnno.value().equals(key)){
                     try {
                         return (String) field.get(androidDevice);
                     } catch (IllegalArgumentException | IllegalAccessException ex) {
                         Logger.getLogger(AndroidDeviceUtils.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 }
            }
        }
        return null;
    }
}
