/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.service;

import com.shuame.wandoujia.bean.Device;
import com.shuame.wandoujia.bean.Modal;
import com.shuame.wandoujia.bean.ProductId;
import com.shuame.wandoujia.bean.StaticMapFile;
import com.shuame.wandoujia.bean.VID;
import com.u2apple.tool.dao.DeviceXmlDao;
import com.u2apple.tool.dao.DeviceXmlDaoJaxbImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Adam
 */
public class DeviceServiceImpl implements DeviceService {

    private final DeviceXmlDao deviceXmlDao = new DeviceXmlDaoJaxbImpl();

    @Override
    public List<String> listFullProductId() {
        return v2();
    }

    private List<String> v1() {
        StaticMapFile staticMapFile = deviceXmlDao.getStaticMapFile();
        List<String> knockOffProductIds = listKnockOffProductId(staticMapFile.getDevices());
        List<String> fullProductIds = new ArrayList<>();
        List<VID> vids = staticMapFile.getVids();
        for (String productId : knockOffProductIds) {
            if (requireAdvanceProps(productId, vids)) {
                fullProductIds.add(productId);
            }
        }
        return fullProductIds;
    }

    private List<String> v2() {
        Set<String> productIds = new HashSet<>();
        StaticMapFile staticMapFile = deviceXmlDao.getStaticMapFile();
        List<VID> vids = staticMapFile.getVids();
        for (VID vid : vids) {
            for (Modal model : vid.getModals()) {
                for (ProductId productId : model.getProductId()) {
                    if (StringUtils.contains(productId.getCondition(), "type")) {
                        productIds.add(productId.getValue());
                    }
                }
            }
        }

        Set<String> knockOffProductIds = new HashSet<>();
        Set<String> disknockOffProductIds = new HashSet<>();

        Set<String> pIds = new HashSet<>();
        for (String productId : productIds) {
            if (isKnockOff(productId)) {
                knockOffProductIds.add(productId);
            } else {
                disknockOffProductIds.add(productId);
            }
        }

        //remove the same product id.
        for (String productId : disknockOffProductIds) {
            String kp = toKnockOff(productId);
            {
                if (!knockOffProductIds.contains(kp)) {
                    pIds.add(productId);
                }
            }
        }

        knockOffProductIds.addAll(pIds);
        for (String productId : knockOffProductIds) {
            pIds.add(toReal(productId));
        }
        List<String> list = new ArrayList<>(pIds);
        Collections.sort(list);
        return list;
    }

    private boolean isKnockOff(String productId) {
        return !productId.startsWith("cmcc-") && productId.contains("cc-");
    }

    private String toKnockOff(String productId) {
        return productId.replace("-", "cc-");
    }

    private String toReal(String productId) {
        return productId.replace("cc-", "-");
    }

    private List<String> listKnockOffProductId(List<Device> devices) {
        List<String> knockOffProductIds = new ArrayList<>();
        if (devices != null) {
            for (Device device : devices) {
                if (!device.getProductId().startsWith("cmcc-") && device.getProductId().contains("cc-")) {
                    knockOffProductIds.add(device.getProductId());
                }
            }
        }
        return knockOffProductIds;
    }

    private boolean requireAdvanceProps(String productId, List<VID> vids) {
        for (VID vid : vids) {
            for (Modal model : vid.getModals()) {
                for (ProductId aProductId : model.getProductId()) {
                    if (aProductId.getValue().equals(productId) && StringUtils.contains(aProductId.getCondition(), "type")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
