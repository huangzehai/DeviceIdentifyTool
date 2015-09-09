/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.filter;

import com.u2apple.tool.constant.Constants;
import com.u2apple.tool.model.AndroidDeviceRanking;
import com.u2apple.tool.model.filter.Rule;
import com.u2apple.tool.model.filter.RuleItem;
import com.u2apple.tool.model.filter.Rules;
import com.u2apple.tool.util.AndroidDeviceUtils;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Adam
 */
public class ExcludeFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ExcludeFilter.class);
    private static final String RULES_CONF = "/com/u2apple/tool/conf/exclude-filter.xml";
    private static Rules rules;

    static {
        loadRules();
    }

    @Override
    public List<AndroidDeviceRanking> filter(List<AndroidDeviceRanking> androidDevices) {
        List<AndroidDeviceRanking> filteredDevices = new ArrayList<>();
        if (androidDevices != null) {
            androidDevices.stream().filter((device) -> (StringUtils.isNotBlank(device.getRoProductModel()) && !rulesmatches(device) && !StringUtils.equalsIgnoreCase(device.getRoProductBrand(), device.getRoProductModel()))).forEach((device) -> {
                filteredDevices.add(device);
            });
        }
        return new CheckedDeviceFilter().filter(filteredDevices);
    }

    private boolean rulesmatches(AndroidDeviceRanking androidDevice) {
        boolean matches = false;
        if (androidDevice != null) {
            for (Rule rule : rules.getRules()) {
                if (ruleMatches(androidDevice, rule)) {
                    matches = true;
                    break;
                }
            }
        }
        return matches;
    }

    private boolean ruleMatches(AndroidDeviceRanking androidDevice, Rule rule) {
        RuleItem[] ruleItems = rule.getRuleItems();
        boolean matches = true;
        for (RuleItem ruleItem : ruleItems) {
            if (!ruleItemMatches(androidDevice, ruleItem)) {
                matches = false;
                break;
            }
        }
        return matches;
    }

    private boolean ruleItemMatches(AndroidDeviceRanking androidDevice, RuleItem ruleItem) {
        boolean matches = false;
        String prop = AndroidDeviceUtils.getPropertyByKey(androidDevice, ruleItem.getKey());
        if (prop != null) {
            String value = ruleItem.getValue();
            String operator = ruleItem.getOperator();
            if (value.contains(Constants.SEPARATOR)) {
                String[] values = value.split(Constants.SEPARATOR);
                for (String v : values) {
                    matches = valueMatches(operator, prop, v);
                    if (matches) {
                        break;
                    }
                }
            } else {
                matches = valueMatches(operator, prop, value);
            }
        }
        return matches;
    }

    private boolean valueMatches(String operator, String prop, String value) {
        boolean matches = false;
        if (null != operator) switch (operator) {
            case "equals":
                matches = prop.equals(value);
                break;
            case "equalsIgnoreCase":
                matches = prop.equalsIgnoreCase(value);
                break;
            case "regexp":
                matches = Pattern.matches(value, prop);
                break;
        }
        return matches;
    }

    private static void loadRules() {
        URL url = DevicePatternFilter.class.getResource(RULES_CONF);
        File file = new File(url.getFile());
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Rules.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            rules = (Rules) unmarshaller.unmarshal(file);
        } catch (JAXBException ex) {
            logger.error("Fail to parse exclude-filter.xml as {}", ex);
        }
    }

}
