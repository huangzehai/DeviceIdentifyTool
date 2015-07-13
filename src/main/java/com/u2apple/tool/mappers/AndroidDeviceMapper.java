/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.mappers;

import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.List;

/**
 *
 * @author Adam
 */
public interface AndroidDeviceMapper {

    // @Select("SELECT count(*) FROM log_device_init_201507")
    public int selectCount();

    /**
     * 查询刷机精灵PC版未识别机型
     *
     * @param days
     * @return AndroidDeviceRanking list .
     */
    List<AndroidDeviceRanking> selectUnidentifiedDevices(int days);

}
