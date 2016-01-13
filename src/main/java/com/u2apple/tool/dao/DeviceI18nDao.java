/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import com.shuame.wandoujia.bean.Device;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Adam
 */
public interface DeviceI18nDao {
  String getChineseBrand(String brandKey);
  String getChineseProductName(String productNameKey);
  String getChineseAlias(String aliasKey);
  
  String getEnglishBrand(String brandKey);
  String getEnglishProductName(String productNameKey);
  String getEnglishAlias(String aliasKey);
  
//  void addBrand(String brandKey,String chineseBrand,String englishBrand);
//  void addProductName(String productNameKey,String chineseProductName,String englishProductName);
//  void addAlias(String aliasKey,String chineseAlias,String englishAlias);
  
  void addDevice(Device device);
  
  boolean brandExists(String brandKey);
  
  void store()throws FileNotFoundException, IOException;
}
