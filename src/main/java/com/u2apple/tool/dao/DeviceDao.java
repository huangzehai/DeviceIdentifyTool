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
import com.u2apple.tool.model.AndroidDeviceRanking;
import com.u2apple.tool.util.SqlUtils;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Adam
 */
@Deprecated
public class DeviceDao {

    final Logger logger = LoggerFactory.getLogger(DeviceDao.class);
    private static final String SQL = "select vid, ro_product_brand, ro_product_model, return_product_id as product_id from %s where mac_address_new='74-27-EA-B0-4D-2C' order by id desc limit 1";
    private static final String SQL_FULL = "select partitions,resolution from %s where mac_address='74-27-EA-B0-4D-2C' and ro_product_model= ? order by id desc limit 1";
    private static final String QUERY_BY_MODEL_SQL = "select vid,ro_product_brand,ro_product_model, return_product_id as product_id from %s where  ro_product_model= ?  order by id desc limit 1";
    private static final String QUERY_LIKE_MODEL_SQL = "select vid,ro_product_brand,ro_product_model, return_product_id as product_id from %s where  lower(ro_product_model) like ? order by id desc limit 10;";

    private static final String DEVICE_DETAIL_SQL = "select mac_address_new as mac_address,vid,pid,prot,sn,adb_device,product_id,ro_product_device,ro_product_model,ro_product_brand,ro_product_board,ro_product_manufacturer,ro_hardware,ro_build_display_id,custom_props,android_version,cpu_hardware,created_at,return_product_id,identified,ro_product_name,ro_build_fingerprint from %s where ro_product_model = ? and vid=? and ro_product_brand =? order by id desc limit ?";
    
     private static final String DEVICE_DETAIL_OF_SHUAME_MOBILE_SQL = "select mac_address,vid,pid,prot,sn,product_id,ro_product_device,ro_product_model,ro_product_brand,ro_product_board,ro_product_manufacturer,ro_hardware,custom_props,android_version,cpu_hardware,created_at,return_product_id,identified,ro_product_name,ro_build_fingerprint from %s where ro_product_model = ? and vid=? and ro_product_brand =?  order by id desc limit ?";

    private static final String DEVICE_ALL_DETAIL_SQL = "select mac_address_new as mac_address,vid,pid,prot,sn,adb_device,product_id,ro_product_device,ro_product_model,ro_product_brand,ro_product_board,ro_product_manufacturer,ro_hardware,ro_build_display_id,custom_props,android_version,cpu_hardware,created_at,return_product_id,identified,resolution,partitions from %s where ro_product_model = ? and vid=?  order by id desc limit ?";

    private static final String QUERY_BY_MAC_ADDRESS_SQL = "select mac_address_new as mac_address,vid,pid,prot,sn,adb_device,product_id,ro_product_device,ro_product_model,ro_product_brand,ro_product_board,ro_product_manufacturer,ro_hardware,ro_build_display_id,custom_props,android_version,cpu_hardware,created_at,return_product_id,identified,resolution,partitions from %s where mac_address_new = ?  order by id desc limit ?";

    private static final String QUERY_BY_BRAND = "select  ro_product_model, vid ,count(*) as count from (select vid,ro_product_model from %s where lower(ro_product_brand) =? order by id desc limit 10000) t group by ro_product_model,vid order by count desc";

    private static final String GET_MAC_ADDRESS_BY_QQ = "select pc_mac_address as mac_address from api_device_binding where qq=?  order by id desc limit 1";
    
    private static final String  MODEL_AND_PRODUCT_ID_ANALYTICS_SQL="select return_product_id,ro_product_model, count(*) as count from %s where created_at > subdate(curdate(), INTERVAL 1 DAY) and return_product_id !='' and return_product_id  != 'android-device' and ro_product_model !='' group by return_product_id,ro_product_model order by return_product_id, count desc";

