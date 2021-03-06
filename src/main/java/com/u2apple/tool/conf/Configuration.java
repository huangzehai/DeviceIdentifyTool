/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.conf;

import com.u2apple.tool.constant.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Adam
 */
public class Configuration {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Configuration.class);
    private static final Properties properties;

    static {
        InputStream inputStream = Configuration.class.getResourceAsStream(Constants.CONFIGURATION);
        properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException ex) {
            logger.error("Fail to load configuration.properties", ex);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
