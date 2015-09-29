/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.ui.worker;

import com.u2apple.tool.model.AndroidDeviceRanking;
import com.u2apple.tool.service.IdentifyAnalyticsService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JTable;

/**
 *
 * @author Adam
 */
public class ErrorDetectionWorker extends AbstractDeviceRankingWorker {

    public ErrorDetectionWorker(JTable deviceTable) {
        super(deviceTable);
    }

    @Override
    protected List<AndroidDeviceRanking> doInBackground() throws Exception {
        IdentifyAnalyticsService service = new IdentifyAnalyticsService();
        List<List<AndroidDeviceRanking>> deviceList = service.analytics();
        List<AndroidDeviceRanking> devices = new ArrayList<>();
        if (deviceList != null && !deviceList.isEmpty()) {
            devices = deviceList.stream().flatMap(l -> l.stream()).collect(Collectors.toList());
        }
        return devices;
    }

}
