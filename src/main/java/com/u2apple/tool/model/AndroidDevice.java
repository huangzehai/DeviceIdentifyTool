package com.u2apple.tool.model;

import com.u2apple.tool.annotation.Key;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class AndroidDevice {

    @XmlTransient
    private String macAddress;
    @Key("vid")
    @XmlElement(name = "vid")
    private String vid;
    @XmlTransient
    private String pid;
    @XmlTransient
    private String prot;
    @XmlTransient
    private String sn;
    @XmlTransient
    private String adbDevice;
    @Key("model")
    @XmlElement(name = "model")
    private String roProductModel;
    @Key("brand")
    @XmlElement(name = "brand")
    private String roProductBrand;
    @XmlTransient
    private String roProductDevice;
    @XmlTransient
    private String roProductBoard;
    @XmlTransient
    private String roProductManufacturer;
    @XmlTransient
    private String roHardware;
    @XmlTransient
    private String roBuildDisplayId;
    @XmlTransient
    private String customProps;
    @XmlTransient
    private String androidVersion;
    @XmlTransient
    private String createdAt;
    @XmlTransient
    private String identified;
    @XmlTransient
    private String productId;
    @XmlTransient
    private String resolution;
    @XmlTransient
    private String partitions;
    @XmlTransient
    private String cpuHardware;
    @XmlTransient
    private String returnProductId;
     @XmlTransient
    private String roProductName;
    @XmlTransient
    private String roBuildFingerprint;
    // Only for generate unit test case.
    @XmlTransient
    private String[] vids;

    public AndroidDevice(String vid, String roProductModel, String roProductBrand, String productId) {
        super();
        this.vid = vid;
        this.roProductModel = roProductModel;
        this.roProductBrand = roProductBrand;
        this.productId = productId;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getRoProductModel() {
        return roProductModel;
    }

    public void setRoProductModel(String roProductModel) {
        this.roProductModel = roProductModel;
    }

    public String getRoProductBrand() {
        return roProductBrand;
    }

    public void setRoProductBrand(String roProductBrand) {
        this.roProductBrand = roProductBrand;
    }

    public String getRoProductDevice() {
        return roProductDevice;
    }

    public void setRoProductDevice(String roProductDevice) {
        this.roProductDevice = roProductDevice;
    }

    public AndroidDevice(String vid, String roProductModel, String roProductBrand) {
        super();
        this.vid = vid;
        this.roProductModel = roProductModel;
        this.roProductBrand = roProductBrand;
    }

    public AndroidDevice() {

    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getPartitions() {
        return partitions;
    }

    public void setPartitions(String partitions) {
        this.partitions = partitions;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCpuHardware() {
        return cpuHardware;
    }

    public void setCpuHardware(String cpuHardware) {
        this.cpuHardware = cpuHardware;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProt() {
        return prot;
    }

    public void setProt(String prot) {
        this.prot = prot;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getAdbDevice() {
        return adbDevice;
    }

    public void setAdbDevice(String adbDevice) {
        this.adbDevice = adbDevice;
    }

    public String getRoProductBoard() {
        return roProductBoard;
    }

    public void setRoProductBoard(String roProductBoard) {
        this.roProductBoard = roProductBoard;
    }

    public String getRoProductManufacturer() {
        return roProductManufacturer;
    }

    public void setRoProductManufacturer(String roProductManufacturer) {
        this.roProductManufacturer = roProductManufacturer;
    }

    public String getRoHardware() {
        return roHardware;
    }

    public void setRoHardware(String roHardWare) {
        this.roHardware = roHardWare;
    }

    public String getRoBuildDisplayId() {
        return roBuildDisplayId;
    }

    public void setRoBuildDisplayId(String roBuildDisplayId) {
        this.roBuildDisplayId = roBuildDisplayId;
    }

    public String getCustomProps() {
        return customProps;
    }

    public void setCustomProps(String customProps) {
        this.customProps = customProps;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getIdentified() {
        return identified;
    }

    public void setIdentified(String identified) {
        this.identified = identified;
    }

    public String getReturnProductId() {
        return returnProductId;
    }

    public void setReturnProductId(String returnProductId) {
        this.returnProductId = returnProductId;
    }

    public String getRoProductName() {
        return roProductName;
    }

    public void setRoProductName(String roProductName) {
        this.roProductName = roProductName;
    }

    public String[] getVids() {
        return vids;
    }

    public void setVids(String[] vids) {
        this.vids = vids;
    }

    public String getRoBuildFingerprint() {
        return roBuildFingerprint;
    }

    public void setRoBuildFingerprint(String roBuildFingerprint) {
        this.roBuildFingerprint = roBuildFingerprint;
    }

    @Override
    public String toString() {
        return "AndroidDevice{" + "macAddress=" + macAddress + ", vid=" + vid + ", pid=" + pid + ", prot=" + prot + ", sn=" + sn + ", adbDevice=" + adbDevice + ", roProductModel=" + roProductModel + ", roProductBrand=" + roProductBrand + ", roProductDevice=" + roProductDevice + ", roProductBoard=" + roProductBoard + ", roProductManufacturer=" + roProductManufacturer + ", roHardware=" + roHardware + ", roBuildDisplayId=" + roBuildDisplayId + ", customProps=" + customProps + ", androidVersion=" + androidVersion + ", createdAt=" + createdAt + ", identified=" + identified + ", productId=" + productId + ", resolution=" + resolution + ", partitions=" + partitions + ", cpuHardware=" + cpuHardware + ", returnProductId=" + returnProductId + ", roProductName=" + roProductName + ", roBuildFingerprint=" + roBuildFingerprint + ", vids=" + vids + '}';
    }
}
