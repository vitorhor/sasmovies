package com.androidapp.sasmovies.repository;

import com.androidapp.sasmovies.api.MoviesService;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class MovieRepository {

    private static MovieRepository instance;

    private MoviesService moviesService;

    public static MovieRepository getInstance(MoviesService moviesService){

        if( instance == null ){
            instance = new MovieRepository(moviesService);
        }

        return instance;

    }

    public MovieRepository( MoviesService moviesService ){
        this.moviesService = moviesService;
    }

    public void getMovies(JsonHttpResponseHandler jsonHttpResponseHandler) {
        moviesService.getPopularMovies(jsonHttpResponseHandler);
    }

    public void getFavorites(JsonHttpResponseHandler jsonHttpResponseHandler) {
        moviesService.getFavorites(jsonHttpResponseHandler);
    }

    public void getMovieDetail(String id, JsonHttpResponseHandler jsonHttpResponseHandler) {
        moviesService.getMovieDetail(id, jsonHttpResponseHandler);
    }

    public void markAsFavorite(JSONObject params, JsonHttpResponseHandler jsonHttpResponseHandler) {
        moviesService.markAsFavorite(params, jsonHttpResponseHandler);
    }

}
