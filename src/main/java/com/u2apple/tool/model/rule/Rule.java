/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.model.rule;

import java.util.Arrays;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adam
 */
@XmlRootElement(name = "Rule")
@XmlAccessorType(XmlAccessType.FIELD)
public class Rule {

    @XmlElementWrapper(name = "Brands")
    @XmlElement(name = "Brand")
    private String[] brands;

    @XmlElementWrapper(name = "Vids")
    @XmlElement(name = "Vid")
    private String[] vids;

    @XmlElementWrapper(name = "Patterns")
    @XmlElement(name = "Pattern")
    private String[] patterns;

    public String[] getBrands() {
        return brands;
    }

    public void setBrands(String[] brands) {
        this.brands = brands;
    }

    public String[] getVids() {
        return vids;
    }

    public void setVids(String[] vids) {
        this.vids = vids;
    }

    public String[] getPatterns() {
        return patterns;
    }

    public void setPatterns(String[] patterns) {
        this.patterns = patterns;
    }

    @Override
    public String toString() {
        return "Rule{" + "brand=" + Arrays.toString(brands) + ", vids=" + Arrays.toString(vids) + ", patterns=" + Arrays.toString(patterns) + '}';
    }
}
