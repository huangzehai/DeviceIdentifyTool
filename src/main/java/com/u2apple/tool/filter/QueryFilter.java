/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.filter;

import com.u2apple.tool.model.AndroidDeviceRanking;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Adam
 */
public class QueryFilter implements Filter {

    private final String query;

    public QueryFilter(String query) {
        this.query = query;
    }

    @Override
    public List<AndroidDeviceRanking> filter(List<AndroidDeviceRanking> androidDevices) {
        List<AndroidDeviceRanking> newDevices = new ArrayList<>();
        if (isQueryValid(query)) {
            if (StringUtils.contains(query, "AND")) {
                String[] queryItems = query.split("AND");
                for (AndroidDeviceRanking device : androidDevices) {
                    boolean matches = true;
                    for (String queryItem : queryItems) {
                        String[] q = queryItem.split(":");
                        String key = q[0].trim();
                        String expected = q[1].trim();
                        String value = getValueByKey(device, key);
                        if (!StringUtils.equalsIgnoreCase(expected, value)) {
                            matches = false;
                        }
                    }
                    if (matches) {
                        newDevices.add(device);
                    }
                }
            } else if (StringUtils.contains(query, ":")) {
                String[] q = query.split(":");
                String key = q[0].trim();
                String expected = q[1].trim();
                for (AndroidDeviceRanking device : androidDevices) {
                    String value = getValueByKey(device, key);
                    if (StringUtils.equalsIgnoreCase(expected, value)) {
                        newDevices.add(device);
                    }
                }
            } else {
                String[] queries = query.split("\\s");
                if (queries.length == 2) {
                    String expectedBrand = queries[0];
                    String expectedVid = queries[1];
                    for (AndroidDeviceRanking device : androidDevices) {
                        if (StringUtils.equalsIgnoreCase(expectedBrand, device.getRoProductBrand()) && StringUtils.equalsIgnoreCase(expectedVid, device.getVid())) {
                            newDevices.add(device);
                        }
                    }
                }
            }
        }
        return newDevices;
    }

    private boolean isQueryValid(String query) {
//        Pattern pattern = Pattern.compile("(vid|brand|model):.*", Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(query);
//        return matcher.matches();
        return StringUtils.isNotBlank(query);
    }

    private String getValueByKey(AndroidDeviceRanking device, String key) {
        if ("vid".equalsIgnoreCase(key)) {
            return device.getVid();
        } else if ("brand".equalsIgnoreCase(key)) {
            return device.getRoProductBrand();
        } else if ("model".equalsIgnoreCase(key)) {
            return device.getRoProductModel();
        } else {
            return null;
        }
    }

}
