package com.androidapp.sasmovies.contract;

import com.androidapp.sasmovies.BaseView;
import com.androidapp.sasmovies.entity.Movie;
import com.androidapp.sasmovies.presenter.BasePresenter;

public interface MovieDetailContract {

    interface View extends BaseView<Presenter> {

        void showDetails(Movie entity);

        void refreshFavorite(boolean favorite);

    }

    interface Presenter extends BasePresenter {

        void markAsFavorite(String id, boolean favorite);

        void getDetails(String id);

        void getFavorites(Movie entity);

    }

}
