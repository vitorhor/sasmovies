package com.androidapp.sasmovies.api;

import android.content.Context;

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

    public String getAccessToken() {
        return Prefs.getString(AppConstant.KEY_PREFERENCE_USER_TOKEN, null);
    }

//    public void removeFromPreferences(String key) {
//        Prefs.remove(key);
//    }

    public BaseApiService request() {

        client.addHeader("Content-Type", "application/json");
        client.addHeader("charset", "UTF-8");
        client.addHeader("AppToken", AppConstant.API_TOKEN);

        return this;

    }

    public BaseApiService addEstablishment() {

        String placeToken = Prefs.getString(AppConstant.KEY_PREFERENCE_PLACE_TOKEN, "");
//
        client.addHeader("EstablishmentToken", placeToken);
//        client.addHeader("EstablishmentToken", "53c49811-ad1b-4dcb-94be-3bb5f166fb03");

        return this;

    }

    public BaseApiService addCustomId(String customField, int id) {
        client.addHeader(customField, id + "");
        return this;
    }

    public BaseApiService authPost() {

//        String userToken = "53c803bf-2332-4cac-a571-70d1f43083dc";

        String userToken = Prefs.getString(AppConstant.KEY_PREFERENCE_USER_TOKEN, "");
        client.addHeader("CustomerToken", userToken);
        return request();
    }

    public BaseApiService authGet() {

        this.path = path + "?api_key=" + AppConstant.API_TOKEN;
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

    public void post(JSONObject jsonParameters, JsonHttpResponseHandler handler) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(jsonParameters.toString(),"UTF-8");
        this.client.post(context, path, entity, "application/json", handler);
    }

    public void post(String jsonParameters, JsonHttpResponseHandler handler) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(jsonParameters);
        this.client.post(context, path, entity, "application/json", handler);
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
