package com.example.devsmar.dailynews.Service;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;

import com.example.devsmar.dailynews.Model.NewsFeed;

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

/**
 * Created by deepanshu on 2/4/17.
 */

public class NewsFeedServiceImpl extends AsyncTaskLoader<List<NewsFeed>> {

    private final static String LOG_TAG = NewsFeedServiceImpl.class.getSimpleName();

    public NewsFeedServiceImpl(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<NewsFeed> loadInBackground() {
        List<NewsFeed> listOfNews;
        String reponseStr = makeHttpRequest(getUri());
        listOfNews = parseJson(reponseStr);
        return listOfNews;
    }

    private ArrayList<NewsFeed> parseJson(String bookJsonStr) {
        ArrayList<NewsFeed> bookArrayList = new ArrayList<>();

        try {
            JSONObject newsJsonObject = new JSONObject(bookJsonStr);
            JSONArray articles = newsJsonObject.getJSONArray("articles");


            if (null != articles && articles.length() > 0) {

                for (int i = 0; i < articles.length(); i++) {

                    JSONObject itemJsonObject = articles.getJSONObject(i);

                    bookArrayList.add(new NewsFeed(
                            itemJsonObject.getString("title"),
                            itemJsonObject.getString("author"),
                            itemJsonObject.getString("url"),
                            itemJsonObject.getString("publishedAt"),
                            itemJsonObject.getString("urlToImage")
                    ));
                }
            } else {
                bookArrayList = null;
            }
        } catch (JSONException e) {
            Log.d(LOG_TAG, e.toString());
        }
        return bookArrayList;
    }

    private String makeHttpRequest(URL url) {
        String bookJsonStr = "";

        if (url == null) {
            return bookJsonStr;
        }

        Log.i("fuck",url.toString());

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            // Create the request to Google Books API, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                if (null == inputStream) {
                    // Nothing to do.
                    bookJsonStr = null;
                }

                if (inputStream != null) {
                    reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line).append("\n");
                    }
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    bookJsonStr = null;
                }
                bookJsonStr = buffer.toString();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the book list data, there's no point in attempting
            // to parse it.
            bookJsonStr = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return bookJsonStr;
    }

    private URL getUri() {
        try {
            return new URL(getUriBuilder());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getUriBuilder() {
        return new Uri.Builder()
                .scheme("http")
                .encodedAuthority("newsapi.org/v2")
                .appendPath("top-headlines")
                .appendQueryParameter("country", "us")
                .appendQueryParameter("apiKey", "8dca7dea475e41e49518b2c61131e118")
                .build()
                .toString();
    }
}
