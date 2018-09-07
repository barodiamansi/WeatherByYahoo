package com.mario.weatherbyyahoo.service;

import com.mario.weatherbyyahoo.model.ServiceResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public final class ServiceHelper {

    private static final String DATA_FORMAT = "json";

    private static final String BASE_URL = "https://query.yahooapis.com";
    private static final String QUERY_BASE = "v1/public/yql";
    private static final String QUERY = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")";
    // TODO - Let the default location be the geographic location where the user is present.
    private static final String DEFAULT_LOCATION = "overland park, ks";

    private static final Retrofit mRetrofit;

    // Initializing retrofit
    static {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * A call to get the weather. Defaults to "overland park, ks" when no location specified.
     *
     * @param callback the callback to give the response to
     * @param location the string value of the location for which we wish to get the weather data.
     */
    public static void getWeatherData(final Callback<ServiceResponse> callback, final String location) {
        final WeatherForecastService service = mRetrofit.create(WeatherForecastService.class);
        final Call<ServiceResponse> weather = service.getWeather(String.format(QUERY, location != null ? location : DEFAULT_LOCATION), DATA_FORMAT);
        weather.enqueue(callback);
    }

    /**
     * Makes a RESTful API request with Retrofit
     */
    interface WeatherForecastService {
        @GET(QUERY_BASE)
        Call<ServiceResponse> getWeather(@Query("q") String query, @Query("format") String format);
    }
}
