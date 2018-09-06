package com.mario.weatherbyyahoo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceResult {
    @SerializedName("channel")
    @Expose
    private WeatherChannel mChannel;

    public WeatherChannel getChannel() {
        return mChannel;
    }

    public void setChannel(WeatherChannel channel) {
        mChannel = channel;
    }
}
