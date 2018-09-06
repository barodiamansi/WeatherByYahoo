package com.mario.weatherbyyahoo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherChannel {
    @SerializedName("item")
    @Expose
    private ForecastItem mItem;

    @SerializedName("location")
    @Expose
    private Location mLocation;

    public ForecastItem getItem() {
        return mItem;
    }

    public void setItem(final ForecastItem item) {
        mItem = item;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(final Location location) {
        mLocation = location;
    }
}
