package com.androidapp.sasmovies.api;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public interface AuthenticationServiceApi {

    void getRequestToken(JsonHttpResponseHandler handler);

    void createSession(JSONObject params, JsonHttpResponseHandler handler);

}