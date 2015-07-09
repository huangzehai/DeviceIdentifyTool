/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import com.jcraft.jsch.JSchException;
import com.u2apple.tool.constant.Constants;
import com.u2apple.tool.persistence.Pool;
import com.u2apple.tool.model.AndroidDevice;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Adam
 */
public class DeviceDetailsDao {

    final Logger logger = LoggerFactory.getLogger(DeviceDetailsDao.class);
    private static final String SQL = "select resolution,partitions, cpu_hardware from api_device_init_log_full_140730 where vid=? and ro_product_model=? and ro_product_brand=? and resolution is not null and resolution <> '' and partitions is not null and partitions <> '' order by id desc limit 1";

    public AndroidDevice getDevice(String vid, String brand, String model) {
        AndroidDevice device = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = Pool.getShuameConnection();
            statement = connection.prepareStatement(SQL);
            statement.setString(1, vid);
            statement.setString(2, model);
            statement.setString(3, brand);
            statement.setQueryTimeout(Constants.TIMEOUT_LONG);
            rs = statement.executeQuery();
            while (rs.next()) {
                String resolution = rs.getString("resolution");
                String partitions = rs.getString("partitions");
                String cpuHardware = rs.getString("cpu_hardware");
                device = new AndroidDevice();
                device.setVid(vid);
                device.setRoProductBrand(brand);
                device.setRoProductModel(model);
                device.setResolution(resolution);
                device.setPartitions(partitions);
                device.setCpuHardware(cpuHardware);
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
        return device;
    }
}
