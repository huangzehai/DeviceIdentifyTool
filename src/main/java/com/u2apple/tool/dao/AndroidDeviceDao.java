/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.List;

/**
 *
 * @author Adam
 */
public interface AndroidDeviceDao {

    int getCount();

    List<AndroidDeviceRanking> getUnidentifiedDevices(int days);

}
