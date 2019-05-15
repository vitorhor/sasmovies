package com.androidapp.sasmovies.api;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class AuthenticationService {

    private final BaseApiService service;

    private static final String TAG = AuthenticationService.class.getName();

    public AuthenticationService(BaseApiService service) {
        this.service = service;
    }

    public void cancelRequests() {
        service.getClient().cancelAllRequests(true);
    }

    public void createRequestToken(JsonHttpResponseHandler handler) {
        service.request().to( "/authentication/token/new" ).authGet().get(handler);
    }

    public void createSession(JSONObject params, JsonHttpResponseHandler handler) {
        service.request().to( "/authentication/session/new" ).authGet().post(params, handler);
    }

//    public void createAccessToken(JSONObject params, JsonHttpResponseHandler handler) {
//        service.request().toAuth( "/auth/access_token" ).authPost().post(params, handler);
//    }

//    public void markAsFavorite(JsonHttpResponseHandler handler) {
//        String accountId = Prefs.getString(AppConstant.KEY_REQUEST_TOKEN, "");
//        service.request().to( "/account/" + accountId + "/favorite" ).authPost().post(handler);
//    }
//
//    public void createSessionToken(JSONObject params, JsonHttpResponseHandler handler) throws UnsupportedEncodingException {
//        service.request().to( "/authentication/session/new" ).authPost().post(params, handler);
//    }

}