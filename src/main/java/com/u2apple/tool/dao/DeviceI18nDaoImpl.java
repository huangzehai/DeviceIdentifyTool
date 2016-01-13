/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import com.shuame.wandoujia.bean.Device;
import com.u2apple.tool.conf.Configuration;
import com.u2apple.tool.constant.Constants;
import com.u2apple.tool.util.AndroidDeviceUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Adam
 */
public class DeviceI18nDaoImpl implements DeviceI18nDao {

    private Properties chineseBrands;
    private Properties englishBrands;
    private Properties chineseProductNames;
    private Properties englishProductNames;
    private Properties chineseAliases;
    private Properties englishAliases;

    private boolean isBrandChanged = false;
    private boolean isProductNameChanged = false;

    public Properties getChineseBrands() {
        if (chineseBrands == null) {
            try {
                chineseBrands = loadProperties(Constants.ChineseBrands);
            } catch (IOException ex) {
                Logger.getLogger(DeviceI18nDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return chineseBrands;
    }

    public Properties getEnglishBrands() {
        if (englishBrands == null) {
            try {
                englishBrands = loadProperties(Constants.EnglishBrands);
            } catch (IOException ex) {
                Logger.getLogger(DeviceI18nDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return englishBrands;
    }

    public Properties getChineseProductNames() {
        if (chineseProductNames == null) {
            try {
                chineseProductNames = loadProperties(Constants.ChineseProducts);
            } catch (IOException ex) {
                Logger.getLogger(DeviceI18nDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return chineseProductNames;
    }

    public Properties getEnglishProductNames() {
        if (englishProductNames == null) {
            try {
                englishProductNames = loadProperties(Constants.EnglishProducts);
            } catch (IOException ex) {
                Logger.getLogger(DeviceI18nDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return englishProductNames;
    }

    public Properties getChineseAliases() {
        if (chineseAliases == null) {
            try {
                chineseAliases = loadProperties(Constants.ChineseAliases);
            } catch (IOException ex) {
                Logger.getLogger(DeviceI18nDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return chineseAliases;
    }

    public Properties getEnglishAliases() {
        if (englishAliases == null) {
            try {
                englishAliases = loadProperties(Constants.EnglishAliases);
            } catch (IOException ex) {
                Logger.getLogger(DeviceI18nDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return englishAliases;
    }

    private static Properties loadProperties(String key) throws IOException {
        String path = Configuration.getProperty(key);
        Properties properties = new Properties();
        properties.load(new FileInputStream(path));
        return properties;
    }

    @Override
    public boolean brandExists(String brandKey) {
        return getEnglishBrands().containsKey(brandKey);
    }

    @Override
    public String getChineseBrand(String brandKey) {
        if (StringUtils.isBlank(brandKey)) {
            return StringUtils.EMPTY;
        }
        return getChineseBrands().getProperty(brandKey);
    }

    @Override
    public String getChineseProductName(String productNameKey) {
        return getChineseProductNames().getProperty(productNameKey);
    }

    @Override
    public String getChineseAlias(String aliasKey) {
        return getChineseAliases().getProperty(aliasKey);
    }

    @Override
    public String getEnglishBrand(String brandKey) {
        if (StringUtils.isBlank(brandKey)) {
            return StringUtils.EMPTY;
        }
        return getEnglishBrands().getProperty(brandKey);
    }

    @Override
    public String getEnglishProductName(String productNameKey) {
        return getEnglishProductNames().getProperty(productNameKey);
    }

    @Override
    public String getEnglishAlias(String aliasKey) {
        return getEnglishAliases().getProperty(aliasKey);
    }

    public void addBrand(String brandKey, String chineseBrand, String englishBrand) {
        getChineseBrands().put(brandKey, chineseBrand);
        getEnglishBrands().put(brandKey, englishBrand);
    }

    public void addProductName(String productNameKey, String chineseProductName, String englishProductName) {
        getChineseProductNames().put(productNameKey, chineseProductName);
        getEnglishProductNames().put(productNameKey, englishProductName);
    }

    public void addAlias(String aliasKey, String chineseAlias, String englishAlias) {
        getChineseAliases().put(aliasKey, chineseAlias);
        getEnglishAliases().put(aliasKey, englishAlias);
    }

    @Override
    public void store()throws FileNotFoundException, IOException {
        if (isBrandChanged) {
             storeProperties(getChineseBrands(),Constants.ChineseBrands);
             storeProperties(getEnglishBrands(),Constants.EnglishBrands);
        }

        if (isProductNameChanged) {
            storeProperties(getChineseProductNames(),Constants.ChineseProducts);
            storeProperties(getEnglishProductNames(),Constants.EnglishProducts);
            storeProperties(getChineseAliases(),Constants.ChineseAliases);
            storeProperties(getEnglishAliases(),Constants.EnglishAliases);
        }
        this.isBrandChanged = false;
        this.isProductNameChanged = false;
    }
    
    private void storeProperties(Properties properties,String fileKey) throws FileNotFoundException, IOException {
        Properties tempProperties = new Properties() {
            @Override
            public synchronized Enumeration<Object> keys() {
                return Collections.enumeration(new TreeSet<Object>(super.keySet()));
            }
        };
        tempProperties.putAll(properties);
        String file = Configuration.getProperty(fileKey);
        tempProperties.store(new FileOutputStream(file), null);
    }

    @Override
    public void addDevice(Device device) {
        if (device != null && StringUtils.isNotBlank(device.getProductId())) {
            String brandKey = AndroidDeviceUtils.getBrandByProductId(device.getProductId());
            if (!this.brandExists(brandKey)) {
                this.addBrand(brandKey, device.getChineseBrand(), device.getEnglishBrand());
                this.isBrandChanged = true;
            }
            this.addProductName(device.getProductId(), device.getProduct(), device.getEnglishProduct());
            this.addAlias(device.getProductId(), device.getAlias(), device.getEnglishAlias());
            this.isProductNameChanged = true;
        }
    }
}
