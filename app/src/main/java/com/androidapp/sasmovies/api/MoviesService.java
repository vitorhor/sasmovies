package com.androidapp.sasmovies.api;

import com.loopj.android.http.JsonHttpResponseHandler;

public class MoviesService {

    private final BaseApiService service;

    private static final String TAG = MoviesService.class.getName();

    public MoviesService(BaseApiService service) {
        this.service = service;
    }

    public void cancelRequests() {
        service.getClient().cancelAllRequests(true);
    }

    public void getPopularMovies(JsonHttpResponseHandler handler) {
        service.request().to( "/movie/popular" ).authGet().ptbr().get(handler);
    }

}
