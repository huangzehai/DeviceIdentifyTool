/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.service;

import com.jcraft.jsch.JSchException;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Adam
 */
public interface KnockOffDetectionService {
     List<AndroidDeviceRanking>  analyticsByCpu()throws IOException, JSchException;
    
}
