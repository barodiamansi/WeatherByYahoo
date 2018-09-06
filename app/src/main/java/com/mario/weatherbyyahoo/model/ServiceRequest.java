package com.mario.weatherbyyahoo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceRequest {
    @SerializedName("results")
    @Expose
    private ServiceResult mResult;

    public ServiceResult getResult() {
        return mResult;
    }

    public void setResult(ServiceResult result) {
        mResult = result;
    }
}