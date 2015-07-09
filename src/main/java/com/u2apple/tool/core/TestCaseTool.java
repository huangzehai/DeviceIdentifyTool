package com.u2apple.tool.core;

import com.google.gson.Gson;
import com.u2apple.tool.constant.Constants;
import com.u2apple.tool.model.AndroidDevice;
import com.u2apple.tool.model.DeviceTestCase;
import com.u2apple.tool.ui.MainFrame;
import com.u2apple.tool.util.FreemarkerUtils;
import com.u2apple.tool.util.PartitionUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestCaseTool {

    public static String generateDeviceTestCase(String productId, String vid, String roProductModel) {
        DeviceTestCase deviceTestCase = new DeviceTestCase(productId, vid, roProductModel);
        return FreemarkerUtils.generate(deviceTestCase, "DeviceTestCase.ftl");
    }

    public static String generateDeviceTestCase(String productId, String vid, String roProductModel, String roProductBrand) {
        DeviceTestCase deviceTestCase = new DeviceTestCase(productId, vid, roProductModel, roProductBrand);
        return FreemarkerUtils.generate(deviceTestCase, "DeviceTestCaseWithCondition.ftl");
    }

    public static String generateDeviceTestCase(String productId, String vid, String roProductModel, String resolution, String partitions) {
        DeviceTestCase deviceTestCase = new DeviceTestCase(productId, vid, roProductModel);
        deviceTestCase.setResolution(resolution);
        deviceTestCase.setPartitions(PartitionUtils.parsePartition(partitions));
        return FreemarkerUtils.generate(deviceTestCase, "KnockOffDeviceTestCase.ftl");
    }

    /**
     * Generate or update test case.
     * @param androidDevice 
     */
    public static void generateTestCase( AndroidDevice androidDevice) {
//        Set<String> vidSet = new HashSet<>();
//        vidSet.add(androidDevice.getVid());
        //Always add recovery model vid.
//        vidSet.add("18D1");
        try {
//            androidDevice.setVids(vidSet.toArray(new String[vidSet.size()]));
            Gson gson = new Gson();
            String json;
            Path path = Paths.get(System.getProperty("user.home"), Constants.ANDROID_DEVICES_JSON_FILE);
            if (!path.toFile().exists()) {
                json = gson.toJson(new AndroidDevice[]{androidDevice});
                Files.write(path, json.getBytes(), StandardOpenOption.CREATE);
            } else {
                byte[] bytes = Files.readAllBytes(path);
                AndroidDevice[] androidDevices = gson.fromJson(new String(bytes), AndroidDevice[].class);
                if (androidDevices != null && androidDevices.length != 0) {
                    List<AndroidDevice> newAndroidDevices = new ArrayList<>();
                    newAndroidDevices.addAll(Arrays.asList(androidDevices));
                    newAndroidDevices.add(androidDevice);
                    json = gson.toJson(newAndroidDevices);
                } else {
                    //When file is empty.
                    json = gson.toJson(new AndroidDevice[]{androidDevice});
                }
                Files.write(path, json.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Deprecated
    public static void updateTestCase( AndroidDevice androidDevice) {
        Set<String> vidSet = new HashSet<>();
        vidSet.add(androidDevice.getVid());
        try {
            androidDevice.setVids(vidSet.toArray(new String[vidSet.size()]));
            Gson gson = new Gson();
            String json;
            Path path = Paths.get(System.getProperty("user.home"), Constants.ANDROID_DEVICES_JSON_FILE);
            if (!path.toFile().exists()) {
                json = gson.toJson(new AndroidDevice[]{androidDevice});
                Files.write(path, json.getBytes(), StandardOpenOption.CREATE);
            } else {
                byte[] bytes = Files.readAllBytes(path);
                AndroidDevice[] androidDevices = gson.fromJson(new String(bytes), AndroidDevice[].class);
                if (androidDevices != null && androidDevices.length != 0) {
                    List<AndroidDevice> newAndroidDevices = new ArrayList<>();
                    newAndroidDevices.addAll(Arrays.asList(androidDevices));
                    newAndroidDevices.add(androidDevice);
                    json = gson.toJson(newAndroidDevices);
                } else {
                    //When file is empty.
                    json = gson.toJson(new AndroidDevice[]{androidDevice});
                }
                Files.write(path, json.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
