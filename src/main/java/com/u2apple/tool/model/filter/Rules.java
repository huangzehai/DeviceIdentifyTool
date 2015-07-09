/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.model.filter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adam
 */
@XmlRootElement(name = "Rules")
@XmlAccessorType(XmlAccessType.FIELD)
public class Rules {

    @XmlElement(name = "Rule")
    private Rule[] rules;

    public Rule[] getRules() {
        return rules;
    }

    public void setRules(Rule[] rules) {
        this.rules = rules;
    }

    @Override
    public String toString() {
        return "Rules{" + "rules=" + rules + '}';
    }
}
