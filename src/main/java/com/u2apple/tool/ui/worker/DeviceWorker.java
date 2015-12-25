/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.ui.worker;

import com.u2apple.tool.Source;
import com.u2apple.tool.dao.AndroidDeviceDao;
import com.u2apple.tool.dao.AndroidDeviceDaoImpl;
import com.u2apple.tool.dao.DeviceDao;
import com.u2apple.tool.model.AndroidDevice;
import com.u2apple.tool.ui.table.DeviceDetailTableModel;
import com.u2apple.tool.util.QueryPattern;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Adam
 */
public class DeviceWorker extends SwingWorker<List<AndroidDevice>, Void> {

    private String vid;
    private String model;
    private String brand;
    private final int limit;
    private boolean isAll;
    private String macAddress;
    private String qq;
    private Source source;
    private final JTable deviceDetailTable;

    public DeviceWorker(String vid,String brand, String model, int limit, boolean isAll, Source source, JTable deviceDetailTable) {
        this.vid = vid;
        this.brand = brand;
        this.model = model;
        this.limit = limit;
        this.isAll = isAll;
        this.source = source;
        this.deviceDetailTable = deviceDetailTable;
    }

    public DeviceWorker(String query, int limit, JTable deviceDetailTable) {
        this.limit = limit;
        if (QueryPattern.isQQ(query)) {
            this.qq = query;
        } else {
            this.macAddress = query;
        }
        this.deviceDetailTable = deviceDetailTable;
    }

    @Override
    protected List<AndroidDevice> doInBackground() throws Exception {
        List<AndroidDevice> androidDevices = null;
        DeviceDao dao = new DeviceDao();
        if (this.qq != null) {
            String macAddr = dao.getMacAddressByQQ(this.qq);
            if (StringUtils.isNotBlank(macAddr)) {
                androidDevices = dao.queryByMacAddress(macAddr.trim(), limit);
            } else {
                androidDevices = new ArrayList<>();
            }
        } else if (this.macAddress != null) {
            androidDevices = dao.queryByMacAddress(this.macAddress.trim(), limit);
        } else {
            if (Source.Shuame == this.source) {
                if (isAll) {
                    androidDevices = dao.queryAllDetailByVidAndModel(vid.trim(), model.trim(), limit);
                } else {
                    androidDevices = dao.queryByVidAndModel(vid.trim(),brand.trim(), model.trim(), limit);
                }
            } else if (Source.ShuameMobile == this.source) {
                androidDevices = dao.queryByVidAndModelForShuameMobile(vid.trim(),brand.trim(), model.trim(), limit);
            } else if (Source.RootSpirit == this.source) {
                AndroidDeviceDao androidDeviceDao = new AndroidDeviceDaoImpl();
                androidDevices = androidDeviceDao.getRootDeviceByVidAndModel(this.vid, this.model, this.limit);
            }

        }
        return androidDevices;
    }

    @Override
    protected void done() {
        try {
            //get the data fetched above, in doInBackground()
            List<AndroidDevice> androidDevices = get();
            if (androidDevices != null && !androidDevices.isEmpty()) {
                DeviceDetailTableModel tableModel = (DeviceDetailTableModel) deviceDetailTable.getModel();
                tableModel.setAndroidDevices(androidDevices);
                tableModel.fireTableDataChanged();
            }
        } catch (InterruptedException | ExecutionException ex) {
            JOptionPane.showMessageDialog(deviceDetailTable.getParent().getParent().getParent(), ex.getMessage());
        }
    }

}
