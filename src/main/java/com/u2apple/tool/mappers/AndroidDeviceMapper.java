/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.mappers;

import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author Adam
 */
public interface AndroidDeviceMapper {

    /**
     * 查询刷机精灵PC版未识别机型
     *
     * @param tableName
     * @param start
     * @return AndroidDeviceRanking list .
     */
    List<AndroidDeviceRanking> selectUnidentifiedDevices(@Param("table") String tableName,@Param("start")Date start);
    
    List<AndroidDeviceRanking> listModelWithRanking(@Param("table") String tableName,@Param("start")Date start);
    
    List<AndroidDeviceRanking> listCpu(@Param("table") String tableName,@Param("start")Date start);

}