    public AndroidDevice getLatestDevice() {
        AndroidDevice device = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = Pool.getTestStatConnection();
            statement = connection.createStatement();
            statement.setQueryTimeout(Constants.TIMEOUT_LONG);
            String sql=SqlUtils.createMonthlyQuery(SQL, "log_device_init");
            rs = statement.executeQuery(sql);

            if (rs.next()) {
                device = new AndroidDevice();
                String vid = rs.getString("vid");
                String roProductModel = rs.getString("ro_product_model");
                String brand = rs.getString("ro_product_brand");
                String productId = rs.getString("product_id");
                device.setVid(vid);
                device.setRoProductBrand(brand);
                device.setRoProductModel(roProductModel);
                device.setProductId(productId);

                //Get resolution and partitios.
                if (StringUtils.isNotBlank(roProductModel)) {
                    String sqlFull=SqlUtils.createMonthlyQuery(SQL_FULL, "log_device_init");
                    PreparedStatement preparedStatement = connection.prepareStatement(SQL_FULL);
                    preparedStatement.setString(1, roProductModel);
                    rs = preparedStatement.executeQuery();
                    if (rs.next()) {
                        String resolution = rs.getString("resolution");
                        String partitions = rs.getString("partitions");
                        device.setResolution(resolution);
                        device.setPartitions(partitions);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException | PropertyVetoException | IOException ex) {
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

    public List<AndroidDevice> queryByModel(String model) {
        List<AndroidDevice> devices = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = Pool.getStatConnection();
            String sql=SqlUtils.createMonthlyQuery(QUERY_BY_MODEL_SQL, "log_device_init");
            statement = connection.prepareStatement(sql);
            statement.setString(1, model);
            statement.setQueryTimeout(Constants.TIMEOUT_SHORT);
            rs = statement.executeQuery();
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

    public List<AndroidDevice> queryLikeModel(String model) {
        List<AndroidDevice> devices = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = Pool.getStatConnection();
            String sql=SqlUtils.createMonthlyQuery(QUERY_LIKE_MODEL_SQL, "log_device_init");
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + model.toLowerCase() + "%");
            statement.setQueryTimeout(Constants.TIMEOUT_SHORT);
            rs = statement.executeQuery();
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

    public List<AndroidDevice> queryByVidAndModel(String aVid, String pBrand, String model, int limit) throws SQLException {
        List<AndroidDevice> devices = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = Pool.getStatConnection();
            String sql = SqlUtils.createMonthlyQuery(DEVICE_DETAIL_SQL, "log_device_init");
            statement = connection.prepareStatement(sql);
            statement.setString(1, model);
            statement.setString(2, aVid);
            statement.setString(3, pBrand);
            statement.setInt(4, limit);
            statement.setQueryTimeout(Constants.TIMEOUT_SHORT);
            rs = statement.executeQuery();
            while (rs.next()) {
                AndroidDevice device = new AndroidDevice();
                String macAddress = rs.getString("mac_address");
                String vid = rs.getString("vid");
                String pid = rs.getString("pid");
                String prot = rs.getString("prot");
                String sn = rs.getString("sn");
                String adbDevice = rs.getString("adb_device");
                String productId = rs.getString("product_id");
                String toProductDevice = rs.getString("ro_product_device");
                String roProductModel = rs.getString("ro_product_model");
                String brand = rs.getString("ro_product_brand");
                String roProductBoard = rs.getString("ro_product_board");
                String roProductManufacturer = rs.getString("ro_product_manufacturer");
                String roHardware = rs.getString("ro_hardware");
                String roBuildDisplayId = rs.getString("ro_build_display_id");
                String customProps = rs.getString("custom_props");
                String createdAt = rs.getString("created_at");
                String returnProductId = rs.getString("return_product_id");
                String identified = rs.getString("identified");
                String androidVersion = rs.getString("android_version");
                String cpuHardware = rs.getString("cpu_hardware");
                String roProductName = rs.getString("ro_product_name");
                String roBuildFingerprint = rs.getString("ro_build_fingerprint");

                device.setMacAddress(macAddress);
                device.setPid(pid);
                device.setProt(prot);
                device.setSn(sn);
                device.setAdbDevice(adbDevice);
                device.setRoProductDevice(toProductDevice);
                device.setRoProductBoard(roProductBoard);
                device.setRoProductManufacturer(roProductManufacturer);
                device.setRoHardware(roHardware);
                device.setRoBuildDisplayId(roBuildDisplayId);
                device.setCustomProps(customProps);
                device.setCreatedAt(createdAt);
                device.setReturnProductId(returnProductId);
                device.setIdentified(identified);
                device.setVid(vid);
                device.setRoProductBrand(brand);
                device.setRoProductModel(roProductModel);
                device.setProductId(productId);
                device.setAndroidVersion(androidVersion);
                device.setCpuHardware(cpuHardware);
                device.setRoProductName(roProductName);
                device.setRoBuildFingerprint(roBuildFingerprint);
                devices.add(device);
            }
        } catch (JSchException | ClassNotFoundException | PropertyVetoException | IOException ex) {
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
    
    
     public List<AndroidDevice> queryByVidAndModelForShuameMobile(String aVid,String pBrand, String model, int limit) throws SQLException {
        List<AndroidDevice> devices = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = Pool.getStatConnection();
            String sql = SqlUtils.createMonthlyQuery(DEVICE_DETAIL_OF_SHUAME_MOBILE_SQL, "log_m_device_init");
            statement = connection.prepareStatement(sql);
            statement.setString(1, model);
            statement.setString(2, aVid);
            statement.setString(3, pBrand);
            statement.setInt(4, limit);
            statement.setQueryTimeout(Constants.TIMEOUT_SHORT);
            rs = statement.executeQuery();
            while (rs.next()) {
                AndroidDevice device = new AndroidDevice();
                String macAddress = rs.getString("mac_address");
                String vid = rs.getString("vid");
                String pid = rs.getString("pid");
                String prot = rs.getString("prot");
                String sn = rs.getString("sn");
                String productId = rs.getString("product_id");
                String toProductDevice = rs.getString("ro_product_device");
                String roProductModel = rs.getString("ro_product_model");
                String brand = rs.getString("ro_product_brand");
                String roProductBoard = rs.getString("ro_product_board");
                String roProductManufacturer = rs.getString("ro_product_manufacturer");
                String roHardware = rs.getString("ro_hardware");
                String customProps = rs.getString("custom_props");
                String createdAt = rs.getString("created_at");
                String returnProductId = rs.getString("return_product_id");
                String identified = rs.getString("identified");
                String androidVersion = rs.getString("android_version");
                String cpuHardware = rs.getString("cpu_hardware");
                String roProductName = rs.getString("ro_product_name");
                String roBuildFingerprint = rs.getString("ro_build_fingerprint");

                device.setMacAddress(macAddress);
                device.setPid(pid);
                device.setProt(prot);
                device.setSn(sn);
                device.setRoProductDevice(toProductDevice);
                device.setRoProductBoard(roProductBoard);
                device.setRoProductManufacturer(roProductManufacturer);
                device.setRoHardware(roHardware);
                device.setCustomProps(customProps);
                device.setCreatedAt(createdAt);
                device.setReturnProductId(returnProductId);
                device.setIdentified(identified);
                device.setVid(vid);
                device.setRoProductBrand(brand);
                device.setRoProductModel(roProductModel);
                device.setProductId(productId);
                device.setAndroidVersion(androidVersion);
                device.setCpuHardware(cpuHardware);
                device.setRoProductName(roProductName);
                device.setRoBuildFingerprint(roBuildFingerprint);
                devices.add(device);
            }
        } catch (JSchException | ClassNotFoundException | PropertyVetoException | IOException ex) {
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

    public List<AndroidDevice> queryByMacAddress(String macAddr, int limit) throws SQLException {
        List<AndroidDevice> devices = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = Pool.getStatConnection();
            String sql = SqlUtils.createMonthlyQuery(QUERY_BY_MAC_ADDRESS_SQL, "log_device_init_full");
            statement = connection.prepareStatement(sql);
            statement.setString(1, macAddr);
            statement.setInt(2, limit);
            statement.setQueryTimeout(Constants.TIMEOUT_SHORT);
            rs = statement.executeQuery();
            while (rs.next()) {
                AndroidDevice device = new AndroidDevice();
                String macAddress = rs.getString("mac_address");
                String vid = rs.getString("vid");
                String pid = rs.getString("pid");
                String prot = rs.getString("prot");
                String sn = rs.getString("sn");
                String adbDevice = rs.getString("adb_device");
                String productId = rs.getString("product_id");
                String toProductDevice = rs.getString("ro_product_device");
                String roProductModel = rs.getString("ro_product_model");
                String brand = rs.getString("ro_product_brand");
                String roProductBoard = rs.getString("ro_product_board");
                String roProductManufacturer = rs.getString("ro_product_manufacturer");
                String roHardware = rs.getString("ro_hardware");
                String roBuildDisplayId = rs.getString("ro_build_display_id");
                String customProps = rs.getString("custom_props");
                String createdAt = rs.getString("created_at");
                String returnProductId = rs.getString("return_product_id");
                String identified = rs.getString("identified");
                String androidVersion = rs.getString("android_version");
                String cpuHardware = rs.getString("cpu_hardware");
                //Add more properties.
                String resolution = rs.getString("resolution");
                String partition = rs.getString("partitions");

                device.setMacAddress(macAddress);
                device.setPid(pid);
                device.setProt(prot);
                device.setSn(sn);
                device.setAdbDevice(adbDevice);
                device.setRoProductDevice(toProductDevice);
                device.setRoProductBoard(roProductBoard);
                device.setRoProductManufacturer(roProductManufacturer);
                device.setRoHardware(roHardware);
                device.setRoBuildDisplayId(roBuildDisplayId);
                device.setCustomProps(customProps);
                device.setCreatedAt(createdAt);
                device.setReturnProductId(returnProductId);
                device.setIdentified(identified);
                device.setVid(vid);
                device.setRoProductBrand(brand);
                device.setRoProductModel(roProductModel);
                device.setProductId(productId);
                device.setAndroidVersion(androidVersion);
                device.setCpuHardware(cpuHardware);
                //Add more properties
                device.setResolution(resolution);
                device.setPartitions(partition);
                devices.add(device);
            }
        } catch (JSchException | ClassNotFoundException | PropertyVetoException | IOException ex) {
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

    public List<AndroidDevice> queryAllDetailByVidAndModel(String aVid, String model, int limit) throws SQLException {
        List<AndroidDevice> devices = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = Pool.getStatConnection();
            String sql = SqlUtils.createMonthlyQuery(DEVICE_ALL_DETAIL_SQL, "log_device_init_full");
            statement = connection.prepareStatement(sql);
            statement.setString(1, model);
            statement.setString(2, aVid);
            statement.setInt(3, limit);
            statement.setQueryTimeout(Constants.TIMEOUT_SHORT);
            rs = statement.executeQuery();
            while (rs.next()) {
                AndroidDevice device = new AndroidDevice();
                String macAddress = rs.getString("mac_address");
                String vid = rs.getString("vid");
                String pid = rs.getString("pid");
                String prot = rs.getString("prot");
                String sn = rs.getString("sn");
                String adbDevice = rs.getString("adb_device");
                String productId = rs.getString("product_id");
                String toProductDevice = rs.getString("ro_product_device");
                String roProductModel = rs.getString("ro_product_model");
                String brand = rs.getString("ro_product_brand");
                String roProductBoard = rs.getString("ro_product_board");
                String roProductManufacturer = rs.getString("ro_product_manufacturer");
                String roHardware = rs.getString("ro_hardware");
                String roBuildDisplayId = rs.getString("ro_build_display_id");
                String customProps = rs.getString("custom_props");
                String createdAt = rs.getString("created_at");
                String returnProductId = rs.getString("return_product_id");
                String identified = rs.getString("identified");
                String androidVersion = rs.getString("android_version");
                String cpuHardware = rs.getString("cpu_hardware");
                //Add more properties.
                String resolution = rs.getString("resolution");
                String partition = rs.getString("partitions");

                device.setMacAddress(macAddress);
                device.setPid(pid);
                device.setProt(prot);
                device.setSn(sn);
                device.setAdbDevice(adbDevice);
                device.setRoProductDevice(toProductDevice);
                device.setRoProductBoard(roProductBoard);
                device.setRoProductManufacturer(roProductManufacturer);
                device.setRoHardware(roHardware);
                device.setRoBuildDisplayId(roBuildDisplayId);
                device.setCustomProps(customProps);
                device.setCreatedAt(createdAt);
                device.setReturnProductId(returnProductId);
                device.setIdentified(identified);
                device.setVid(vid);
                device.setRoProductBrand(brand);
                device.setRoProductModel(roProductModel);
                device.setProductId(productId);
                device.setAndroidVersion(androidVersion);
                device.setCpuHardware(cpuHardware);
                //Add more properties
                device.setResolution(resolution);
                device.setPartitions(partition);
                devices.add(device);
            }
        } catch (JSchException | ClassNotFoundException | PropertyVetoException | IOException ex) {
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

    public List<AndroidDeviceRanking> queryByBrand(String brand) throws SQLException {
        List<AndroidDeviceRanking> devices = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = Pool.getStatConnection();
            String sql=SqlUtils.createMonthlyQuery(QUERY_BY_BRAND, "log_device_init");
            statement = connection.prepareStatement(sql);
            statement.setString(1, brand);
            statement.setQueryTimeout(Constants.TIMEOUT_LONG);
            rs = statement.executeQuery();
            while (rs.next()) {
                AndroidDeviceRanking device = new AndroidDeviceRanking();
                String vid = rs.getString("vid");
                String roProductModel = rs.getString("ro_product_model");
                int count = rs.getInt("count");

                device.setVid(vid);
                device.setRoProductModel(roProductModel);
                device.setCount(count);
                devices.add(device);
            }
        } catch (JSchException | ClassNotFoundException | PropertyVetoException | IOException ex) {
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

    public String getMacAddressByQQ(String qq) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String macAddress = null;
        try {
            connection = Pool.getShuameConnection();
            statement = connection.prepareStatement(GET_MAC_ADDRESS_BY_QQ);
            statement.setString(1, qq);
            statement.setQueryTimeout(Constants.TIMEOUT_LONG);
            rs = statement.executeQuery();
            if (rs.next()) {
                macAddress = rs.getString("mac_address");
            }
        } catch (JSchException | ClassNotFoundException | PropertyVetoException | IOException ex) {
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
        return macAddress;
    }
    
    @Deprecated
     public List<AndroidDeviceRanking> listModes() throws SQLException {
        List<AndroidDeviceRanking> devices = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = Pool.getStatConnection();
            String sql=SqlUtils.createMonthlyQuery(MODEL_AND_PRODUCT_ID_ANALYTICS_SQL, "log_device_init");
            statement = connection.prepareStatement(sql);
            statement.setQueryTimeout(Constants.TIMEOUT_LONG);
            rs = statement.executeQuery();
            while (rs.next()) {
                AndroidDeviceRanking device = new AndroidDeviceRanking();
                String productId = rs.getString("return_product_id");
                String roProductModel = rs.getString("ro_product_model");
                int count = rs.getInt("count");

                device.setProductId(productId);
                device.setRoProductModel(roProductModel);
                device.setCount(count);
                devices.add(device);
            }
        } catch (JSchException | ClassNotFoundException | PropertyVetoException | IOException ex) {
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
