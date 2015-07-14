/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.cache;

import com.u2apple.tool.constant.Constants;
import com.u2apple.tool.model.AndroidDeviceRanking;
import com.u2apple.tool.model.CheckedDevices;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Adam
 */
public class DeviceCache {

    private static CheckedDevices checkedDevices;
    private static boolean changed = false;

    private static CheckedDevices getCheckedDevices() throws JAXBException {
        if (checkedDevices == null) {
            loadCheckedDevices();
        }
        return checkedDevices;
    }

    public static void markDeviceChecked(AndroidDeviceRanking androidDevice) throws JAXBException {
        getCheckedDevices().getDevices().add(androidDevice);
        changed = true;
    }

    private static void loadCheckedDevices() throws JAXBException {
        File file = new File(Constants.CHECKED_DEVICES);
        if (file.exists()) {
            JAXBContext jaxbContext = JAXBContext.newInstance(CheckedDevices.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            checkedDevices = (CheckedDevices) jaxbUnmarshaller.unmarshal(file);
        } else {
            checkedDevices = new CheckedDevices();
            checkedDevices.setDevices(new ArrayList<AndroidDeviceRanking>());
        }

    }

    public static void flush() throws PropertyException, JAXBException {
        if (changed) {
            File file = new File(Constants.CHECKED_DEVICES);
            JAXBContext jaxbContext = JAXBContext.newInstance(CheckedDevices.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(checkedDevices, file);
        }
    }

    public static boolean isChecked(AndroidDeviceRanking device) {
        boolean isChecked = false;
        List<AndroidDeviceRanking> devices = null;
        try {
            devices = getCheckedDevices().getDevices();
        } catch (JAXBException ex) {
            Logger.getLogger(DeviceCache.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (devices != null) {
            for (AndroidDeviceRanking checkedDevice : devices) {
                if (device.getVid().equalsIgnoreCase(checkedDevice.getVid()) && device.getRoProductBrand().equalsIgnoreCase(checkedDevice.getRoProductBrand()) && device.getRoProductModel().equalsIgnoreCase(checkedDevice.getRoProductModel())) {
                    isChecked = true;
                    break;
                }
            }
        }
        return isChecked;
    }
}
