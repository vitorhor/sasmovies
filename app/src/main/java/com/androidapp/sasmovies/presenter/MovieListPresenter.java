package com.androidapp.sasmovies.presenter;

import com.androidapp.sasmovies.api.AuthenticationServiceApi;
import com.androidapp.sasmovies.api.MoviesServiceApi;
import com.androidapp.sasmovies.contract.MovieListContract;
import com.androidapp.sasmovies.entity.Movie;
import com.androidapp.sasmovies.util.AppConstant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieListPresenter implements MovieListContract.Presenter {

    private MoviesServiceApi moviesService;

    private AuthenticationServiceApi authenticationService;

    private MovieListContract.View movieView;

    public MovieListPresenter(MoviesServiceApi moviesService, AuthenticationServiceApi authService, MovieListContract.View movieView) {
        this.moviesService = moviesService;
        this.authenticationService = authService;
        this.movieView = movieView;
    }

    @Override
    public void getRequestToken() {

        authenticationService.getRequestToken(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    movieView.authUser(response.getString("request_token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
            }

        });

    }

    public void createSession() {

        JSONObject params = new JSONObject();

        try {
            String requestToken = Prefs.getString(AppConstant.REQUEST_TOKEN, "");
            params.put(AppConstant.REQUEST_TOKEN, requestToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        authenticationService.createSession(params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Prefs.putString(AppConstant.SESSION_ID, response.getString(AppConstant.SESSION_ID));
                    loadMovies();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {

            }

        });

    }

    @Override
    public void loadMovies() {

        moviesService.getPopularMovies(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Type type = new TypeToken<List<Movie>>() {}.getType();

                List<Movie> listMovies = new ArrayList<>();

                try {

                    String responseJson = response.getJSONArray("results").toString();
                    listMovies = new Gson().fromJson(responseJson, type);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                movieView.showMovies(listMovies);

            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                movieView.showMovies(new ArrayList<Movie>());
            }

        });

    }

}
