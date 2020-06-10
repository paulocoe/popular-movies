package com.example.android.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public final class NetworkUtils {

    private static final Resources resources = Resources.getSystem();

    private static final String MOVIES_URL = "https://api.themoviedb.org/3/movie";
    //Provide your api key here

    private static final String LANGUAGE = "en-US";
    private static final String API_KEY_PARAM = "api_key";
    private static final String LANGUAGE_PARAM = "language";
    private static final String PAGE_PARAM = "page";

    public static URL buildUrl(String sortOption, int page) {
        Uri builtUri = Uri.parse(MOVIES_URL).buildUpon()
                .appendPath(sortOption)
                .appendQueryParameter(API_KEY_PARAM, resources.getString(R.string.moviesDBApiKey))
                .appendQueryParameter(LANGUAGE_PARAM, LANGUAGE)
                .appendQueryParameter(PAGE_PARAM, String.valueOf(page))
                .build();

        try {
            return new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        InputStream stream = urlConnection.getInputStream();
        Scanner scanner = new Scanner(stream);
        scanner.useDelimiter("\\A");

        try {
            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
