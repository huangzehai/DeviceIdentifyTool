/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.ui.table;

import com.u2apple.tool.model.AndroidDevice;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Adam
 */
public class DeviceDetailTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Mac", "VID", "PID", "PROT", "SN", "ADB", "Product ID", "device", "model", "brand", "board", "manufacturer", "hardware", "display id", "custom props", "android version", "cpu hardware", "created at", "return product id", "identified","resolution","partitions"};
    private List<AndroidDevice> androidDevices;

    public DeviceDetailTableModel(List<AndroidDevice> androidDevices) {
        this.androidDevices = androidDevices;
    }

    public List<AndroidDevice> getAndroidDevices() {
        return androidDevices;
    }

    public void setAndroidDevices(List<AndroidDevice> androidDevices) {
        this.androidDevices = androidDevices;
    }

    @Override
    public int getRowCount() {
        return this.androidDevices.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AndroidDevice device = androidDevices.get(rowIndex);
        Object value ;
        switch (columnIndex) {
            case 0:
                value = device.getMacAddress();
                break;
            case 1:
                value = device.getVid();
                break;
            case 2:
                value = device.getPid();
                break;
            case 3:
                value = device.getProt();
                break;
            case 4:
                value = device.getSn();
                break;
            case 5:
                value = device.getAdbDevice();
                break;
            case 6:
                value = device.getProductId();
                break;
            case 7:
                value = device.getRoProductDevice();
                break;
            case 8:
                value = device.getRoProductModel();
                break;
            case 9:
                value = device.getRoProductBrand();
                break;
            case 10:
                value = device.getRoProductBoard();
                break;
            case 11:
                value = device.getRoProductManufacturer();
                break;
            case 12:
                value = device.getRoHardware();
                break;
            case 13:
                value = device.getRoBuildDisplayId();
                break;
            case 14:
                value = device.getCustomProps();
                break;
            case 15:
                value = device.getAndroidVersion();
                break;
            case 16:
                value = device.getCpuHardware();
                break;
            case 17:
                value = device.getCreatedAt();
                break;
            case 18:
                value = device.getReturnProductId();
                break;
            case 19:
                value = device.getIdentified();
                break;
                case 20:
                value = device.getResolution();
                break;
            case 21:
                value = device.getPartitions();
                break;
            default:
                value=null;
        }
        return value;
    }
}
