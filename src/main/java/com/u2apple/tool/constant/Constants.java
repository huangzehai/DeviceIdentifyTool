/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.constant;

/**
 *
 * @author Adam
 */
public class Constants {

    public static final String RECOVERY_VID = "18D1";
    public static final String COMMON_VID = "0000";
    public static final String BRAND_CONF = "/com/u2apple/tool/conf/brand.properties";
    public static final String DATA_SOURCE_CONF = "/com/u2apple/tool/conf/dataSource.properties";
    public static final String CONFIGURATION = "/com/u2apple/tool/conf/configuration.properties";

    public static final String EXCLUDED_BRANDS = "4G,qcom,LTE 4G,Android,G4,M3,4G-4G,M!,custom version,MT6589";

    public static final String SEPARATOR = ",";

    //Long time.
    public static final int TIMEOUT_LONG = 100;

    //Short time.
    public static final int TIMEOUT_SHORT = 5;

    public static final String ANDROID_DEVICES_JSON_FILE = "androidDevices.json";

    public static final String IDENTIFY_ANALYTICS_FILE = System.getProperty("user.home") + "/identify-analytics.csv";

    public static final String DEVICES_XML = "devicesXml";
    public static final String VID_DIR = "vidDir";

    public static final String VID_FILE_FORMAT = "vidFileFormat";
    
    public static final String APPLICATION_ICON="com/u2apple/tool/icon/tools.png";
     public static final String CHECKED_DEVICES = System.getProperty("user.home") + "/checkedDevices.xml";

}
