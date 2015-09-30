/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import com.jcraft.jsch.JSchException;
import com.u2apple.tool.mappers.AndroidDeviceMapper;
import com.u2apple.tool.mappers.RootDeviceMapper;
import com.u2apple.tool.model.AndroidDevice;
import com.u2apple.tool.model.AndroidDeviceRanking;
import com.u2apple.tool.persistence.MyBatisHelper;
import com.u2apple.tool.util.SqlUtils;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Adam
 */
public class AndroidDeviceDaoImpl implements AndroidDeviceDao {

    @Override
    public int getCount() throws IOException, JSchException {
        SqlSession sqlSession = MyBatisHelper.getStatSqlSessionFactory().openSession();
        AndroidDeviceMapper mapper = sqlSession.getMapper(AndroidDeviceMapper.class);
        return mapper.selectCount();
    }

    @Override
    public List<AndroidDeviceRanking> getUnidentifiedDevices(int days) throws IOException, JSchException {
        SqlSession sqlSession = MyBatisHelper.getStatSqlSessionFactory().openSession();
        AndroidDeviceMapper mapper = sqlSession.getMapper(AndroidDeviceMapper.class);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        return mapper.selectUnidentifiedDevices(SqlUtils.getMonthlyTable("log_device_init"), calendar.getTime());
    }

    @Override
    public List<AndroidDeviceRanking> getUnidentifiedDevicesOfRootSpirit(int days) throws IOException, JSchException {
        SqlSession sqlSession = MyBatisHelper.getRootSqlSessionFactory().openSession();
        RootDeviceMapper mapper = sqlSession.getMapper(RootDeviceMapper.class);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        return mapper.selectUnidentifiedDevices(SqlUtils.getMonthlyTable("log_root_solution"), calendar.getTime());
    }

    @Override
    public List<AndroidDevice> getRootDeviceByVidAndModel(String vid, String model, int limit) throws IOException, JSchException {
        SqlSession sqlSession = MyBatisHelper.getRootSqlSessionFactory().openSession();
        RootDeviceMapper mapper = sqlSession.getMapper(RootDeviceMapper.class);
        return mapper.getDeviceByVidAndModel(SqlUtils.getMonthlyTable("log_root_solution"), vid, model, limit);
    }

    @Override
    public List<AndroidDeviceRanking> listModelWithRanking(int days) throws IOException, JSchException {
        SqlSession sqlSession = MyBatisHelper.getStatSqlSessionFactory().openSession();
        AndroidDeviceMapper mapper = sqlSession.getMapper(AndroidDeviceMapper.class);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        return mapper.listModelWithRanking(SqlUtils.getMonthlyTable("log_device_init"), calendar.getTime());
    }

    @Override
    public List<AndroidDeviceRanking> listCpu(int days) throws IOException, JSchException {
        SqlSession sqlSession = MyBatisHelper.getStatSqlSessionFactory().openSession();
        AndroidDeviceMapper mapper = sqlSession.getMapper(AndroidDeviceMapper.class);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        return mapper.listCpu(SqlUtils.getMonthlyTable("log_device_init"), calendar.getTime());
    }

}
