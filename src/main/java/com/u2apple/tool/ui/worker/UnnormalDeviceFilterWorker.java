/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.ui.worker;

import com.u2apple.tool.filter.DeviceFilter;
import com.u2apple.tool.filter.UnnormalDeviceFilter;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author Adam
 */
public class UnnormalDeviceFilterWorker extends AbstractDeviceRankingWorker{

     List<AndroidDeviceRanking> androidDevices;

    public UnnormalDeviceFilterWorker(List<AndroidDeviceRanking> androidDevices, JTable deviceTable) {
        super(deviceTable);
        this.androidDevices = androidDevices;
    }
    @Override
    protected List<AndroidDeviceRanking> doInBackground() throws Exception {
        DeviceFilter filter = new UnnormalDeviceFilter();
        return filter.filter(androidDevices);
    }
}
