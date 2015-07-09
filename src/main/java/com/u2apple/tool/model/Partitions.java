package com.u2apple.tool.model;

public class Partitions {
    private Partition systemPartition;
    private Partition cachePartition;
    private Partition dataPartition;

    public Partition getSystemPartition() {
        return systemPartition;
    }

    public void setSystemPartition(Partition systemPartition) {
        this.systemPartition = systemPartition;
    }

    public Partition getCachePartition() {
        return cachePartition;
    }

    public void setCachePartition(Partition cachePartition) {
        this.cachePartition = cachePartition;
    }

    public Partition getDataPartition() {
        return dataPartition;
    }

    public void setDataPartition(Partition dataPartition) {
        this.dataPartition = dataPartition;
    }

}
