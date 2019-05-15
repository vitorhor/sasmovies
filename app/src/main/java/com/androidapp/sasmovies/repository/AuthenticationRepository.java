package com.androidapp.sasmovies.repository;

import com.androidapp.sasmovies.api.AuthenticationService;
import com.androidapp.sasmovies.delegate.RequestDelegate;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class AuthenticationRepository {

    private static AuthenticationRepository instance;

    private AuthenticationService authenticationService;

    public static AuthenticationRepository getInstance(AuthenticationService authenticationService){

        if( instance == null ){
            instance = new AuthenticationRepository(authenticationService);
        }

        return instance;

    }

    public AuthenticationRepository(AuthenticationService authService ){
        authenticationService = authService;
    }

    public void getRequestToken(RequestDelegate requestDelegate, JsonHttpResponseHandler jsonHttpResponseHandler) {

        requestDelegate.onStartRequest();

        authenticationService.createRequestToken(jsonHttpResponseHandler);

    }

    public void createSession(RequestDelegate requestDelegate, JSONObject params, JsonHttpResponseHandler jsonHttpResponseHandler) {

        requestDelegate.onStartRequest();

        authenticationService.createSession(params, jsonHttpResponseHandler);

    }

//    public void getAccessToken(RequestDelegate requestDelegate, JSONObject params, JsonHttpResponseHandler jsonHttpResponseHandler) {
//        requestDelegate.onStartRequest();
//        authenticationService.createAccessToken(params, jsonHttpResponseHandler);
//    }

}