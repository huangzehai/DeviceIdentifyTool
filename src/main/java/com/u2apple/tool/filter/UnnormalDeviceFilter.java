/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.filter;

import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

/**
 * 返回brand和model相同的设备列表.
 * @author Adam
 */
public class UnnormalDeviceFilter implements DeviceFilter{

    @Override
    public List<AndroidDeviceRanking> filter(List<AndroidDeviceRanking> androidDevices) {
        return androidDevices.stream().filter(device->StringUtils.equalsIgnoreCase(device.getRoProductBrand(), device.getRoProductModel())).collect(Collectors.toList());
    }
    
}
