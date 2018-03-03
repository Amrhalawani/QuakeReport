package com.example.android.quakereport;

import android.content.AsyncTaskLoader; // lazem ykon da mesh
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Amrhalawani on 12/12/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    String mUrl;

    public EarthquakeLoader(Context context, String url) { // when use
        super(context);
        mUrl = url;
        Log.e("TAG","EarthquakeLoader initialized");
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.e("TAG","EarthquakeLoader onStartLoading forceLoad");
    }


    @Override
    public List<Earthquake> loadInBackground() {

        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Earthquake> earthquakes = QueryUtils.fetchdata(mUrl);
        Log.e("TAG","EarthquakeLoader loadInBackground ");
        return earthquakes;
    }

}
