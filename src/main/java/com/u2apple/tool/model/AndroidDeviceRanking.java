package com.u2apple.tool.model;

public class AndroidDeviceRanking extends AndroidDevice {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public AndroidDeviceRanking(String vid, String roProductModel, String roProductBrand, int count) {
        super(vid, roProductModel, roProductBrand);
        this.count = count;
    }

    public AndroidDeviceRanking(AndroidDevice androidDevice) {
        super(androidDevice.getVid(), androidDevice.getRoProductModel(), androidDevice.getRoProductBrand(),
                androidDevice.getProductId());
    }

    public AndroidDeviceRanking() {

    }

    @Override
    public String toString() {
        return "AndroidDeviceRanking [count=" + count + ", vid=" + getVid() + ", roProductModel=" + getRoProductModel()
                + ", roProductBrand=" + getRoProductBrand() + "]";
    }

}
