/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import com.jcraft.jsch.JSchException;
import com.u2apple.tool.model.AndroidDevice;
import com.u2apple.tool.persistence.SshTunnel;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Adam
 */
public class AndroidDeviceDaoTest {

    private AndroidDeviceDao dao;

    public AndroidDeviceDaoTest() {
    }

    @Before
    public void init() throws IOException, JSchException {
        dao = new AndroidDeviceDaoImpl();
    }

    @After
    public void destroy() {
//        System.out.println("destroy");
//        SshTunnel.close();
    }

    @Test
    public void testGetUnidentifiedDevices() throws IOException, JSchException {
        int days = 1;
        List<AndroidDeviceRanking> androidDevices = dao.getUnidentifiedDevices(days);
        Assert.assertNotNull(androidDevices);
        Assert.assertTrue(androidDevices.size() > 0);
    }

    @Test
    public void testListModelWithRanking() throws IOException, JSchException {
        int days = 1;
        List<AndroidDeviceRanking> androidDevices = dao.listModelWithRanking(days);
        Assert.assertNotNull(androidDevices);
        Assert.assertTrue(androidDevices.size() > 0);
    }

    @Test
    public void testListCpu() throws IOException, JSchException {
        int days = 1;
        List<AndroidDeviceRanking> androidDevices = dao.listCpu(days);
        Assert.assertNotNull(androidDevices);
        Assert.assertTrue(androidDevices.size() > 0);
    }

    @Test
    public void testGetUnidentifiedDevicesOfRootSpirit() throws IOException, JSchException {
        int days = 0;
        List<AndroidDeviceRanking> androidDevices = dao.getUnidentifiedDevicesOfRootSpirit(days);
        Assert.assertNotNull(androidDevices);
    }

    @Test
    public void testGetRootDeviceByVidAndModel() throws IOException, JSchException {
        List<AndroidDevice> androidDevices = dao.getRootDeviceByVidAndModel("04E8", "SM-G9200", 1);
        Assert.assertNotNull(androidDevices);
        Assert.assertTrue(androidDevices.size() > 0);
    }
}
