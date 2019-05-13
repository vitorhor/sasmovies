package com.androidapp.sasmovies.datasource;

import com.androidapp.sasmovies.api.BaseApiService;

public class MovieDataSource {

    private BaseApiService service;

    private static MovieDataSource instance;

    public static MovieDataSource getInstance(){

        if( instance == null ){
            instance = new MovieDataSource();
        }

        return instance;

    }




}
