package com.u2apple.tool.model;

public class Ranking {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Ranking(int count) {
        super();
        this.count = count;
    }

    @Override
    public String toString() {
        return "Ranking [count=" + count + "]";
    }

}
