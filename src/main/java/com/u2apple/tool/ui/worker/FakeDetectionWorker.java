/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.ui.worker;

import com.u2apple.tool.model.AndroidDeviceRanking;
import com.u2apple.tool.service.KnockOffDetectionService;
import com.u2apple.tool.service.KnockOffDetectionServiceImpl;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author Adam
 */
public class FakeDetectionWorker extends AbstractDeviceRankingWorker {

    public FakeDetectionWorker(JTable deviceTable) {
        super(deviceTable);
    }

    @Override
    protected List<AndroidDeviceRanking> doInBackground() throws Exception {
        KnockOffDetectionService service = new KnockOffDetectionServiceImpl();
        return service.analyticsByCpu();
    }

}
