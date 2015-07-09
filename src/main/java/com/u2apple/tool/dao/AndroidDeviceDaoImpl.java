/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import com.jcraft.jsch.JSchException;
import com.u2apple.tool.mappers.AndroidDeviceMapper;
import com.u2apple.tool.model.AndroidDeviceRanking;
import com.u2apple.tool.persistence.MyBatisHelper;
import java.io.IOException;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 *
 * @author Adam
 */
public class AndroidDeviceDaoImpl implements AndroidDeviceDao {

    private final SqlSessionFactory sqlSessionFactory;

    public AndroidDeviceDaoImpl() throws IOException, JSchException {
             this.sqlSessionFactory = MyBatisHelper.getSqlSessionFactory();
    }

    @Override
    public int getCount() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AndroidDeviceMapper mapper = sqlSession.getMapper(AndroidDeviceMapper.class);
        return mapper.selectCount();
    }

    @Override
    public List<AndroidDeviceRanking> getUnidentifiedDevices(int days) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AndroidDeviceMapper mapper = sqlSession.getMapper(AndroidDeviceMapper.class);
        return mapper.selectUnidentifiedDevices(days);
    }

}
