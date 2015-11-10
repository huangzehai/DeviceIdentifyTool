package com.u2apple.tool.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.u2apple.tool.constant.Constants;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public final class Pool {

    private static Session session;
    /**
     * Root Spirit datasource.
     */
    private static DataSource rootDataSource;

    private static DataSource shuameDataSource;

    private static DataSource statDataSource;

    private static DataSource testDataSource;

    private static DataSource testStatDataSource;
    
    public static void close() {
        //Close SSH session.
        if (session != null) {
            session.disconnect();
        }

        //close root data source.
        if (rootDataSource != null) {
            shutdownDataSource(rootDataSource);
            rootDataSource = null;
        }

        if (shuameDataSource != null) {
            shutdownDataSource(shuameDataSource);
            shuameDataSource = null;
        }

        if (statDataSource != null) {
            shutdownDataSource(statDataSource);
            statDataSource = null;
        }

        if (testDataSource != null) {
            shutdownDataSource(testDataSource);
            testDataSource = null;
        }

        if (testStatDataSource != null) {
            shutdownDataSource(testStatDataSource);
            testStatDataSource = null;
        }
    }

    public static void shutdownDataSource(DataSource ds) {
        if (ds != null) {
            BasicDataSource bds = (BasicDataSource) ds;
            try {
                bds.close();
            } catch (SQLException ex) {
                Logger.getLogger(Pool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static Connection getShuameConnection() throws JSchException, SQLException, ClassNotFoundException, IOException, PropertyVetoException {
        if (shuameDataSource == null) {
            shuameDataSource = setupDataSource();
        }
        return shuameDataSource.getConnection();
    }

    public static DataSource setupDataSource() throws JSchException, SQLException, ClassNotFoundException, IOException, PropertyVetoException {
        Properties dataSource = new Properties();
        dataSource.load(Pool.class.getResourceAsStream(Constants.DATA_SOURCE_CONF));
//        String strSshUser = dataSource.getProperty("sshUser"); // SSH loging username
//        String strSshPassword = dataSource.getProperty("sshPassword");// SSH login password
//        String strSshHost = dataSource.getProperty("sshHost");// hostname or ip or SSH server
//        int nSshPort = Integer.parseInt(dataSource.getProperty("sshPort")); // remote SSH host port number
//        String strRemoteHost = dataSource.getProperty("dbHost"); // hostname or ip of your database server
        int nLocalPort = Integer.parseInt(dataSource.getProperty("localPort")); // local port number use to bind SSH tunnel
//        int nRemotePort = Integer.parseInt(dataSource.getProperty("dbPort")); // remote port number of your database
        String strDbUser = dataSource.getProperty("dbUser");// database loging username
        String strDbPassword = dataSource.getProperty("dbPassword"); // database login password
//        Pool
//                .doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);

        SshTunnel.doApiSshTunnel();
        //Pooling.
        String dbUrl = dataSource.getProperty("dbUrl") + nLocalPort + "/" + dataSource.getProperty("dbName");
        String driver = dataSource.getProperty("dbDriver");
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUsername(strDbUser);
        ds.setPassword(strDbPassword);
        ds.setUrl(dbUrl);
        return ds;
    }

    private static DataSource initStatDataSource() throws JSchException, SQLException, ClassNotFoundException, IOException, PropertyVetoException {
        Properties dataSource = new Properties();
        dataSource.load(Pool.class.getResourceAsStream(Constants.DATA_SOURCE_CONF));
//        String strSshUser = dataSource.getProperty("sshUser"); // SSH loging username
//        String strSshPassword = dataSource.getProperty("sshPassword");// SSH login password
//        String strSshHost = dataSource.getProperty("sshHost");// hostname or ip or SSH server
//        int nSshPort = Integer.parseInt(dataSource.getProperty("sshPort")); // remote SSH host port number
//        String strRemoteHost = dataSource.getProperty("dbHost"); // hostname or ip of your database server
        int nLocalPort = Integer.parseInt(dataSource.getProperty("statLocalPort")); // local port number use to bind SSH tunnel
//        int nRemotePort = Integer.parseInt(dataSource.getProperty("statDbPort")); // remote port number of your database
        String strDbUser = dataSource.getProperty("dbUser");// database loging username
        String strDbPassword = dataSource.getProperty("statDbPassword"); // database login password
//        Pool
//                .doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);
        SshTunnel.doStatSshTunnel();
        //Pooling.
        String dbUrl = dataSource.getProperty("dbUrl") + nLocalPort + "/" + dataSource.getProperty("statDbName");
        String driver = dataSource.getProperty("dbDriver");
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUsername(strDbUser);
        ds.setPassword(strDbPassword);
        ds.setUrl(dbUrl);
        ds.setValidationQuery("select 1");
        ds.setRemoveAbandonedOnMaintenance(true);
        ds.setRemoveAbandonedOnBorrow(true);
        ds.setTestOnBorrow(true);
        return ds;
    }

    private static DataSource initRootDataSource() throws JSchException, SQLException, ClassNotFoundException, IOException, PropertyVetoException {
        Properties dataSource = new Properties();
        dataSource.load(Pool.class.getResourceAsStream(Constants.DATA_SOURCE_CONF));
        int nLocalPort = Integer.parseInt(dataSource.getProperty("rootLocalPort")); // local port number use to bind SSH tunnel
        String strDbUser = dataSource.getProperty("rootDbUser");// database loging username
        String strDbPassword = dataSource.getProperty("rootDbPassword"); // database login password
        SshTunnel.doRootSshTunnel();
        //Pooling.
        String dbUrl = dataSource.getProperty("dbUrl") + nLocalPort + "/" + dataSource.getProperty("rootDbName");
        BasicDataSource ds = new BasicDataSource();
        String driver = dataSource.getProperty("dbDriver");
        ds.setDriverClassName(driver);
        ds.setUsername(strDbUser);
        ds.setPassword(strDbPassword);
        ds.setUrl(dbUrl);
        return ds;
    }

    public static DataSource initTestDataSource() throws SQLException, ClassNotFoundException, IOException, PropertyVetoException {
        Properties dataSource = new Properties();
        dataSource.load(Pool.class.getResourceAsStream(Constants.DATA_SOURCE_CONF));
        Class.forName(dataSource.getProperty("testDbDriver"));
        String port = dataSource.getProperty("testDbPort");
        String strDbUser = dataSource.getProperty("testDbUser");
        String strDbPassword = dataSource.getProperty("testDbPassword");
        String dbUrl = dataSource.getProperty("testDbUrl") + port + "/" + dataSource.getProperty("testDbName");

        //Pooling.
        String driver = dataSource.getProperty("dbDriver");
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUsername(strDbUser);
        ds.setPassword(strDbPassword);
        ds.setUrl(dbUrl);
        return ds;
    }

    public static DataSource initTestStatDataSource() throws SQLException, ClassNotFoundException, IOException, PropertyVetoException {
        Properties dataSource = new Properties();
        dataSource.load(Pool.class.getResourceAsStream(Constants.DATA_SOURCE_CONF));
        Class.forName(dataSource.getProperty("testDbDriver"));
        String port = dataSource.getProperty("testDbPort");
        String strDbUser = dataSource.getProperty("testDbUser");
        String strDbPassword = dataSource.getProperty("testDbPassword");
        String dbUrl = dataSource.getProperty("testDbUrl") + port + "/" + dataSource.getProperty("testStatDbName");

        //Pooling.
        String driver = dataSource.getProperty("dbDriver");
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUsername(strDbUser);
        ds.setPassword(strDbPassword);
        ds.setUrl(dbUrl);
        return ds;
    }

    public static Connection getTestConnection() throws SQLException, ClassNotFoundException, IOException, PropertyVetoException {
        if (testDataSource == null) {
            testDataSource = initTestDataSource();
        }
        return testDataSource.getConnection();
    }

    public static Connection getTestStatConnection() throws SQLException, ClassNotFoundException, IOException, PropertyVetoException {
        if (testStatDataSource == null) {
            testStatDataSource = initTestStatDataSource();
        }
        return testStatDataSource.getConnection();
    }

    public static Connection getStatConnection() throws SQLException, ClassNotFoundException, IOException, JSchException, PropertyVetoException {
        if (statDataSource == null) {
            statDataSource = initStatDataSource();
        }
        return statDataSource.getConnection();
    }

    public static Connection getRootConnection() throws SQLException, ClassNotFoundException, IOException, JSchException, PropertyVetoException {
        if (rootDataSource == null) {
            rootDataSource = initRootDataSource();
        }
        return rootDataSource.getConnection();
    }
}
