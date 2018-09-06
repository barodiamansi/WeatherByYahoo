package com.mario.weatherbyyahoo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceResponse {
    @SerializedName("query")
    @Expose
    private ServiceRequest mQuery;

    public ServiceRequest getQuery() {
        return mQuery;
    }

    public void setQuery(ServiceRequest query) {
        mQuery = query;
    }
}

