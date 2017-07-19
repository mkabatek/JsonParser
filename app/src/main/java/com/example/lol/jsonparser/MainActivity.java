package com.example.lol.jsonparser;

/*
 * MainActivity.java
 * Updated by lol on 7/18/17.
 * Author: Michael Kabatek
 * A simple app to download json data and display it in a RecyclerView
 * downloads images, titles, and authors from an array of json data
 *
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private final String JSON_URL = "http://de-coding-test.s3.amazonaws.com/books.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup SwipeRefreshLayout from its id
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        //Grab the RecyclerView from its id
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new CustomAdapter(null));

        //Download the JSON using a new async task
        new DownloadJSONTask().execute(JSON_URL);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRefresh() {
        recyclerView.setAdapter(new CustomAdapter(null));
        swipeRefreshLayout.setRefreshing(true);
        new DownloadJSONTask().execute(JSON_URL);
    }

    //Async Task to download json data array
    private class DownloadJSONTask extends AsyncTask<String, Void, AsyncTaskResult<ArrayList<Book>>> {

        //This function downloads the JSON data
        protected AsyncTaskResult<ArrayList<Book>> doInBackground(String... urls) {
            try {
                //Use helper method to download JSON and convert to string.
                return new AsyncTaskResult<>(Book.fromJson(new JSONArray(readInputStream(new java.net.URL(urls[0]).openStream()))));
            } catch (IOException | JSONException e) {
                //Something wrong with URL or JSON
                e.printStackTrace();
                return new AsyncTaskResult<>(e);
            }

        }

        //This function executes when JSON download finished
        protected void onPostExecute(final AsyncTaskResult<ArrayList<Book>> result) {

            //Handel AsyncTaskResult
            if (result.getError() != null) {
                //Display exception from background task
                recyclerView.setAdapter(new CustomAdapter(null));
                Toast.makeText(MainActivity.this, result.getError().getMessage(), Toast.LENGTH_LONG).show();
            } else if (isCancelled()) {
                //Async task canceled
                Toast.makeText(MainActivity.this, this.getClass().toString() + " Canceled", Toast.LENGTH_LONG).show();
            } else {
                //Result successful
                //Set RecyclerView adaptor to Books ArrayList
                recyclerView.setAdapter(new CustomAdapter(result.getResult()));
            }
            swipeRefreshLayout.setRefreshing(false);

        }
    }

    //Helper method to read the http input stream and convert to a string
    private String readInputStream(InputStream entityResponse) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = entityResponse.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        return byteArrayOutputStream.toString();
    }


}

