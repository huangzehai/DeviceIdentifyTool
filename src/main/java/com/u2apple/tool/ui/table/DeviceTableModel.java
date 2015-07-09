/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.ui.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.u2apple.tool.model.AndroidDeviceRanking;

/**
 *
 * @author Adam
 */
public class DeviceTableModel extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = -2512160437754458640L;
    private final String[] columnNames = {"VID", "Model", "Brand", "Product ID", "Count"};
    private List<AndroidDeviceRanking> androidDevices;

    public DeviceTableModel(List<AndroidDeviceRanking> androidDevices) {
        this.androidDevices = androidDevices;
    }

    public List<AndroidDeviceRanking> getAndroidDevices() {
        return androidDevices;
    }

    public void setAndroidDevices(List<AndroidDeviceRanking> androidDevices) {
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
        AndroidDeviceRanking device = androidDevices.get(rowIndex);
        Object result = null;
        switch (columnIndex) {
            case 0:
                result = device.getVid();
                break;
            case 1:
                result = device.getRoProductModel();
                break;
            case 2:
                result = device.getRoProductBrand();
                break;
            case 3:
                result = device.getProductId();
                break;
            case 4:
                result = device.getCount();
                break;
        }
        return result;
    }
    
    public void removeRow(int row) {
        androidDevices.remove(row);
        fireTableRowsDeleted(row, row);
    }
}
