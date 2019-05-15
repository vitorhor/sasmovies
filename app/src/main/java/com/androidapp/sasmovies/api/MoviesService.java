package com.androidapp.sasmovies.api;

import com.androidapp.sasmovies.util.AppConstant;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONObject;

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

    public void getFavorites(JsonHttpResponseHandler handler) {
        service.request().to( "/account/%7Baccount_id%7D/favorite/movies" ).authGet().addAuthSession().get(handler);
    }

    public void getMovieDetail(String id, JsonHttpResponseHandler handler) {
        service.request().to( "/movie/" + id ).authGet().ptbr().get(handler);
    }

    public void markAsFavorite(JSONObject params, JsonHttpResponseHandler handler) {
        String accountId = Prefs.getString(AppConstant.ACCOUNT_ID, "");
        service.request().to( "/account/%7Baccount_id%7D/favorite" ).authGet().addAuthSession().post(params, handler);
    }

}
