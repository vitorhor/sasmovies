package com.androidapp.sasmovies.api;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class MoviesService {

    private final BaseApiService service;

    public MoviesService(BaseApiService service) {
        this.service = service;
    }

    public void getPopularMovies(JsonHttpResponseHandler handler) {
        service.request().to( "/movie/popular" ).authGet().ptbr().get(handler);
    }

    public void getFavorites(JsonHttpResponseHandler handler) {
        service.request().to( "/account/%7Baccount_id%7D/favorite/movies" ).authGet().addAuthSession().get(handler);
    }

    public void getMovieDetail(int id, JsonHttpResponseHandler handler) {
        service.request().to( "/movie/" + id ).authGet().ptbr().get(handler);
    }

    public void markAsFavorite(JSONObject params, JsonHttpResponseHandler handler) {
        service.request().to( "/account/%7Baccount_id%7D/favorite" ).authGet().addAuthSession().post(params, handler);
    }

}
