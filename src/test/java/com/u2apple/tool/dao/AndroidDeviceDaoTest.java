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
        SshTunnel.close();
    }

    @Test
    public void testGetCount() throws IOException, JSchException {

        int count = dao.getCount();
        System.out.println(count);

    }

    @Test
    public void testGetUnidentifiedDevices() throws IOException, JSchException {
        int days = 1;
        List<AndroidDeviceRanking> androidDevices = dao.getUnidentifiedDevices(days);
        androidDevices.stream().forEach((androidDevice) -> {
            System.out.println(androidDevice);
        });
    }

    @Test
    public void testGetUnidentifiedDevicesOfRootSpirit() throws IOException, JSchException {
        int days = 1;
        List<AndroidDeviceRanking> androidDevices = dao.getUnidentifiedDevicesOfRootSpirit(days);
        androidDevices.stream().forEach((androidDevice) -> {
            System.out.println(androidDevice);
        });
    }

    @Test
    public void testGetRootDeviceByVidAndModel() {
        List<AndroidDevice> devices = dao.getRootDeviceByVidAndModel("22D9", "A31", 10);
        if (devices != null) {
            devices.stream().forEach(System.out::println);
        }
    }

}
