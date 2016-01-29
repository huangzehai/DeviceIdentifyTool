package com.u2apple.tool.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.JSchException;
import com.u2apple.tool.constant.Constants;
import com.u2apple.tool.persistence.Pool;
import com.u2apple.tool.model.AndroidDeviceRanking;
import com.u2apple.tool.util.SqlUtils;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

public class NonRecognitionRankingDao {

    final org.slf4j.Logger logger = LoggerFactory.getLogger(NonRecognitionRankingDao.class);
   

    private static String createRootQuery(int days) {
        StringBuilder queryBuilder = new StringBuilder("select vid, ro_product_brand,ro_product_model ,count(*) as count from ");
        queryBuilder.append(SqlUtils.getMonthlyTable("log_root_solution"));
        queryBuilder.append(" where created_at > '").append(SqlUtils.dateSub(days)).append("'");
        queryBuilder.append(" and ( product_id is  null or product_id ='' or product_id='query-empty' ) and ro_product_model is not null and ro_product_model !='' and ro_product_brand is not null and ro_product_brand !='' group by vid,ro_product_brand,ro_product_model order by count desc limit 1000");
        return queryBuilder.toString();
    }
    private String createWhiteListQuery(int days) {
        InputStream brandInputStream = NonRecognitionRankingDao.class.getResourceAsStream(Constants.BRAND_CONF);
        Properties brandProp = new Properties();
        try {
            brandProp.load(brandInputStream);
        } catch (IOException ex) {
            Logger.getLogger(NonRecognitionRankingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        Set<Object> brands = brandProp.keySet();
        StringBuilder queryBuilder = new StringBuilder("select vid, ro_product_model, ro_product_brand, count(*) as count from ");
        queryBuilder.append(SqlUtils.getMonthlyTable("log_device_init"));
        queryBuilder.append(" where created_at > '").append(SqlUtils.dateSub(days)).append("'");
        queryBuilder.append("and identified=0 and ro_product_model is not null and ro_product_model <> ''  and ro_product_model <> '　　' and  ro_product_brand  in (");
        int index = 0;
        for (Object brand : brands) {
            queryBuilder.append("'");
            queryBuilder.append(brand);
            queryBuilder.append("'");
            if (index != brands.size() - 1) {
                queryBuilder.append(",");
            }
            index++;
        }
        queryBuilder.append(")  group by vid, ro_product_model,ro_product_brand order by count desc limit 1000");
        return queryBuilder.toString();
    }

    @Deprecated
    public List<AndroidDeviceRanking> getWhiteListNonRecognizedDevicesRanking(int days) throws SQLException {
        List<AndroidDeviceRanking> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = Pool.getStatConnection();
            String sql = createWhiteListQuery(days);
            statement = connection.prepareStatement(sql);
//        statement.setInt(1, days);
            statement.setQueryTimeout(Constants.TIMEOUT_LONG);
            rs = statement.executeQuery();
            while (rs.next()) {
                String vid = rs.getString("vid");
                String roProductModel = rs.getString("ro_product_model");
                String brand = rs.getString("ro_product_brand");
                int count = rs.getInt("count");
                AndroidDeviceRanking androidDeviceRanking = new AndroidDeviceRanking(vid, roProductModel, brand, count);
                list.add(androidDeviceRanking);
            }
        } catch ( JSchException | ClassNotFoundException | PropertyVetoException | IOException ex) {
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
        return list;
    }
}
