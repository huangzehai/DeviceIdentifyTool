/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.filter;

import com.u2apple.tool.filter.ExcludeFilter;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adam
 */
public class ExcludeFilterTest {

    public ExcludeFilterTest() {
    }

    @Test
    public void testFilter() {
        AndroidDeviceRanking device = new AndroidDeviceRanking();
        device.setRoProductBrand("Samsung");
        device.setRoProductModel("3G");
        device.setVid("04E8");
        List<AndroidDeviceRanking> devices = new ArrayList<>();
        devices.add(device);
        ExcludeFilter filter = new ExcludeFilter();
        List<AndroidDeviceRanking> filteredDevices = filter.filter(devices);
        System.out.println(filteredDevices.size());
    }

}
