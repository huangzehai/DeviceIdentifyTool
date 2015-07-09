package com.u2apple.tool.model;

public class PartitionRanking extends Ranking {

    private String partition;

    public PartitionRanking(String partition, int count) {
        super(count);
        this.partition = partition;
    }

    public String getPartition() {
        return partition;
    }

    public void setPartition(String partition, int count) {
        this.partition = partition;
    }

    @Override
    public String toString() {
        return "PartitionRanking [partition=" + partition + "]" + ", count=" + getCount() + "]";
    }

}
