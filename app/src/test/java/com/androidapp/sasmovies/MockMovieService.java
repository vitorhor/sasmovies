package com.androidapp.sasmovies;

import com.androidapp.sasmovies.api.MoviesServiceApi;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MockMovieService implements MoviesServiceApi {

    @Override
    public void getPopularMovies(JsonHttpResponseHandler handler) {
        JSONObject jsonObject = new JSONObject();
        handler.onSuccess(200, new Header[13], jsonObject);
    }

    @Override
    public void getFavorites(JsonHttpResponseHandler handler) {

    }

    @Override
    public void getMovieDetail(int id, JsonHttpResponseHandler handler) {
        JSONObject jsonObject = new JSONObject();
        handler.onSuccess(200, new Header[13], jsonObject);
    }

    @Override
    public void markAsFavorite(JSONObject params, JsonHttpResponseHandler handler) {

    }

}
