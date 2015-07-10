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
import java.util.Comparator;
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
        Collections.sort(devices, new Comparator<Device>() {

            @Override
            public int compare(Device o1, Device o2) {
                return o1.getProductId().compareTo(o2.getProductId());
            }
        });
    }

    private static void sortModels(List<VID> vids) {
        for (VID vid : vids) {
            sortModels(vid);
        }
    }

    private static void sortModels(List<VID> vids, Set<String> changedVids) {
        for (VID vid : vids) {
            if (changedVids.contains(vid.getValue())) {
                sortModels(vid);
            }
        }
    }

    private static void sortModels(VID vid) {
        //Sort model values.
        for (Modal model : vid.getModals()) {
            sortValue(model.getValues());
        }

        //Sort models.
        Collections.sort(vid.getModals(), new Comparator<Modal>() {
            @Override
            public int compare(Modal o1, Modal o2) {
                return o1.getValues().get(0).getValue().compareTo(o2.getValues().get(0).getValue());
            }
        });

        //Reverse contained model.
        List<Modal> models = vid.getModals();
        for (int i = 0; i < models.size(); i++) {
            for (int j = i + 1; j < models.size(); j++) {
                if (valueContains(models.get(j).getValues(), models.get(i).getValues())) {
                    models.add(i, models.remove(j));
                }
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
        Collections.sort(values, new Comparator<Value>() {
            @Override
            public int compare(Value o1, Value o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
    }

}
