package com.androidapp.sasmovies.contract;

import com.androidapp.sasmovies.entity.Movie;

import java.util.List;

public interface MovieListContract {

    interface View {

        void showMovies(List<Movie> movieList);

        void authUser(String requestToken);

    }

    interface Presenter {

        void loadMovies();

        void createSession();

        void getRequestToken();

    }

}
