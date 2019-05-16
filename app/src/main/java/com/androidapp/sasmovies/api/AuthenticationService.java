package com.androidapp.sasmovies.api;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class AuthenticationService {

    private final BaseApiService service;

    public AuthenticationService(BaseApiService service) {
        this.service = service;
    }

    public void getRequestToken(JsonHttpResponseHandler handler) {
        service.request().to( "/authentication/token/new" ).authGet().get(handler);
    }

    public void createSession(JSONObject params, JsonHttpResponseHandler handler) {
        service.request().to( "/authentication/session/new" ).authGet().post(params, handler);
    }

}