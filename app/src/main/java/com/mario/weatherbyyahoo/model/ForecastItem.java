package com.mario.weatherbyyahoo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastItem {
    @SerializedName("forecast")
    @Expose
    private List<Forecast> mForecasts;

    @SerializedName("title")
    @Expose
    private String mTitle;

    public List<Forecast> getForecasts() {
        return mForecasts;
    }

    public void setForecasts(final List<Forecast> forecasts) {
        mForecasts = forecasts;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(final String title) {
        mTitle = title;
    }
}
