package com.example.android.newsapp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.R.attr.data;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    //**********************************************************************************************
    //                          GLOBAL VARIABLES
    //**********************************************************************************************
    /** URL to query the Google Books for books information */
    private static final String THE_GUARDIAN_REQUEST_URL =
           // "https://content.guardianapis.com/search?q=technology&from-date=2017-01-14&api-key=test";
            "https://content.guardianapis.com/search?q=";

    public static final String LOG_TAG = NewsActivity.class.getName();

    /* Id for identifying the loader */
    public static final int ARTICLE_LOADER_ID = 1 ;

    ArticleAdapter mAdapter ; //Global mAdapter that modifies on each bookListUpdating

    //**********************************************************************************************
    //                         LOADER   METHODS
    //**********************************************************************************************

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Article}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (articles != null && !articles.isEmpty()) {
            mAdapter.addAll(articles);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        mAdapter.clear();
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {

        /*
           Create an instance object of SharedPreferences File for retrieving key-value, storing
           the value in a string, and appending the value to the search query of the GUARDIAN URL
        */
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Retrieves the category key from sharedPrefs instance.
        String category= sharedPrefs.getString(
                getString(R.string.settings_category_key), getString(R.string.settings_category_default));

        //Retrieves the order-by key from sharedPrefs instance.
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key), getString(R.string.settings_order_by_default)) ;

        Uri baseUri = Uri.parse(THE_GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("", category);
        uriBuilder.appendQueryParameter("from-date","2017-01-14"); // Quitar mas adelante y ver como acomodar por fecha
        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("api-key", "test");

        Log.i(LOG_TAG, uriBuilder.toString().replace("&=","") ) ;
        return new ArticleLoader(this, uriBuilder.toString().replace("&=",""));
    }

    //**********************************************************************************************
    //                         onCreate()   method
    //**********************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Find a reference to the {@link ListView} in the layout
        ListView articleListView = (ListView) findViewById(R.id.list) ;

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new ArticleAdapter(this, new ArrayList<Article>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        articleListView.setAdapter(mAdapter);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        //When the activity is created or device is rotated, check if the loader with the ARTICLE_LOADER_ID exists.
        // If exists, load the information from the ARTICLE_LOADER_ID loader.
        if(loaderManager.getLoader(ARTICLE_LOADER_ID) != null ){
            loaderManager.initLoader(ARTICLE_LOADER_ID, null, NewsActivity.this);
        }

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(ARTICLE_LOADER_ID,null, NewsActivity.this);
        Log.i(LOG_TAG, "TEST  : initloader() ");

        actualDate();
    }

    //**********************************************************************************************
    //                        GLOBAL  METHODS
    //**********************************************************************************************

    private void actualDate() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd ");
        Log.i(LOG_TAG, "TEST: " + mdformat.format(calendar.getTime() ) );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
