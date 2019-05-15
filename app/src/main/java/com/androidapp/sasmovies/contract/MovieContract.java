package com.androidapp.sasmovies.contract;

import com.androidapp.sasmovies.BaseView;
import com.androidapp.sasmovies.entity.Movie;
import com.androidapp.sasmovies.presenter.BasePresenter;

import java.util.List;

public interface MovieContract {

    interface View extends BaseView<Presenter> {

        void showMovies(List<Movie> movieList);

//        void getAccountId();

        void authUser(String requestToken);

    }

    interface Presenter extends BasePresenter {

        void loadMovies();

        void createSession();

    }

}
