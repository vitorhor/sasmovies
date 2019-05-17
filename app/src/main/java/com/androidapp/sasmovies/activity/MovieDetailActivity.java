package com.androidapp.sasmovies.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.androidapp.sasmovies.R;
import com.androidapp.sasmovies.api.MoviesService;
import com.androidapp.sasmovies.contract.MovieDetailContract;
import com.androidapp.sasmovies.entity.Movie;
import com.androidapp.sasmovies.presenter.MovieDetailPresenter;
import com.androidapp.sasmovies.util.AppConstant;
import com.androidapp.sasmovies.util.Util;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View {

    private MovieDetailContract.Presenter presenter;

    private ProgressBar progressBarBackdrop, progressBarPoster;

    private ImageView imgBackdrop, imgPoster;

    private TextView txName, txDescription;

    private ViewFlipper viewFlipperFavorite;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
            postponeEnterTransition();
        }

        id = getIntent().getIntExtra(AppConstant.KEY_ID, -1);

        setToolbar(true);

        setWidgets();
        setActions();

        MoviesService moviesService = new MoviesService(service);

        presenter = new MovieDetailPresenter(moviesService, this);

        presenter.getDetails(id);

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
        viewFlipperFavorite = findViewById(R.id.viewFlipperFavorite);

    }

    @Override
    protected void setActions() {

        viewFlipperFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewFlipperFavorite.setEnabled(false);

                boolean favorite = false;

                if( viewFlipperFavorite.getDisplayedChild() == 0 ){
                    favorite = true;
                }

                viewFlipperFavorite.setDisplayedChild(2);
                presenter.markAsFavorite(id, favorite);

            }
        });

    }

    @Override
    public void showDetails(Movie entity) {

        if( entity == null ){
            Toast.makeText(this, getString(R.string.movie_not_available), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(AppConstant.AMERICAN_DATE_FORMAT);
        LocalDate dateTimeExpireAt = LocalDate.parse(entity.getReleaseDate(), dateTimeFormatter);

        txName.setText(entity.getTitle() + " - " + dateTimeExpireAt.toString(AppConstant.BRAZILIAN_DATE_FORMAT) + "\n" + "(" + entity.getOriginalTitle() + ")");
        txDescription.setText(entity.getOverview());

        String url = AppConstant.API_IMAGE + entity.getBackdropPath();
        Util.loadImage( url, progressBarBackdrop, imgBackdrop );

        String urlPoster = AppConstant.API_IMAGE + entity.getPosterPath();
        ImageLoader.getInstance().displayImage(urlPoster, imgPoster, null, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

                if (progressBarPoster != null) {
                    progressBarPoster.setVisibility(View.VISIBLE);
                }

                view.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                if (progressBarPoster != null) {
                    progressBarPoster.setVisibility(View.GONE);
                }

                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                if (progressBarPoster != null) {
                    progressBarPoster.setVisibility(View.GONE);
                }

                view.setVisibility(View.VISIBLE);

                if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
                    startPostponedEnterTransition();
                }

            }
        });

        refreshFavorite(entity.getFavorite());

    }

    @Override
    public void refreshFavorite(boolean favorite) {

        if( favorite ){
            viewFlipperFavorite.setDisplayedChild(1);
        } else {
            viewFlipperFavorite.setDisplayedChild(0);
        }

        viewFlipperFavorite.setEnabled(true);

    }

}
