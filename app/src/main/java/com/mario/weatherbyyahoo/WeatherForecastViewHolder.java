package com.mario.weatherbyyahoo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mario.weatherbyyahoo.model.Forecast;

public class WeatherForecastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // Hold a reference to all the views
    private final TextView mDateView;
    private final TextView mWeatherView;
    private final TextView mLowView;
    private final TextView mHighView;

    WeatherForecastViewHolder(final View itemView) {
        super(itemView);

        // Set the listener
        itemView.setOnClickListener(this);

        // Hold all the views
        mDateView = (TextView) itemView.findViewById(R.id.forecast_date);
        mWeatherView = (TextView) itemView.findViewById(R.id.forecast_weather);
        mLowView = (TextView) itemView.findViewById(R.id.forecast_low);
        mHighView = (TextView) itemView.findViewById(R.id.forecast_high);
    }

    void bindView(final Forecast forecast) {
        final Context context = itemView.getContext();

        mDateView.setText(context.getString(R.string.yahoo_date_format, forecast.getDay(), forecast.getDate()));
        mWeatherView.setText(forecast.getText());
        mLowView.setText(context.getString(R.string.degree_weather_format, forecast.getLow()));
        mHighView.setText(context.getString(R.string.degree_weather_format, forecast.getHigh()));
    }

    @Override
    public void onClick(final View v) {
        // TODO - Show more weather details on individual day basis.
    }
}
