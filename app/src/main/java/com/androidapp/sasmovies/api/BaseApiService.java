package com.androidapp.sasmovies.api;

import android.content.Context;
import android.util.Log;

import com.androidapp.sasmovies.util.AppConstant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;

public class BaseApiService {

    private Context context;

    private static BaseApiService instance;

    public static final String URL_AUTH = "https://api.themoviedb.org/4";
    public static final String URL = "https://api.themoviedb.org/3";

    private final AsyncHttpClient client;

    private String path;

    public AsyncHttpClient getClient() {
        return client;
    }

    public static BaseApiService getInstance(Context context){

        if( instance == null ){
            instance = new BaseApiService(context);
        }

        return instance;

    }

    public BaseApiService(Context context) {
        this.context = context;
        client = new AsyncHttpClient(true, 80, 443);
        client.setConnectTimeout(30000);
        client.setTimeout(30000);
    }

    public BaseApiService request() {

        client.addHeader("Content-Type", "application/json");
        client.addHeader("charset", "UTF-8");

        return this;

    }

    public BaseApiService authPost() {

        String apiToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZGE4ZTg2NGMyMzBiNzMzZTg2MTA1ZmQ1YzRiNWMxMiIsInN1YiI6IjVjZDMxZDZkYzNhMzY4MGI3MGRkNTY0NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.GnO9lV9mG4miu35_NrYNWE8g3qqENnMRrAuUNAvUROk";

        client.addHeader("Authorization", apiToken);
        return request();

    }

    public BaseApiService authGet() {

        this.path = path + "?api_key=" + AppConstant.API_TOKEN;
        return this;

    }

    public BaseApiService addAuthSession() {

        String sessionId = Prefs.getString(AppConstant.SESSION_ID, "");
        Log.d("mslz", "SESSION_ID = " + sessionId);

        this.path = path + "&session_id=" + sessionId;
        return this;

    }

    public BaseApiService to(String path) {
        this.path = URL + path;
        return this;
    }

    public BaseApiService toAuth(String path) {
        this.path = URL_AUTH + path;
        return this;
    }

    public BaseApiService ptbr() {
        this.path = path + "&language=pt-BR";
        return this;
    }

    public void post(JSONObject jsonParameters, JsonHttpResponseHandler handler) {
        StringEntity entity = new StringEntity(jsonParameters.toString(),"UTF-8");
        this.client.post(context, path, entity, "application/json", handler);
    }

    public void post(String jsonParameters, JsonHttpResponseHandler handler) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(jsonParameters);
        this.client.post(context, path, entity, "application/json", handler);
    }

    public void post(JsonHttpResponseHandler handler) {
        this.client.post(context, path, null, "application/json", handler);
    }

    public void delete(JsonHttpResponseHandler handler) {
        this.client.delete(path, handler);
    }

    public void get(JsonHttpResponseHandler jsonHandler) {
        this.client.get(path, jsonHandler);
    }

    public void get(JSONObject jsonParameters, JsonHttpResponseHandler jsonHandler) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(jsonParameters.toString());
        this.client.get(context, path, entity, "application/json", jsonHandler);
    }

    public void put(JSONObject jsonParameters, JsonHttpResponseHandler handler) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(jsonParameters.toString());
        this.client.put(context, path, entity, "application/json", handler);
    }

}
