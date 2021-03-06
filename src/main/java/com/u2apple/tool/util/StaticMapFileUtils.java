/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.util;

import com.shuame.wandoujia.bean.Device;
import com.shuame.wandoujia.bean.Modal;
import com.shuame.wandoujia.bean.StaticMapFile;
import com.shuame.wandoujia.bean.VID;
import com.shuame.wandoujia.bean.Value;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Adam
 */
public final class StaticMapFileUtils {

    public static void format(StaticMapFile staticMapFile) {
        if (staticMapFile != null) {
            sortDevices(staticMapFile.getDevices());
            sortModels(staticMapFile.getVids());
        }
    }

    public static void format(StaticMapFile staticMapFile, boolean isDeviceChanged, Set<String> changedVids) {
        if (staticMapFile != null) {
            if (isDeviceChanged) {
                sortDevices(staticMapFile.getDevices());
            }
            sortModels(staticMapFile.getVids(), changedVids);
        }
    }

    private static void sortDevices(List<Device> devices) {
        Collections.sort(devices, (o1, o2) -> o1.getProductId().compareTo(o2.getProductId()));
    }

    private static void sortModels(List<VID> vids) {
        vids.stream().forEach((vid) -> {
            sortModels(vid);
        });
    }

    private static void sortModels(List<VID> vids, Set<String> changedVids) {
        vids.stream().filter((vid) -> (changedVids.contains(vid.getValue()))).forEach((vid) -> {
            sortModels(vid);
        });
    }

    private static void sortModels(VID vid) {
        List<Modal> models = vid.getModals();
        //Sort model values.
        models.stream().forEach((model) -> {
            if (model.getValues() == null || model.getValues().isEmpty()) {
                System.out.println(model);
            }
            sortValue(model.getValues());
        });

        //Sort models.
        Collections.sort(models, (o1, o2) -> o1.getValues().get(0).getValue().compareToIgnoreCase(o2.getValues().get(0).getValue()));

        //Merge models.
        fastMergeModels(models);

        //Reverse contained model.
        for (int i = 0; i < models.size(); i++) {
            for (int j = i + 1; j < models.size(); j++) {
                if (valueContains(models.get(j).getValues(), models.get(i).getValues())) {
                    models.add(i, models.remove(j));
                }
            }
        }

    }

    /**
     * Merage after sorting. 合并Values节点相同的Modal节点。 TODO:
     * 同时添加多个相同Value的Modal节点时不能正确合并.
     *
     * @param models
     */
    private static void fastMergeModels(List<Modal> models) {
        for (int i = 0; i < models.size() - 1;) {
            if (models.get(i).getValues().equals(models.get(i + 1).getValues())) {
                models.get(i).getProductId().addAll(models.get(i + 1).getProductId());
                models.remove(i + 1);
            }else{
                i++;
            }
        }
    }

    private static boolean valueContains(List<Value> values1, List<Value> values2) {
        boolean contains = false;
        for (Value value1 : values1) {
            for (Value value2 : values2) {
                if (StringUtils.containsIgnoreCase(value1.getValue(), value2.getValue())) {
                    contains = true;
                    break;
                }
            }
        }
        return contains;
    }

    private static void sortValue(List<Value> values) {
        Collections.sort(values, (v1, v2) -> v1.getValue().compareTo(v2.getValue()));
    }

}
