/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.mappers;

import com.u2apple.tool.model.AndroidDevice;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author Adam
 */
public interface RootDeviceMapper {
    /** 列出最近几天未识别机型，按连接次数到排序，列出前1000个型号
     * @param tableName
     * @param start.
     * @return */
     List<AndroidDeviceRanking> selectUnidentifiedDevices(@Param("table")String tableName,@Param("start")Date start);
     
      List<AndroidDevice> getDeviceByVidAndModel(@Param("table")String tableName,@Param("vid")String vid,@Param("model")String model,@Param("limit")int limit);
}
