package com.u2apple.tool.model;

public class KnockOffConfig {
    private String resolution;
    private Partitions partitions;
    private String productId;
    private String knockOffProductId;

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getKnockOffProductId() {
        return knockOffProductId;
    }

    public void setKnockOffProductId(String knockOffProductId) {
        this.knockOffProductId = knockOffProductId;
    }
}
