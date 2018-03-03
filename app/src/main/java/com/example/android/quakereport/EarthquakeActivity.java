/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    private static final int EARTHQUAKE_LOADER_ID = 1;
    ListView earthquakeListView;
    List<Earthquake> earthquakes = new ArrayList<>();
    EarthquakeAdapter mAdapter;
    TextView mEmptyStatView;
    ImageView imageView;
    ProgressBar progressBar;
    private final String Usgs_url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        progressBar = findViewById(R.id.progress_bar);


        // Find arraylistObject reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(R.id.list);
        // Create arraylistObject new {@link ArrayAdapter} of earthquakes
        mAdapter = new EarthquakeAdapter(this, earthquakes);

        // Set the mAdapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);


        // earthquakes.add(new Earthquake(5.0, "dasdasdasda", 1513356476930L, "https://www.google.com.eg/"));
        //  Mytask mytask = new Mytask();
        //    mytask.execute(Usgs_url);
        whenItemClick();
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        mEmptyStatView = findViewById(R.id.emptyStateView);
        earthquakeListView.setEmptyView(mEmptyStatView);
        imageView = findViewById(R.id.image);
        //  earthquakeListView.setEmptyView(findViewById(R.id.emptyStateViewLayout));

    }


    void whenItemClick() {
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //ananyomes class
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Earthquake dataPosition = mAdapter.getItem(position);

                Toast.makeText(EarthquakeActivity.this, "Postion no :  " + position, Toast.LENGTH_SHORT).show();

//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(dataPosition.getUrl()));
//                startActivity(i);

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataPosition.getUrl()));

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }


    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        Log.e("TAG", "activity -- onCreateLoader called... and return new earth loader");

        return new EarthquakeLoader(this, Usgs_url);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakeList) {
        progressBar.setVisibility(View.INVISIBLE);
        // for empty state

        //   mEmptyStatView.setText("No Earthquakes Found");
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakeList != null && !earthquakeList.isEmpty()) {
            mAdapter.addAll(earthquakeList);
            imageView.setVisibility(View.INVISIBLE);

        } else if (earthquakeList.isEmpty()) {
            earthquakeListView.setEmptyView(findViewById(R.id.emptyStateViewLayout));
            mEmptyStatView.setText("No Earthquakes Found.");
        }
        Log.e("TAG", "activity -- onLoadFinished called...");
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
        Log.e("TAG", "activity -- onLoaderReset called...");
    }

    //--------------------------------------------------------------------------------------------------------------------

//    class Mytask extends AsyncTask<String, Void, List<Earthquake>> {
//
//        // we should not update the UI from doInBackground.
//        @Override
//        protected List<Earthquake> doInBackground(String... urls) {
//            try {
//
//                // Don't perform the request if there are no URLs, or the first URL is null.
//                if (urls.length < 1 || urls[0] == null) {
//                    Log.e("TAG", "return null cuz urls.length= " + urls.length + " or " + "url is null");
//                    return null;
//                }
//                earthquakes = QueryUtils.fetchdata(urls[0]);
//                Log.e("TAG", "return earthquakes well and the results is = " + earthquakes);
//
//                return earthquakes;
//
//            } catch (Exception e) {
//                Log.e("TAG", " doInBackground " + String.valueOf(e));
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(List<Earthquake> dataobject) {
//            super.onPostExecute(dataobject);
//            mAdapter.clear();
//            if (dataobject == null) {
//                Log.e("TAG", "List<Earthquake> dataobject = null");
//                return;
//            }
//            mAdapter.addAll(dataobject);
//            earthquakes.add(new Earthquake(5.0, "dasdasdasda", 1513356476930L, "https://www.google.com.eg/"));
//            mAdapter.notifyDataSetChanged();
//            //updateUi(earthquakes);
//        }
//    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TAG", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "onPause");
    }
}