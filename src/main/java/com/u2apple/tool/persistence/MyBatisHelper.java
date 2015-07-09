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

    private static SqlSessionFactory sqlSessionFactory;

    private MyBatisHelper() {

    }

    public static SqlSessionFactory getSqlSessionFactory() throws IOException, JSchException {
        if (sqlSessionFactory == null) {
            createSqlSessionFactory();
        }
        return sqlSessionFactory;
    }

    private static void createSqlSessionFactory() throws IOException, JSchException {
        SshTunnel.doStatSshTunnel();
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }
    
}
