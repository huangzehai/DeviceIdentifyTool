/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.model;

/**
 *
 * @author Adam
 */
public class ModelExistence {

    private boolean existingInGlobal;
    private boolean existingInVid;

    public boolean isExistingInGlobal() {
        return existingInGlobal;
    }

    public void setExistingInGlobal(boolean existingInGlobal) {
        this.existingInGlobal = existingInGlobal;
    }

    public boolean isExistingInVid() {
        return existingInVid;
    }

    public void setExistingInVid(boolean existingInVid) {
        this.existingInVid = existingInVid;
    }

    public ModelExistence(boolean existingInGlobal, boolean existingInVid) {
        this.existingInGlobal = existingInGlobal;
        this.existingInVid = existingInVid;
    }

    @Override
    public String toString() {
        return "ModelWorkderResult{" + "existingInGlobal=" + existingInGlobal + ", existingInVid=" + existingInVid + '}';
    }

}
