/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.util;

import java.util.Map;

/**
 *
 * @author Adam
 */
public final class ConditionUtils {

    private ConditionUtils() {

    }

    public static String build(Map<String, String> conditions) {
        if (conditions.isEmpty()) {
            return null;
        }
        StringBuilder conditionBuilder = new StringBuilder();
        int count = 0;
        for (Map.Entry<String, String> condition : conditions.entrySet()) {
            conditionBuilder.append(condition.getKey());
            conditionBuilder.append("=");
            conditionBuilder.append(condition.getValue());
            if (count < conditions.entrySet().size() - 1) {
                conditionBuilder.append("&");
            }
            count++;
        }
        return conditionBuilder.toString();
    }
}
