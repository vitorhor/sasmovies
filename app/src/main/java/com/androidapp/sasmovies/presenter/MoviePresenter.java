package com.androidapp.sasmovies.presenter;

import android.util.Log;

import com.androidapp.sasmovies.contract.MovieContract;
import com.androidapp.sasmovies.delegate.RequestDelegate;
import com.androidapp.sasmovies.entity.Movie;
import com.androidapp.sasmovies.repository.MovieRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MoviePresenter implements MovieContract.Presenter, RequestDelegate {

    private MovieRepository movieRepository;

    private MovieContract.View movieView;

    public MoviePresenter(MovieRepository movieRepo, MovieContract.View movieView) {
        this.movieRepository = movieRepo;
        this.movieView = movieView;
    }

    @Override
    public void start() {
        loadMovies();
    }

    @Override
    public void loadMovies() {

        getMovies();

//        String prefsExpireAt = Prefs.getString(AppConstant.REQUEST_EXPIRES_AT, "");
//
//        if (!prefsExpireAt.isEmpty()) {
//
//            LocalDateTime now = LocalDateTime.now();
//
//            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(AppConstant.API_DATE_FORMAT);
//            LocalDateTime dateTimeExpireAt = LocalDateTime.parse(prefsExpireAt, dateTimeFormatter);
//
//            Log.d("mslz", "NOW = " + now.toString("YYYY-MM-dd HH:mm:ss zzz"));
//            Log.d("mslz", "DATE EXPIRE = " + dateTimeExpireAt.toString("YYYY-MM-dd HH:mm:ss zzz"));
//
//            if (now.isAfter(dateTimeExpireAt)) {
//                getRequestToken();
//            } else {
//                getMovies();
//            }
//
//        } else {
//            getRequestToken();
//        }

    }

//    private void getRequestToken() {
//
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        JSONObject jsonParams = new JSONObject();
//
//        try {
//            jsonParams.put(AppConstant.AUTH_USERNAME, currentUser.getEmail());
//            jsonParams.put(AppConstant.AUTH_PASSWORD, AppConstant.API_PASS);
//            jsonParams.put(AppConstant.AUTH_REQUEST_TOKEN, "");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        try {
//
//            authenticationRepository.getRequestToken(this, jsonParams, new JsonHttpResponseHandler() {
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//
//                    Log.d("mslz", response.toString());
//
//                    try {
//                        Prefs.putString(AppConstant.KEY_REQUEST_TOKEN, response.getString("expires_at"));
//                        getMovies();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
//                    Log.d("mslz", "Falha onFailure = " + response.toString());
//                }
//
//            });
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//    }

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
