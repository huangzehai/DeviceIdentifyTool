/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.ui.worker;

import com.u2apple.tool.model.AndroidDeviceRanking;
import com.u2apple.tool.ui.table.DeviceTableModel;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;

/**
 *
 * @author Adam
 */
public abstract class AbstractDeviceRankingWorker extends SwingWorker<List<AndroidDeviceRanking>, Void> {

    private final JTable deviceTable;

    public AbstractDeviceRankingWorker(JTable deviceTable) {
        this.deviceTable = deviceTable;
    }

    @Override
    protected void done() {
        try {
            //get the data fetched above, in doInBackground()
            List<AndroidDeviceRanking> rankingList = get();
            if (rankingList != null ) {
                DeviceTableModel tableModel = (DeviceTableModel) deviceTable.getModel();
                tableModel.setAndroidDevices(rankingList);
                tableModel.fireTableDataChanged();
            }
        } catch (InterruptedException | ExecutionException ex) {
            JOptionPane.showMessageDialog(deviceTable.getParent().getParent().getParent(), ex.getMessage());
        }
    }
}
