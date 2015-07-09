/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.cache;

import com.shuame.wandoujia.bean.Device;
import com.shuame.wandoujia.bean.Modal;
import com.shuame.wandoujia.bean.StaticMapFile;
import com.shuame.wandoujia.bean.VID;
import com.shuame.wandoujia.bean.Value;
import com.u2apple.tool.constant.Configuration;
import com.u2apple.tool.constant.Constants;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Adam
 */
public class DeviceCache {

    private static final Set<String> productIds = new HashSet<>();
    private static final Map<String, Set<String>> models = new HashMap<>();

    static {
        init();
    }

    private static void init() {
        try {
            loadProductIds();
            loadModels();
        } catch (JAXBException ex) {
            Logger.getLogger(DeviceCache.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void loadProductIds() throws JAXBException {
        File file = new File(Configuration.getProperty(Constants.DEVICES_XML));
        JAXBContext jaxbContext = JAXBContext.newInstance(StaticMapFile.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        StaticMapFile staticMapFile = (StaticMapFile) jaxbUnmarshaller.unmarshal(file);
        if (staticMapFile != null) {
            List<Device> devices = staticMapFile.getDevices();
            for (Device device : devices) {
                productIds.add(device.getProductId());
            }
        }
    }

    public static void loadModels() throws JAXBException {
        File dir = new File(Configuration.getProperty(Constants.VID_DIR));
        if (dir.exists()) {
            File[] vidFiles = dir.listFiles();
            for (File vidFile : vidFiles) {
                JAXBContext jaxbContext = JAXBContext.newInstance(VID.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                VID vid = (VID) jaxbUnmarshaller.unmarshal(vidFile);
                if (vid != null) {
                    Set<String> set = new HashSet<>();
                    List<Modal> modals = vid.getModals();
                    for (Modal modal : modals) {
                        for (Value value : modal.getValues()) {
                            set.add(value.getValue().toLowerCase());
                        }
                    }
                    models.put(vid.getValue(), set);
                }
            }
        }
    }

    public static Set<String> getProductIds() {
        return productIds;
    }

    public static void addProductId(String productId) {
        productIds.add(productId);
    }

    public static void addModel(final String[] vids, final List<String> ms) {
        for (String vid : vids) {
            if (models.containsKey(vid)) {
                models.get(vid).addAll(ms);
            } else {
                models.put(vid, new HashSet<String>() {
                    {
                        addAll(ms);
                    }
                });
            }
        }
    }

    public static Map<String, Set<String>> getModels() {
        return models;
    }

}
