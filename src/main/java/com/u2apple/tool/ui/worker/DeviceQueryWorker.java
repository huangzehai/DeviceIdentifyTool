/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.ui.worker;

import com.u2apple.tool.dao.DeviceDao;
import com.u2apple.tool.model.AndroidDevice;
import com.u2apple.tool.model.AndroidDeviceRanking;
import com.u2apple.tool.util.AndroidDeviceUtils;
import java.util.List;
import javax.swing.JTable;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Adam
 */
public class DeviceQueryWorker extends AbstractDeviceRankingWorker {

    private final String query;

    /**
     *
     * @param query
     * @param deviceTable
     */
    public DeviceQueryWorker(String query, JTable deviceTable) {
        super(deviceTable);
        this.query = query;
    }

    @Override
    protected List<AndroidDeviceRanking> doInBackground() throws Exception {
        DeviceDao deviceDao = new DeviceDao();
        List<AndroidDevice> devices;
        if (query.contains("like")) {
            String model = StringUtils.substringAfter(query, "like");
            devices = deviceDao.queryLikeModel(model.trim());
        } else {
            devices = deviceDao.queryByModel(query.trim());
        }
        return AndroidDeviceUtils.parse(devices);
    }

}
