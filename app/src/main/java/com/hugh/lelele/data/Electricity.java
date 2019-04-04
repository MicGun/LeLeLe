package com.hugh.lelele.data;

public class Electricity {

    private String mTime;
    private String mPrice;
    private String mScale;
    private String mScaleLast;
    private String mTotalConsumption;

    public Electricity() {
        mTime = "";
        mPrice = "";
        mScale = "";
        mScaleLast = "";
        mTotalConsumption = "";
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getScale() {
        return mScale;
    }

    public void setScale(String scale) {
        mScale = scale;
    }

    public String getTotalConsumption() {
        return mTotalConsumption;
    }

    public void setTotalConsumption(String totalConsumption) {
        mTotalConsumption = totalConsumption;
    }

    public String getScaleLast() {
        return mScaleLast;
    }

    public void setScaleLast(String scaleLast) {
        mScaleLast = scaleLast;
    }
}
