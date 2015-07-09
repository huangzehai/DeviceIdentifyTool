/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.ui.worker;

import com.u2apple.tool.dao.NonRecognitionRankingDao;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author Adam
 */
public class RootRankingWorker extends AbstractDeviceRankingWorker {

    private final int days;

    public RootRankingWorker(int days, JTable deviceTable) {
        super(deviceTable);
        this.days = days;
    }

    @Override
    protected List<AndroidDeviceRanking> doInBackground() throws Exception {
        return new NonRecognitionRankingDao().getMobileRootSpiritDevices(days);
    }

}
