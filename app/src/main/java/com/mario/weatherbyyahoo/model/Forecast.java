package com.mario.weatherbyyahoo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Forecast {
    @SerializedName("date")
    @Expose
    private String mDate;

    @SerializedName("day")
    @Expose
    private String mDay;

    @SerializedName("high")
    @Expose
    private String mHigh;

    @SerializedName("low")
    @Expose
    private String mLow;

    @SerializedName("text")
    @Expose
    private String mText;

    public String getDate() {
        return mDate;
    }

    public void setDate(final String date) {
        mDate = date;
    }

    public String getDay() {
        return mDay;
    }

    public void setDay(final String day) {
        mDay = day;
    }

    public String getHigh() {
        return mHigh;
    }

    public void setHigh(final String high) {
        mHigh = high;
    }

    public String getLow() {
        return mLow;
    }

    public void setLow(final String low) {
        mLow = low;
    }

    public String getText() {
        return mText;
    }

    public void setText(final String text) {
        mText = text;
    }
}