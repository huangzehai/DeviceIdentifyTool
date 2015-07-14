/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.filter;

import com.u2apple.tool.cache.DeviceCache;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adam
 */
public class CheckedDeviceFilter implements Filter {

    @Override
    public List<AndroidDeviceRanking> filter(List<AndroidDeviceRanking> androidDevices) {
        List<AndroidDeviceRanking> filteredDevices = new ArrayList<>();
        for (AndroidDeviceRanking device : androidDevices) {
            if (!DeviceCache.isChecked(device)) {
                filteredDevices.add(device);
            }
        }
        return filteredDevices;
    }

}
