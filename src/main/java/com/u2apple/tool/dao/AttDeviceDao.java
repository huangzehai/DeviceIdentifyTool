package com.u2apple.tool.dao;

import com.jcraft.jsch.JSchException;
import com.u2apple.tool.constant.Constants;
import com.u2apple.tool.persistence.Pool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.u2apple.tool.model.AndroidDeviceRanking;
import java.beans.PropertyVetoException;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttDeviceDao {

    final Logger logger = LoggerFactory.getLogger(AttDeviceDao.class);

    private static final String SQL = "SELECT return_product_id as product_id, ro_product_brand AS `ro.product.brand`, ro_product_model AS `ro.product.model`, ro_product_device AS `ro.product.device`, COUNT(*) as count from( select return_product_id ,ro_product_brand ,ro_product_model, ro_product_device FROM `api_device_init_log_140730` WHERE return_product_id = ? and DATE_SUB(CURDATE(),INTERVAL 10 DAY) <= created_at and ro_product_brand is not null and ro_product_brand !='' and ro_product_model is not null and ro_product_model != '' and ro_product_device is not null and ro_product_device !='' limit 100) as t GROUP BY ro_product_brand, ro_product_model, ro_product_device ORDER BY count DESC";

    private static final String QUERY_BY_RPODUCT_AND_BRAND_SQL = "SELECT return_product_id as product_id, ro_product_brand AS `ro.product.brand`, ro_product_model AS `ro.product.model`, ro_product_device AS `ro.product.device`, COUNT(*) as count from( select return_product_id ,ro_product_brand ,ro_product_model, ro_product_device FROM `api_device_init_log_140730` WHERE return_product_id = ? and lower(ro_product_brand) =lower(?) and DATE_SUB(CURDATE(),INTERVAL 10 DAY) <= created_at and ro_product_brand is not null and ro_product_brand !='' and ro_product_model is not null and ro_product_model != '' and ro_product_device is not null and ro_product_device !='' limit 100) as t GROUP BY ro_product_brand, ro_product_model, ro_product_device ORDER BY count DESC";

    public List<AndroidDeviceRanking> queryByProductId(String theProductId) {
        List<AndroidDeviceRanking> devices = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = Pool.getStatConnection();
            statement = connection.prepareStatement(SQL);
            statement.setString(1, theProductId);
            statement.setQueryTimeout(Constants.TIMEOUT_LONG);
            rs = statement.executeQuery();
            while (rs.next()) {
                AndroidDeviceRanking device = new AndroidDeviceRanking();
                String roProductModel = rs.getString("ro.product.model");
                String brand = rs.getString("ro.product.brand");
                String roProductDevice = rs.getString("ro.product.device");
                String productId = rs.getString("product_id");
                int count = rs.getInt("count");
                device.setRoProductBrand(brand);
                device.setRoProductModel(roProductModel);
                device.setProductId(productId);
                device.setRoProductDevice(roProductDevice);
                device.setCount(count);
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

    public List<AndroidDeviceRanking> queryByProductId(String theProductId, String theBrand) {
        List<AndroidDeviceRanking> devices = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = Pool.getStatConnection();
            statement = connection.prepareStatement(QUERY_BY_RPODUCT_AND_BRAND_SQL);
            statement.setString(1, theProductId);
            statement.setString(2, theBrand);
            rs = statement.executeQuery();
            while (rs.next()) {
                AndroidDeviceRanking device = new AndroidDeviceRanking();
                String roProductModel = rs.getString("ro.product.model");
                String brand = rs.getString("ro.product.brand");
                String roProductDevice = rs.getString("ro.product.device");
                String productId = rs.getString("product_id");
                int count = rs.getInt("count");
                device.setRoProductBrand(brand);
                device.setRoProductModel(roProductModel);
                device.setProductId(productId);
                device.setRoProductDevice(roProductDevice);
                device.setCount(count);
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
