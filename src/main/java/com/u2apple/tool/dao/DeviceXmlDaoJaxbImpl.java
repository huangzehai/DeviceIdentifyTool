/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import com.shuame.wandoujia.bean.Device;
import com.shuame.wandoujia.bean.Modal;
import com.shuame.wandoujia.bean.StaticMapFile;
import com.shuame.wandoujia.bean.VID;
import com.shuame.wandoujia.bean.Value;
import com.u2apple.tool.conf.Configuration;
import com.u2apple.tool.constant.Constants;
import com.u2apple.tool.util.StaticMapFileUtils;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Adam
 */
public class DeviceXmlDaoJaxbImpl implements DeviceXmlDao {

    private static volatile  StaticMapFile staticMapFile;

    /**
     * 机型识别配置文件的文件名正则表达式.
     */
    private static final String VID_FILE_NAME_PATTERN = "^[0-9a-zA-Z]{4}\\.xml$";
    private static final Set<String> changedVids = new HashSet<>();
    private static boolean isDeviceChanged = false;
    private DeviceI18nDao deviceI18nDao = new DeviceI18nDaoImpl();

    /**
     * 加载机型识别配置文件.
     *
     * @return
     * @throws ShuameException
     */
    private void loadStaticMapFile() throws JAXBException {
        File file = new File(Configuration.getProperty(Constants.DEVICES_XML));
        JAXBContext jaxbContext = JAXBContext.newInstance(StaticMapFile.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        staticMapFile = (StaticMapFile) jaxbUnmarshaller.unmarshal(file);
    }

    private void loadVids() throws JAXBException {
        File dir = new File(Configuration.getProperty(Constants.VID_DIR));
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return Pattern.matches(VID_FILE_NAME_PATTERN, name);
            }
        });
        if (files != null) {
            for (File file : files) {
                JAXBContext jaxbContext = JAXBContext.newInstance(VID.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                VID vid = (VID) jaxbUnmarshaller.unmarshal(file);
                if (vid != null) {
                    staticMapFile.getVids().add(vid);
                }
            }
        }
    }

    @Override
    public StaticMapFile getStaticMapFile() {
        if (staticMapFile == null) {
            synchronized (StaticMapFile.class) {
                if (staticMapFile == null) {
                    try {
                        loadStaticMapFile();
                        loadVids();
                    } catch (JAXBException ex) {
                        Logger.getLogger(DeviceXmlDaoJaxbImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return staticMapFile;
    }

    @Override
    public void flush() throws PropertyException, JAXBException {
        if (staticMapFile != null) {
            StaticMapFileUtils.format(staticMapFile, isDeviceChanged, changedVids);
            flushDevices();
            flushVids();
            deviceI18nDao.store();
        }
    }

    private void flushDevices() throws JAXBException, PropertyException {
        if (isDeviceChanged) {
            File file = new File(Configuration.getProperty(Constants.DEVICES_XML));
            JAXBContext jaxbContext = JAXBContext.newInstance(StaticMapFile.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(getStaticMapFile(), file);
            isDeviceChanged = false;
        }
    }

    private void flushVids() throws JAXBException, PropertyException {
        String vidFileFormat = Configuration.getProperty(Constants.VID_FILE_FORMAT);
        for (String vid : changedVids) {
            File file = new File(String.format(vidFileFormat, vid));
            JAXBContext jaxbContext = JAXBContext.newInstance(VID.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            VID v = getVid(getStaticMapFile().getVids(), vid);
            jaxbMarshaller.marshal(v, file);
        }
        changedVids.clear();
    }

    @Override
    public void addDevice(Device device) {
        getStaticMapFile().getDevices().add(device);
        deviceI18nDao.addDevice(device);
        isDeviceChanged = true;
    }

    @Override
    public int deviceCount() {
        return getStaticMapFile().getDevices().size();
    }

    @Override
    public void addModel(String[] vids, Modal model) {
        for (String vid : vids) {
            List<VID> vs = getStaticMapFile().getVids();
            VID v = getVid(vs, vid);
            if (v == null) {
                v = new VID();
                v.setValue(vid);
                vs.add(v);
            }
            v.getModals().add(model);
        }
        changedVids.addAll(Arrays.asList(vids));
    }

    private VID getVid(List<VID> vids, String vid) {
        VID result = null;
        for (VID v : vids) {
            if (v.getValue().equalsIgnoreCase(vid)) {
                result = v;
                break;
            }
        }
        return result;
    }

    @Override
    public void format(String vid) {
        changedVids.add(vid);
        StaticMapFileUtils.format(getStaticMapFile(), false, changedVids);
    }

    @Override
    public boolean modelExists(String vid, String text) {
        boolean exists = false;
        if (StringUtils.isBlank(vid) || StringUtils.isBlank(text)) {
            return exists;
        }
        List<VID> vids = getStaticMapFile().getVids();
        VID v = getVid(vids, vid);
        return modelExists(v, text);
    }

    private boolean modelExists(VID vid, String text) {
        boolean exists = false;
        if (vid != null) {
            List<Modal> models = vid.getModals();
            for (Modal model : models) {
                List<Value> values = model.getValues();
                for (Value value : values) {
                    if (value.getValue().equalsIgnoreCase(text)) {
                        exists = true;
                        break;
                    }
                }
            }
        }
        return exists;
    }

    @Override
    public boolean modelExists(String text) {
        boolean exists = false;
        if (StringUtils.isBlank(text)) {
            return exists;
        }
        List<VID> vids = getStaticMapFile().getVids();
        for (VID vid : vids) {
            if (modelExists(vid, text)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    @Override
    public boolean productIdExists(String productId) {
        boolean exists = false;
        if (StringUtils.isBlank(productId)) {
            return exists;
        }
        List<Device> devices = getStaticMapFile().getDevices();
        for (Device device : devices) {
            if (device.getProductId().equalsIgnoreCase(productId)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    @Override
    public boolean brandExists(String brandKey) {
        return deviceI18nDao.brandExists(brandKey);
    }

    @Override
    public String getChineseBrand(String brandKey) {
        return deviceI18nDao.getChineseBrand(brandKey);
    }

    @Override
    public String getEnglishBrand(String brandKey) {
       return deviceI18nDao.getEnglishBrand(brandKey);
    }

}
