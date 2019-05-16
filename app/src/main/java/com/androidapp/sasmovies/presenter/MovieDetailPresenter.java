package com.androidapp.sasmovies.presenter;

import android.util.Log;

import com.androidapp.sasmovies.api.MoviesService;
import com.androidapp.sasmovies.contract.MovieDetailContract;
import com.androidapp.sasmovies.entity.Movie;
import com.androidapp.sasmovies.util.AppConstant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MoviesService moviesService;

    private MovieDetailContract.View movieView;

    public MovieDetailPresenter(MoviesService moviesService, MovieDetailContract.View movieView) {
        this.moviesService = moviesService;
        this.movieView = movieView;
    }

    @Override
    public void markAsFavorite(int id, final boolean favorite) {

        JSONObject params = new JSONObject();

        try {
            params.put(AppConstant.MEDIA_TYPE, "movie");
            params.put(AppConstant.MEDIA_ID, id);
            params.put(AppConstant.FAVORITE, favorite);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        moviesService.markAsFavorite(params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("mslz", response.toString());
                movieView.refreshFavorite(favorite);
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Log.d("mslz", "Falha onFailure = " + response.toString());
            }

        });

    }

    @Override
    public void getDetails(int id) {

        moviesService.getMovieDetail(id, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("mslz", response.toString());

                Type type = new TypeToken<Movie>() {
                }.getType();

                String userJson = response.toString();
                Movie entity = new Gson().fromJson(userJson, type);

                getFavorites(entity);

            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Log.d("mslz", "Falha onFailure = " + response.toString());
            }

        });


    }

    @Override
    public void getFavorites(final Movie entity) {

        moviesService.getFavorites(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("mslz", response.toString());

                Type type = new TypeToken<List<Movie>>() {
                }.getType();

                List<Movie> listFavorites = new ArrayList<>();

                try {

                    String responseJson = response.getJSONArray("results").toString();
                    listFavorites = new Gson().fromJson(responseJson, type);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (listFavorites.size() > 0) {

                    for (Movie favorite : listFavorites) {

                        if (entity.getId().intValue() == favorite.getId().intValue()) {
                            entity.setFavorite(true);
                        }

                    }

                }

                movieView.showDetails(entity);

            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Log.d("mslz", "Falha onFailure = " + response.toString());
                movieView.showDetails(new Movie());
            }

        });

    }
    
}
