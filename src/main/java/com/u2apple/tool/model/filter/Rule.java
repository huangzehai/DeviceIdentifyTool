/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.model.filter;

import java.util.Arrays;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adam
 */
@XmlRootElement(name = "Rule")
@XmlAccessorType(XmlAccessType.FIELD)
public class Rule {
     @XmlElement(name = "RuleItem")
    private RuleItem[] ruleItems;

    public RuleItem[] getRuleItems() {
        return ruleItems;
    }

    public void setRuleItems(RuleItem[] ruleItems) {
        this.ruleItems = ruleItems;
    }

    @Override
    public String toString() {
        return "Rule{" + "ruleItems=" + Arrays.toString(ruleItems) + '}';
    }
    
}
