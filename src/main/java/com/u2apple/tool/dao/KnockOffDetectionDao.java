package com.u2apple.tool.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.JSchException;
import com.u2apple.tool.constant.Constants;
import com.u2apple.tool.persistence.Pool;
import com.u2apple.tool.model.ResolutionRanking;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.PreparedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnockOffDetectionDao {

    final Logger logger = LoggerFactory.getLogger(KnockOffDetectionDao.class);
    private static final String RESOLUTION_SQL = "select resolution, count(*)  count from (select resolution from api_device_init_log_full_140730 where product_id= ? order by id desc limit 100) t group by resolution order by count desc";

    public List<ResolutionRanking> getResolutionsByProductId(String productId) {
        List<ResolutionRanking> resolutions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = Pool.getShuameConnection();
            connection = Pool.getShuameConnection();
            statement = connection.prepareStatement(RESOLUTION_SQL);
            statement.setString(1, productId);
            statement.setQueryTimeout(Constants.TIMEOUT_LONG);
            rs = statement.executeQuery();
            while (rs.next()) {
                String resolution = rs.getString("resolution");
                int count = rs.getInt("count");
                resolutions.add(new ResolutionRanking(resolution, count));
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
        return resolutions;
    }

}
