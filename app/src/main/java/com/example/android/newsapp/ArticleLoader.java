package com.example.android.newsapp;

/**
 * Created by Paviliondm4 on 2/26/2017.
 */


import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of articles by using an AsyncTaskLoader to perform the
 * network request to the given URL.
 */
public class ArticleLoader extends AsyncTaskLoader <List<Article>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = ArticleLoader.class.getName();
    /**
     * Query URL
     */
    private String mUrl;

    /*
     * Constructs a new {@link ArticleLoader}
     *@param context of the activity
     *@param url to load data from
     */
    public ArticleLoader(Context context, String url) {

        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Article> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of articles
        List<Article> articles = QueryUtils.fetchArticleData(mUrl);
        return articles;
    }

}

