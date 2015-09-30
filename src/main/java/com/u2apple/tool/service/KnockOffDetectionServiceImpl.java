/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.service;

import com.jcraft.jsch.JSchException;
import com.u2apple.tool.dao.AndroidDeviceDao;
import com.u2apple.tool.dao.AndroidDeviceDaoImpl;
import com.u2apple.tool.model.AndroidDeviceRanking;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Adam
 */
public class KnockOffDetectionServiceImpl implements KnockOffDetectionService {

    private final AndroidDeviceDao androidDeviceDao = new AndroidDeviceDaoImpl();

    class Devices {

        private int count;
        private List<AndroidDeviceRanking> devices;

        public Devices(int count, List<AndroidDeviceRanking> devices) {
            this.count = count;
            this.devices = devices;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<AndroidDeviceRanking> getDevices() {
            return devices;
        }

        public void setDevices(List<AndroidDeviceRanking> devices) {
            this.devices = devices;
        }

        @Override
        public String toString() {
            return "Devices{" + "count=" + count + ", devices=" + devices + '}';
        }
    }

    @Override
    public List<AndroidDeviceRanking> analyticsByCpu() throws IOException, JSchException {
        List<AndroidDeviceRanking> devices = androidDeviceDao.listCpu(1);

        if (devices != null && !devices.isEmpty()) {
            //Group by product id.
            Map<String, Devices> map = new HashMap<>();
            devices.parallelStream().forEach(device -> {
                if (map.containsKey(device.getProductId())) {
                    Devices ds = map.get(device.getProductId());
                    ds.getDevices().add(device);
                    ds.setCount(ds.getCount() + device.getCount());
                } else {
                    List<AndroidDeviceRanking> devicesOfTheSameProductId = new ArrayList<>();
                    devicesOfTheSameProductId.add(device);
                    map.put(device.getProductId(), new Devices(device.getCount(), devicesOfTheSameProductId));
                }
            });
            return map.values().stream().filter((ds) -> ds.getDevices().size() > 1).sorted((o1, o2) -> o2.getCount() - o1.getCount()).flatMap(d -> d.getDevices().stream()).map(d->{ d.setRoProductModel(d.getCpuHardware());return d;}).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    class ValueComparator implements Comparator<Devices> {

        @Override
        public int compare(Devices o1, Devices o2) {
            return o1.getCount() - o2.getCount();
        }
    }
}
