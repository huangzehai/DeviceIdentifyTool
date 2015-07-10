/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.ui.worker;

import com.u2apple.tool.filter.NewDeviceFilter;
import com.u2apple.tool.filter.Filter;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author Adam
 */
public class NewDeviceFilterWorker extends AbstractDeviceRankingWorker {

    List<AndroidDeviceRanking> androidDevices;

    public NewDeviceFilterWorker(List<AndroidDeviceRanking> androidDevices, JTable deviceTable) {
        super(deviceTable);
        this.androidDevices = androidDevices;
    }

    @Override
    protected List<AndroidDeviceRanking> doInBackground() throws Exception {
        try{
        Filter filter = new NewDeviceFilter();
         filter.filter(androidDevices);
        }catch(Exception e){
           e.printStackTrace();
        }
        return androidDevices;
    }

}
