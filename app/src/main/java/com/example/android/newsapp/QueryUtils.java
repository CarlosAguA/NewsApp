package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.description;
import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Paviliondm4 on 2/26/2017.
 */

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getName();

    private QueryUtils (){
    }

    public static List<Article> fetchArticleData(String requestUrl) {
        
        URL url = createUrl(requestUrl); //Instantiate URL object and execute createURL(@String) method

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            // TODO Handle the IOException
            Log.e (LOG_TAG , "It was not possible to connect to the server", e) ;
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Article> articles = extractFeatureFromJson(jsonResponse);

        // Return the list of articles
        return articles;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200 ) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            // TODO: Handle the exception
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e ) ;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return an {@link Article} object by parsing out information
     * about the first earthquake from the input earthquakeJSON string.
     */
    private static List<Article> extractFeatureFromJson(String articlesJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(articlesJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Article> articles = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(articlesJSON);

            // Extract the JSONArray associated with the key called "response",
            // which represents a list of features (or articles).
            JSONObject responseObject = baseJsonResponse.getJSONObject("response");
            JSONArray articlesArray = responseObject.getJSONArray("results") ;

            // If there are results in the features array
            if (articlesArray.length() > 0) {

                // For each article in the articlesArray, create an {@link Article} object
                for (int i = 0 ; i < articlesArray.length() ; i++) {

                    // Get a single article at position i within the list of articles
                    JSONObject articleObject = articlesArray.getJSONObject(i);

                    String section= "";
                    if(articleObject.has("sectionName")) {
                        section = articleObject.getString("sectionName");
                    }

                    String date = "";
                    if(articleObject.has("webPublicationDate")) {
                        date = articleObject.getString("webPublicationDate");
                    }

                    String title = "" ;
                    if(articleObject.has("webTitle")) {
                        title = articleObject.getString("webTitle");
                    }

                    String url = "";
                    if(articleObject.has("webUrl")) {
                        url = articleObject.getString("webUrl");
                    }


                    // Create a new {@link Article} object with the title, author, description,
                    // and url from the JSON response.
                    Article article = new Article (section, title, date , url );

                    // Add the new {@link Article} to the list.
                    articles.add(article);
                }
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            Log.e(LOG_TAG, "Problem parsing the article JSON results", e);
        }

        //Return the list of articles
        return articles;
    }

}
