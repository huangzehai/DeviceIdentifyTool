/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.persistence;

import com.jcraft.jsch.JSchException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author Adam
 */
public final class MyBatisHelper {

    private static SqlSessionFactory statSqlSessionFactory;
     private static SqlSessionFactory rootSqlSessionFactory;

    private MyBatisHelper() {

    }

    public static SqlSessionFactory getStatSqlSessionFactory() throws IOException, JSchException {
        if (statSqlSessionFactory == null) {
            createStatSqlSessionFactory();
        }
        return statSqlSessionFactory;
    }

    private static void createStatSqlSessionFactory() throws IOException, JSchException {
        SshTunnel.doStatSshTunnel();
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        statSqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }
    
       public static SqlSessionFactory getRootSqlSessionFactory() throws IOException, JSchException {
        if (rootSqlSessionFactory == null) {
            createRootSqlSessionFactory();
        }
        return rootSqlSessionFactory;
    }

    private static void createRootSqlSessionFactory() throws IOException, JSchException {
        SshTunnel.doRootSshTunnel();
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        rootSqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream,"root");
    }
    
}
