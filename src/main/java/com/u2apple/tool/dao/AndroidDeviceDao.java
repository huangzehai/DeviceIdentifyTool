/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import com.jcraft.jsch.JSchException;
import com.u2apple.tool.model.AndroidDevice;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Adam
 */
public interface AndroidDeviceDao {

    List<AndroidDeviceRanking> getUnidentifiedDevices(int days) throws IOException, JSchException;
    
    List<AndroidDeviceRanking> getUnidentifiedDevicesOfShuameMobile(int days) throws IOException, JSchException;

    List<AndroidDeviceRanking> getUnidentifiedDevicesOfRootSpirit(int days) throws IOException, JSchException;

    List<AndroidDevice> getRootDeviceByVidAndModel(String vid,String model,int limit)throws IOException, JSchException;
    
    List<AndroidDeviceRanking> listModelWithRanking(int days)throws IOException, JSchException;
    
    List<AndroidDeviceRanking> listDevicesOfShuameMobile(int days)throws IOException, JSchException;
    
    List<AndroidDeviceRanking> listCpu(int days)throws IOException, JSchException;
}
