package com.androidapp.sasmovies.api;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public interface MoviesServiceApi {

    void getPopularMovies(JsonHttpResponseHandler handler);

    void getFavorites(JsonHttpResponseHandler handler);

    void getMovieDetail(int id, JsonHttpResponseHandler handler);

    void markAsFavorite(JSONObject params, JsonHttpResponseHandler handler);

}
