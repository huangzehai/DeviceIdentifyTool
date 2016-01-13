package com.shuame.wandoujia.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

@XmlType(name = "Device", namespace = "http://www.wandoujia.com/schema/1.0.0")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Device")
public class Device {

    /**  */
    @XmlElement(name = "ProductId")
    private String _productId;

    /**  */
     @XmlElement(name = "Manufactory")
//    @XmlTransient
    private String _manufactory;

    /**  */
    @XmlElement(name = "Brand")
    private String _brand;

    /**  */
    @XmlElement(name = "Product")
    private String _product;

    /**  */
    @XmlElement(name = "Alias")
    private String _alias;

    /**  */
    @XmlElement(name = "Support")
    // @XmlTransient
    private String _support;

    /**  */
    @XmlElement(name = "Type")
    // @XmlTransient
    private Integer _type;

    /**  */
    @XmlElement(name = "SmallIconPath")
    private String _smallIconPath;

    /**  */
    @XmlElement(name = "LargeIconPath")
    private String _largeIconPath;

    /** 客户端款图的md5值 */
    @XmlElement(name = "LargeIconPathMd5")
    private String _largeIconPathMd5;

    /**  */
    // @XmlElement(name = "Screen")
    @XmlTransient
    private String _screen;

    /**  */
    @XmlElement(name = "Rotate")
    private Integer _rotate;

    private String englishBrand;
    private String englishProduct;
    private String englishAlias;
    
    public Device() {
    }

    @JsonProperty("ProductId")
    public String getProductId() {
        return _productId;
    }

    public void setProductId(String productId) {
        _productId = productId;
    }

    @JsonProperty("Manufactory")
    public String getManufactory() {
        return _manufactory;
    }

    public void setManufactory(String manufactory) {
        _manufactory = manufactory;
    }

    @JsonProperty("Brand")
    public String getBrand() {
        return _brand;
    }

    public void setBrand(String brand) {
        _brand = brand;
    }

    @JsonProperty("Product")
    public String getProduct() {
        return _product;
    }

    public void setProduct(String product) {
        _product = product;
    }

    @JsonProperty("Alias")
    public String getAlias() {
        return _alias;
    }

    public void setAlias(String alias) {
        _alias = alias;
    }

    @JsonProperty("Support")
    public String getSupport() {
        return _support;
    }

    public void setSupport(String support) {
        _support = support;
    }

    @JsonProperty("Type")
    public Integer getType() {
        return _type;
    }

    public void setType(Integer type) {
        _type = type;
    }

    @JsonProperty("SmallIconPath")
    public String getSmallIconPath() {
        return _smallIconPath;
    }

    public void setSmallIconPath(String smallIconPath) {
        _smallIconPath = smallIconPath;
    }

    @JsonProperty("LargeIconPath")
    public String getLargeIconPath() {
        return _largeIconPath;
    }

    public void setLargeIconPath(String largeIconPath) {
        _largeIconPath = largeIconPath;
    }

    @JsonProperty("Screen")
    public String getScreen() {
        return _screen;
    }

    public void setScreen(String screen) {
        _screen = screen;
    }

    public Integer getRotate() {
        return _rotate;
    }

    public void setRotate(Integer rotate) {
        _rotate = rotate;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public String getLargeIconPathMd5() {
        return _largeIconPathMd5;
    }

    public void setLargeIconPathMd5(String largeIconPathMd5) {
        _largeIconPathMd5 = largeIconPathMd5;
    }

    public String getEnglishBrand() {
        return englishBrand;
    }

    public void setEnglishBrand(String englishBrand) {
        this.englishBrand = englishBrand;
    }

    public String getEnglishProduct() {
        return englishProduct;
    }

    public void setEnglishProduct(String englishProduct) {
        this.englishProduct = englishProduct;
    }

    public String getEnglishAlias() {
        return englishAlias;
    }

    public void setEnglishAlias(String englishAlias) {
        this.englishAlias = englishAlias;
    }

}
