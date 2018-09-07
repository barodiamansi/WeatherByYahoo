package com.mario.weatherbyyahoo;

import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import com.mario.weatherbyyahoo.model.Forecast;

public class WeatherForecastRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @IntDef({ViewType.NONE, ViewType.LOADING, ViewType.FORECAST})
    @Retention(RetentionPolicy.SOURCE)
    @interface ViewType {
        int NONE = 0;
        int LOADING = 1;
        int FORECAST = 2;
    }

    private boolean mIsLoading;
    private List<Forecast> mForecasts; // Keep track of our forecasts

    WeatherForecastRecyclerAdapter(final List<Forecast> forecasts) {
        super();
        updateForecasts(forecasts); // Copy over the forecasts
    }

    @Override
    public int getItemViewType(final int position) {
        // Check if we're loading and if the list is empty
        return mIsLoading ? ViewType.LOADING :
                mForecasts.isEmpty() ? ViewType.NONE : ViewType.FORECAST;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        RecyclerView.ViewHolder holder = null;

        // Initialize the holder to the right view holder type
        switch (i) {
            case ViewType.NONE: {
                holder = new NoDataView(inflater.inflate(R.layout.no_data_view_container, viewGroup, false));
                break;
            }
            case ViewType.LOADING: {
                holder = new NoDataView(inflater.inflate(R.layout.forecast_loader, viewGroup, false));
                break;
            }
            case ViewType.FORECAST: {
                holder = new WeatherForecastViewHolder(inflater.inflate(R.layout.days_forecast_view_container, viewGroup, false));
                break;
            }
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (getItemViewType(i)) {
            case ViewType.LOADING:
            case ViewType.NONE: {
                return; // Do nothing for an empty position
            }
            case ViewType.FORECAST: {
                if (mForecasts.size() <= i) {
                    return; // Bad forecast position
                }

                // Update the view holder for the forecast at this position
                ((WeatherForecastViewHolder) viewHolder).bindView(mForecasts.get(i));
                if (i%2 == 0) {
                    viewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {
                    viewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));

                }
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        // We want to always have at least 1 item (for an empty/loading state)
        return Math.max(mForecasts.size(), 1);
    }

    /**
     * Set the state for whether data is loading or not
     *
     * @param isLoading true if waiting for content to load, false if data has been loaded
     */
    void setIsLoading(final boolean isLoading) {
        mIsLoading = isLoading;
        notifyDataSetChanged();
    }

    /**
     * Update this adapter with the forecasts provided
     * When null is passed in, the data is cleared and a placeholder is shown to the user
     * (saying that there is no data).
     *
     * Also updates isLoading to false.
     *
     * @param updatedForecasts the list of forecasts to use
     */
    void updateForecasts(final List<Forecast> updatedForecasts) {
        mForecasts = updatedForecasts == null ? new ArrayList<Forecast>(0) : new ArrayList<>(updatedForecasts);
        setIsLoading(false);
    }
}
