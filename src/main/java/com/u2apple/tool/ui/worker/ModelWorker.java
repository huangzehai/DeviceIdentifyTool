/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.ui.worker;

import com.u2apple.tool.model.ModelWorkderResult;
import com.u2apple.tool.dao.DeviceXmlDao;
import com.u2apple.tool.dao.DeviceXmlDaoJaxbImpl;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.SwingWorker;

/**
 *
 * @author Adam
 */
public class ModelWorker extends SwingWorker<ModelWorkderResult, Void> {

    private final String model;
    private final String vid;
    private final JCheckBox existCheckBox;
    private final JCheckBox existInVidCheckBox;
    private final DeviceXmlDao deviceXmlDao = new DeviceXmlDaoJaxbImpl();

    public ModelWorker(String vid, String model, JCheckBox existCheckBox, JCheckBox existInVidCheckBox) {
        this.model = model;
        this.vid = vid;
        this.existCheckBox = existCheckBox;
        this.existInVidCheckBox = existInVidCheckBox;
    }

    @Override
    protected ModelWorkderResult doInBackground() throws Exception {
//        boolean existingInGlobal = RecognitionTool.modelExists(model);
        boolean existingInGlobal = deviceXmlDao.modelExists(model);
//        boolean existingInVid = RecognitionTool.modelExists(vid, model);
        boolean existingInVid = deviceXmlDao.modelExists(vid, model);
        return new ModelWorkderResult(existingInGlobal, existingInVid);
    }

    @Override
    protected void done() {
        try {
            ModelWorkderResult result = get();
            existCheckBox.setSelected(result.isExistingInGlobal());
            existInVidCheckBox.setSelected(result.isExistingInVid());
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(ModelWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
