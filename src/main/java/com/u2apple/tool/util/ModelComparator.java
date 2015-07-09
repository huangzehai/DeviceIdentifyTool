/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.util;

import com.u2apple.tool.dao.DeviceXmlDao;
import java.util.Comparator;
import org.dom4j.Element;

/**
 *
 * @author Adam
 */
public class ModelComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Element n1 = (Element) o1;
        Element n2 = (Element) o2;
        if (DeviceXmlDao.contains(n1, n2)) {
            return 1;
        } else {
            return 0;
        }
    }

}
