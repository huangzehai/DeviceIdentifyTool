package com.u2apple.tool.model;

import org.apache.commons.lang3.StringUtils;

public class DeviceTestCase {

    private String productId;
    private String vid;
    private String roProductModel;
    private String methodName;
    private String resolution;
    private Partitions partitions;
    private String roProductBrand;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
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

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Partitions getPartitions() {
        return partitions;
    }

    public void setPartitions(Partitions partitions) {
        this.partitions = partitions;
    }

    public String getRoProductBrand() {
        return roProductBrand;
    }

    public void setRoProductBrand(String roProductBrand) {
        this.roProductBrand = roProductBrand;
    }

    public DeviceTestCase(String productId, String vid, String roProductModel) {
        super();
        this.productId = productId;
        this.vid = vid;
        this.roProductModel = roProductModel;
        methodName = getMethodNameByProductId(productId);
    }

    public DeviceTestCase(String productId, String vid, String roProductModel, String roProductBrand) {
        super();
        this.productId = productId;
        this.vid = vid;
        this.roProductModel = roProductModel;
        this.roProductBrand = roProductBrand;
        methodName = getMethodNameByProductId(productId);
    }

    private String getMethodNameByProductId(String productId) {
        String methodName;
        if (StringUtils.isNotBlank(productId)) {
            methodName = productId.replace("-", "_");

        } else {
            methodName = null;
        }
        return methodName;
    }

}
