package com.androidapp.sasmovies.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidapp.sasmovies.R;
import com.androidapp.sasmovies.api.MoviesService;
import com.androidapp.sasmovies.entity.Movie;
import com.androidapp.sasmovies.repository.MovieRepository;
import com.androidapp.sasmovies.util.AppConstant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

public class MovieDetailActivity extends BaseActivity {

    private ProgressBar progressBarBackdrop, progressBarPoster;

    private ImageView imgBackdrop, imgPoster;

    private String id;

    private Movie entity;

    private TextView txName, txDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
            postponeEnterTransition();
        }

        id = getIntent().getStringExtra(AppConstant.KEY_ID);

        setToolbar(true);

        setWidgets();
        setActions();
        getData();

        if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
            getWindow().setEnterTransition(null);
            imgPoster.setTransitionName(getString(R.string.transition_detail) + id);
            getWindow().setSharedElementReturnTransition(null);
            getWindow().setSharedElementReenterTransition(null);
        }

    }

    @Override
    protected void setWidgets() {

        progressBarBackdrop = findViewById(R.id.progressBarBackdrop);
        progressBarPoster = findViewById(R.id.progressBarPoster);
        imgBackdrop = findViewById(R.id.imgBackdrop);
        imgPoster = findViewById(R.id.imgPoster);

        txName = findViewById(R.id.txName);
        txDescription = findViewById(R.id.txDescription);

    }

    @Override
    protected void setActions() {



    }

    private void getData(){

        MoviesService moviesService = new MoviesService(service);

        MovieRepository movieRepository = MovieRepository.getInstance(moviesService);

        movieRepository.getMovieDetail(id, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("mslz", response.toString());

                Type type = new TypeToken<Movie>() {}.getType();

                String userJson = response.toString();
                entity = new Gson().fromJson(userJson, type);

                loadData();

            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Log.d("mslz", "Falha onFailure = " + response.toString());
            }

        });

    }

    private void loadData(){

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(AppConstant.AMERICAN_DATE_FORMAT);
        LocalDate dateTimeExpireAt = LocalDate.parse(entity.getReleaseDate(), dateTimeFormatter);

        txName.setText(entity.getTitle() + " - " + dateTimeExpireAt.toString(AppConstant.BRAZILIAN_DATE_FORMAT) + "\n" + "(" + entity.getOriginalTitle() + ")");
        txDescription.setText(entity.getOverview());

        String url = "https://image.tmdb.org/t/p/w500/" + entity.getBackdropPath();

        ImageLoader.getInstance().displayImage(url, imgBackdrop, null, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

                if (progressBarBackdrop != null) {
                    progressBarBackdrop.setVisibility(View.VISIBLE);
                }

                view.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                if (progressBarBackdrop != null) {
                    progressBarBackdrop.setVisibility(View.GONE);
                }

                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                if (progressBarBackdrop != null) {
                    progressBarBackdrop.setVisibility(View.GONE);
                }

                view.setVisibility(View.VISIBLE);
            }
        });

        String urlPoster = "https://image.tmdb.org/t/p/w500/" + entity.getPosterPath();

        ImageLoader.getInstance().displayImage(urlPoster, imgPoster, null, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

                if (progressBarBackdrop != null) {
                    progressBarBackdrop.setVisibility(View.VISIBLE);
                }

                view.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                if (progressBarBackdrop != null) {
                    progressBarBackdrop.setVisibility(View.GONE);
                }

                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                if (progressBarBackdrop != null) {
                    progressBarBackdrop.setVisibility(View.GONE);
                }

                view.setVisibility(View.VISIBLE);

                if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
                    startPostponedEnterTransition();
                }

            }
        });

    }

}
