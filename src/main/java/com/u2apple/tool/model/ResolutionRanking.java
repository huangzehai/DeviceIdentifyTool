package com.u2apple.tool.model;

public class ResolutionRanking extends Ranking {
    private String resolution;

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public ResolutionRanking(String resolution, int count) {
        super(count);
        this.resolution = resolution;
    }

    @Override
    public String toString() {
        return "ResolutionRanking [resolution=" + resolution + ", count=" + getCount() + "]";
    }

}
