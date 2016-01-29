/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.ui.worker;

import com.u2apple.tool.filter.DeviceFilter;
import com.u2apple.tool.filter.QueryFilter;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author Adam
 */
public class QueryFilterWorker extends AbstractDeviceRankingWorker {

    List<AndroidDeviceRanking> androidDevices;

    private final String query;

    public QueryFilterWorker(List<AndroidDeviceRanking> androidDevices, JTable deviceTable, String query) {
        super(deviceTable);
        this.androidDevices = androidDevices;
        this.query = query;
    }

    @Override
    protected List<AndroidDeviceRanking> doInBackground() throws Exception {
        DeviceFilter filter = new QueryFilter(this.query);
        return filter.filter(androidDevices);
    }

}
