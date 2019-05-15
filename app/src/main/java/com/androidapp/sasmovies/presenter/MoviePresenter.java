package com.androidapp.sasmovies.presenter;

import android.util.Log;

import com.androidapp.sasmovies.contract.MovieContract;
import com.androidapp.sasmovies.delegate.RequestDelegate;
import com.androidapp.sasmovies.entity.Movie;
import com.androidapp.sasmovies.repository.AuthenticationRepository;
import com.androidapp.sasmovies.repository.MovieRepository;
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

public class MoviePresenter implements MovieContract.Presenter, RequestDelegate {

    private MovieRepository movieRepository;

    private AuthenticationRepository authenticationRepository;

    private MovieContract.View movieView;

    public MoviePresenter(MovieRepository movieRepo, AuthenticationRepository authRepo, MovieContract.View movieView) {
        this.movieRepository = movieRepo;
        this.authenticationRepository = authRepo;
        this.movieView = movieView;
    }

    @Override
    public void start() {
        getRequestToken();
    }

    private void getRequestToken() {

        authenticationRepository.getRequestToken(this, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("mslz", response.toString());

                try {
                    movieView.authUser(response.getString("request_token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Log.d("mslz", "Falha onFailure = " + response.toString());
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

        authenticationRepository.createSession(this, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("mslz", response.toString());

                try {
                    Prefs.putString(AppConstant.SESSION_ID, response.getString(AppConstant.SESSION_ID));
                    getMovies();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Log.d("mslz", "Falha onFailure = " + response.toString());
            }

        });

    }

    @Override
    public void loadMovies() {
        getMovies();
    }

    private void getMovies(){

        movieRepository.getMovies(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("mslz", response.toString());

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
                Log.d("mslz", "Falha onFailure = " + response.toString());
                movieView.showMovies(new ArrayList<Movie>());
            }

        });

    }

    @Override
    public void onStartRequest() {

    }

    @Override
    public void onFinishRequest() {

    }

}
