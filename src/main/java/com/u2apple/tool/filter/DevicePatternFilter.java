/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.filter;

import com.u2apple.tool.model.AndroidDevice;
import com.u2apple.tool.model.AndroidDeviceRanking;
import com.u2apple.tool.model.rule.Rule;
import com.u2apple.tool.model.rule.Rules;
import com.u2apple.tool.util.ArrayUtils;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Adam
 */
public class DevicePatternFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(DevicePatternFilter.class);

    private static final String DEFAULT_PATTERN_FILE = "/com/u2apple/rt/conf/device-pattern.xml";

    private static final String OTHERS_PATTERN_FILE = "/com/u2apple/rt/conf/device-pattern-others.xml";

    private Rules rules;

    public DevicePatternFilter() {
        rules = loadRules(DEFAULT_PATTERN_FILE);
    }

    public DevicePatternFilter(boolean others) {
        if (others) {
              rules = loadRules(OTHERS_PATTERN_FILE);
        }else{
              rules = loadRules(DEFAULT_PATTERN_FILE);
        }
    }



    /**
     * Match by brand,vid,and ro.product.model.
     *
     * @param androidDevice
     * @return
     */
    public boolean matches(AndroidDevice androidDevice) {
        //Refresh each time. Just for testing. TODO Remove this line.
//        rules = loadRules(DEFAULT_PATTERN_FILE);

        boolean matches;
        matches = false;
        if (rules != null) {
            for (Rule rule : rules.getRules()) {
                if (matchRule(androidDevice, rule)) {
                    matches = true;
                    break;
                }
            }
        }
        return matches;
    }

    /**
     * Filtere by brand,vid,ro.product.model.
     *
     * @param androidDevices
     * @return
     */
    @Override
    public List<AndroidDeviceRanking> filter(List<AndroidDeviceRanking> androidDevices) {
        List<AndroidDeviceRanking> newList = new ArrayList<>();
        for (AndroidDeviceRanking androidDevice : androidDevices) {
            if (matches(androidDevice)) {
                newList.add(androidDevice);
            }
        }
        return newList;
    }

    private boolean matchRule(AndroidDevice androidDevice, Rule rule) {
        boolean matches;
        if (isRuleValid(rule)) {
            boolean isBrandEqual = ArrayUtils.containsIgoreCase(rule.getBrands(), androidDevice.getRoProductBrand());
            boolean isVidValid = Arrays.asList(rule.getVids()).contains(androidDevice.getVid());
            boolean isModelValid = isModelValid(androidDevice.getRoProductModel(), rule.getPatterns());
            matches = isBrandEqual && isVidValid && isModelValid;
        } else {
            matches = false;
        }
        return matches;
    }

    private boolean isModelValid(String model, String[] patterns) {
        boolean matches = false;
        for (String pattern : patterns) {
            Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
            if (p.matcher(model).matches()) {
                matches = true;
                break;
            }
        }
        logger.debug("model {} doesn't match pattern", model);
        return matches;
    }

    private boolean isRuleValid(Rule rule) {
        if (rule.getBrands().length < 1) {
            logger.error("brand should not be blank {}", rule);
            return false;
        } else if (rule.getVids().length < 1) {
            logger.error("Vid is requied {}", rule);
            return false;
        } //        else if (rule.getPatterns().length < 1) {
        //            logger.error("Pattern is requied {}", rule);
        //            return false;
        //        }
        else {
            return true;
        }
    }

    private Rules loadRules(String patternFile) {
        URL url = DevicePatternFilter.class.getResource(patternFile);
        File file = new File(url.getFile());
        JAXBContext jaxbContext;
        Rules rules = null;
        try {
            jaxbContext = JAXBContext.newInstance(Rules.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            rules = (Rules) unmarshaller.unmarshal(file);
        } catch (JAXBException ex) {
            logger.error("Fail to parse device-pattern.xml as {}", ex);
        }
        return rules;
    }

}
