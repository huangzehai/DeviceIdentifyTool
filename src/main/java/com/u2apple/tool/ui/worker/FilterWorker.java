/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.ui.worker;

import com.u2apple.tool.filter.DevicePatternFilter;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author Adam
 */
public class FilterWorker extends AbstractDeviceRankingWorker {

    List<AndroidDeviceRanking> androidDevices;
    private boolean isOthers;

    public FilterWorker(List<AndroidDeviceRanking> androidDevices, JTable deviceTable) {
        super(deviceTable);
        this.androidDevices = androidDevices;
    }
    
     public FilterWorker(List<AndroidDeviceRanking> androidDevices, JTable deviceTable,boolean isOthers) {
        super(deviceTable);
        this.androidDevices = androidDevices;
        this.isOthers=isOthers;
    }

    @Override
    protected List<AndroidDeviceRanking> doInBackground() throws Exception {
        DevicePatternFilter patternFilter = new DevicePatternFilter(this.isOthers);
//        Filter existenceFilter = new DeviceExistenceFilter();
        return patternFilter.filter(androidDevices);
    }

}
