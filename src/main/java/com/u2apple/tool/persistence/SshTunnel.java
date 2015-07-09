/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.persistence;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.u2apple.tool.constant.Constants;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Adam
 */
public class SshTunnel {

    private static Session statSession;

    private static Session apiSession;

    private static Session rootSession;

    public static void doStatSshTunnel() throws JSchException, IOException {
        if (statSession == null) {
            Properties dataSource = new Properties();
            dataSource.load(MyBatisHelper.class.getResourceAsStream(Constants.DATA_SOURCE_CONF));
            String strSshUser = dataSource.getProperty("sshUser"); // SSH loging username
            String strSshPassword = dataSource.getProperty("sshPassword");// SSH login password
            String strSshHost = dataSource.getProperty("sshHost");// hostname or ip or SSH server
            int nSshPort = Integer.parseInt(dataSource.getProperty("sshPort")); // remote SSH host port number
            String strRemoteHost = dataSource.getProperty("dbHost"); // hostname or ip of your database server
            int nLocalPort = Integer.parseInt(dataSource.getProperty("statLocalPort")); // local port number use to bind SSH tunnel
            int nRemotePort = Integer.parseInt(dataSource.getProperty("statDbPort")); // remote port number of your database
            doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);
        }
    }

    public static void doApiSshTunnel() throws JSchException, IOException {
        if (statSession == null) {
            Properties dataSource = new Properties();
            dataSource.load(MyBatisHelper.class.getResourceAsStream(Constants.DATA_SOURCE_CONF));
            String strSshUser = dataSource.getProperty("sshUser"); // SSH loging username
            String strSshPassword = dataSource.getProperty("sshPassword");// SSH login password
            String strSshHost = dataSource.getProperty("sshHost");// hostname or ip or SSH server
            int nSshPort = Integer.parseInt(dataSource.getProperty("sshPort")); // remote SSH host port number
            String strRemoteHost = dataSource.getProperty("dbHost"); // hostname or ip of your database server
            //The difference.
            int nLocalPort = Integer.parseInt(dataSource.getProperty("localPort")); // local port number use to bind SSH tunnel
            int nRemotePort = Integer.parseInt(dataSource.getProperty("dbPort")); // remote port number of your database
            doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);
        }
    }

    private static void doSshTunnel(String strSshUser, String strSshPassword, String strSshHost, int nSshPort,
            String strRemoteHost, int nLocalPort, int nRemotePort) throws JSchException {
        final JSch jsch = new JSch();
        statSession = jsch.getSession(strSshUser, strSshHost, nSshPort);
        statSession.setPassword(strSshPassword);
        final Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        statSession.setConfig(config);
        statSession.connect();
        statSession.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
    }

    /**
     * Close SSH tunnel when appication is shutdown.
     */
    public static void close() {
        //Close stat SSH session.
        if (statSession != null) {
            statSession.disconnect();
            statSession = null;
        }

        if (apiSession != null) {
            apiSession.disconnect();
            apiSession = null;
        }

        if (rootSession != null) {
            rootSession.disconnect();
            rootSession = null;
        }
    }
}
