package com.u2apple.tool.util;

import com.u2apple.tool.model.Device;



public class DeviceFactory {
    public static Device createDevice(String productId, String brand, String product, String alias,int type) {
        Device device = new Device();
        device.setProductId(productId);
        device.setBrand(brand);
        device.setProduct(product);
        device.setAlias(alias);
       // device.setSupport("true");
        device.setType(type);
        return device;
    }
}
