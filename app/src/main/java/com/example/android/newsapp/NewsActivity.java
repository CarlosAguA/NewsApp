package com.example.android.newsapp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
            "https://content.guardianapis.com/search?q=technology&from-date=2017-01-14&api-key=test";

    public static final String LOG_TAG = NewsActivity.class.getName();

    /* Id for identifying the loader */
    private static final int ARTICLE_LOADER_ID = 1 ;

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
        return new ArticleLoader(this, THE_GUARDIAN_REQUEST_URL);
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
        // If exists, load the information from the BOOK_LOADER_ID loader.
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

}
