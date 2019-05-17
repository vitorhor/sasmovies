package com.androidapp.sasmovies;

import com.androidapp.sasmovies.api.AuthenticationServiceApi;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class MockAuthenticationService implements AuthenticationServiceApi {

    @Override
    public void getRequestToken(JsonHttpResponseHandler handler) {

    }

    @Override
    public void createSession(JSONObject params, JsonHttpResponseHandler handler) {

    }

}
