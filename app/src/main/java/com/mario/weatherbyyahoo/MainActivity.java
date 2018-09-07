package com.mario.weatherbyyahoo;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mario.weatherbyyahoo.model.ForecastItem;
import com.mario.weatherbyyahoo.model.ServiceResponse;
import com.mario.weatherbyyahoo.model.WeatherChannel;
import com.mario.weatherbyyahoo.service.ServiceHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<ServiceResponse>, TextView.OnEditorActionListener {

    private static final String CURRENT_LOCATION = "CURRENT_LOCATION";

    private WeatherForecastRecyclerAdapter mRecyclerAdapter;
    private EditText mEditTextView;
    private Button searchBtn;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // Allow user to specify location
        mEditTextView = (EditText) toolbar.findViewById(R.id.main_search);
        searchBtn = (Button) toolbar.findViewById(R.id.searchBtn);
        searchBtn.setVisibility(View.VISIBLE);
        mEditTextView.setOnEditorActionListener(this);
        if (savedInstanceState != null && savedInstanceState.containsKey(CURRENT_LOCATION)) {
            mEditTextView.setText(savedInstanceState.getString(CURRENT_LOCATION));
        }

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditorAction(mEditTextView, EditorInfo.IME_ACTION_SEARCH, null);
            }
        });

        // Create recycler adapter
        mRecyclerAdapter = new WeatherForecastRecyclerAdapter(null);

        // Set up recycler view
        final RecyclerView recycler = (RecyclerView) findViewById(R.id.main_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(mRecyclerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Clear reference to adapter
        mRecyclerAdapter = null;

        if (mEditTextView != null) {
            mEditTextView.setOnEditorActionListener(null);
            mEditTextView = null;
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState, final PersistableBundle outPersistentState) {
        if (mEditTextView != null) {
            outState.putString(CURRENT_LOCATION, mEditTextView.getText().toString());
        }
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Load weather whenever we resume to get the most up to date forecast
        if (mEditTextView != null && mEditTextView.getText().length() > 0) {
            renderWeatherData(mEditTextView.getText().toString());
        } else {
            renderWeatherData(null);
        }
    }

    @Override
    public void onResponse(@NonNull final Call<ServiceResponse> call, @NonNull final Response<ServiceResponse> response) {
        // Load weather views
        final ServiceResponse yahooResponse = response.body();
        if (yahooResponse != null) {
            try {
                final WeatherChannel channel = yahooResponse.getQuery().getResult().getChannel();
                final ForecastItem item = channel.getItem();

                // Show location in title bar edit text and update list
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                if (mEditTextView != null) {
                    mEditTextView.setText(channel.getLocation().toString());
                }
                if (mRecyclerAdapter != null) {
                    mRecyclerAdapter.updateForecasts(item.getForecasts());
                }
            } catch (final Exception e) {
                // We had an error parsing somewhere down the line, so show that we failed to load
                onFailure(call, e);
            }
        } else {
            onFailure(call, new Throwable("Invalid response from yahoo: " + response));
        }
    }

    @Override
    public void onFailure(@NonNull final Call<ServiceResponse> call, @NonNull final Throwable t) {
        if (mRecyclerAdapter != null) {
            mRecyclerAdapter.updateForecasts(null); // Clear the forecasts so we show the right thing to the user
        }
    }

    @Override
    public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
        // When users hits search, find the new location
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            renderWeatherData(v.getText().toString());

            // Close the keyboard
            final InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            // Clear focus
            if (mEditTextView != null) {
                mEditTextView.clearFocus();
            }
            return true;
        }
        return false;
    }

    /**
     * Load the weather data and tell the adapter to update its state
     *
     * @param location the location to load weather for, can be null
     */
    private void renderWeatherData(@Nullable final String location) {
        if (mRecyclerAdapter != null) {
            mRecyclerAdapter.setIsLoading(true);
        }
        ServiceHelper.getWeatherData(this, location);
    }
}
