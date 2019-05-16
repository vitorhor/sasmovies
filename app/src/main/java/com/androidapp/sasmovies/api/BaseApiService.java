package com.androidapp.sasmovies.api;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.androidapp.sasmovies.BuildConfig;
import com.androidapp.sasmovies.util.AppConstant;
import com.androidapp.sasmovies.util.Util;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONObject;

import cz.msebera.android.httpclient.entity.StringEntity;

public class BaseApiService {

    private Context context;

    private static BaseApiService instance;

    public static final String URL = "https://api.themoviedb.org/3";

    private final AsyncHttpClient client;

    private String path;

    public static BaseApiService getInstance(Context context){

        if( instance == null ){
            instance = new BaseApiService(context);
        }

        return instance;

    }

    public BaseApiService(Context context) {

        this.context = context;

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Util.initializeSSLContext(context);
        }

        client = new AsyncHttpClient();
        client.setConnectTimeout(30000);
        client.setTimeout(30000);

    }

    public BaseApiService request() {

        client.addHeader("Content-Type", "application/json");
        client.addHeader("charset", "UTF-8");

        return this;

    }

    public BaseApiService authGet() {

        this.path = path + "?api_key=" + BuildConfig.T;
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

    public BaseApiService ptbr() {
        this.path = path + "&language=pt-BR";
        return this;
    }

    public void post(JSONObject jsonParameters, JsonHttpResponseHandler handler) {
        StringEntity entity = new StringEntity(jsonParameters.toString(),"UTF-8");
        this.client.post(context, path, entity, "application/json", handler);
    }

    public void get(JsonHttpResponseHandler jsonHandler) {
        this.client.get(path, jsonHandler);
    }

}
