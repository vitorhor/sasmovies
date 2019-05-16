package com.androidapp.sasmovies.contract;

import com.androidapp.sasmovies.entity.Movie;

public interface MovieDetailContract {

    interface View {

        void showDetails(Movie entity);

        void refreshFavorite(boolean favorite);

    }

    interface Presenter {

        void markAsFavorite(int id, boolean favorite);

        void getDetails(int id);

        void getFavorites(Movie entity);

    }

}
