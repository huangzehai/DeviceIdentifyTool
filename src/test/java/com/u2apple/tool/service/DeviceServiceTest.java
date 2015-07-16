/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.service;

import java.util.List;
import org.junit.Test;

/**
 *
 * @author Adam
 */
public class DeviceServiceTest {

    private final DeviceService deviceService = new DeviceServiceImpl();

    public DeviceServiceTest() {
    }

    @Test
    public void testListFullProductId() {
        List<String> productIds = deviceService.listFullProductId();
        for (String productId : productIds) {
            System.out.println(productId);
        }
    }

}
