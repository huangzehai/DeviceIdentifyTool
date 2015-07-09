/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import com.jcraft.jsch.JSchException;
import com.u2apple.tool.persistence.Pool;
import com.u2apple.tool.model.AndroidDevice;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Adam
 */
public class RecentDeviceDao {

    final Logger logger = LoggerFactory.getLogger(RecentDeviceDao.class);
    private static final String SQL = "select return_product_id as product_id, vid, ro_product_brand,ro_product_model from api_device_init_log_140730 where DATE_SUB(CURDATE(),INTERVAL 1 DAY) <= created_at and return_product_id is not null and return_product_id <> 'android-device' limit 100000 ";

    public List<AndroidDevice> getRecentDevices() {
        List<AndroidDevice> devices = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = Pool.getShuameConnection();
            statement = connection.createStatement();
            statement.setQueryTimeout(10);
            rs = statement.executeQuery(SQL);
            while (rs.next()) {
                AndroidDevice device = new AndroidDevice();
                String vid = rs.getString("vid");
                String roProductModel = rs.getString("ro_product_model");
                String brand = rs.getString("ro_product_brand");
                String productId = rs.getString("product_id");
                device.setVid(vid);
                device.setRoProductBrand(brand);
                device.setRoProductModel(roProductModel);
                device.setProductId(productId);
                devices.add(device);
            }
        } catch (SQLException | JSchException | ClassNotFoundException | PropertyVetoException | IOException ex) {
            logger.error("SQL fail", ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                logger.error("Fail when conection was closed", ex);
            }
        }
        return devices;
    }

}
