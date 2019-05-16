package com.androidapp.sasmovies.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidapp.sasmovies.R;
import com.androidapp.sasmovies.adapter.MovieListAdapter;
import com.androidapp.sasmovies.api.AuthenticationService;
import com.androidapp.sasmovies.api.MoviesService;
import com.androidapp.sasmovies.contract.MovieListContract;
import com.androidapp.sasmovies.delegate.ItemClickDelegate;
import com.androidapp.sasmovies.entity.Movie;
import com.androidapp.sasmovies.presenter.MovieListPresenter;
import com.androidapp.sasmovies.util.AppConstant;
import com.google.firebase.auth.FirebaseAuth;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

public class MovieListActivity extends BaseActivity implements MovieListContract.View, ItemClickDelegate {

    public static final int REQUEST_PERMISSION = 4412;

    private TextView txAllowApi;

    private MovieListContract.Presenter presenter;

    private ViewFlipper viewFlipper;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar(false);

        setWidgets();
        setActions();

        MoviesService moviesService = new MoviesService(service);
        AuthenticationService authenticationService = new AuthenticationService(service);
        presenter = new MovieListPresenter(moviesService, authenticationService, this);

        String sessionId = Prefs.getString(AppConstant.SESSION_ID, "");

        if( sessionId.isEmpty() ){
            presenter.getRequestToken();
        } else {
            presenter.loadMovies();
        }

    }

    @Override
    protected void setWidgets() {

        txAllowApi = findViewById(R.id.txAllowApi);
        viewFlipper = findViewById(R.id.viewFlipper);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    protected void setActions() {

        txAllowApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getRequestToken();
            }
        });

    }

    @Override
    public void showMovies(List<Movie> movieList) {

        MovieListAdapter adapter = new MovieListAdapter(this, this, movieList);
        recyclerView.setAdapter(adapter);

        viewFlipper.setDisplayedChild(AppConstant.STATUS_SHOW);

    }

    @Override
    public void authUser(String requestToken) {
        Intent i = new Intent(this, AuthActivity.class);
        i.putExtra(AppConstant.KEY_ID, requestToken);
        startActivityForResult(i, REQUEST_PERMISSION);
    }

    @Override
    public void onItemClick(int id, View v) {

        Intent i = new Intent(this, MovieDetailActivity.class);

        i.putExtra(AppConstant.KEY_ID, id);

        Bundle bundle = new Bundle();

        if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {

            ImageView image = v.findViewById(R.id.imgPoster);

            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this, image, getString(R.string.transition_detail) + id);
            bundle = transitionActivityOptions.toBundle();

            getWindow().setExitTransition(null);

        }

        startActivity(i, bundle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_loggout: {

                FirebaseAuth.getInstance().signOut();

                Prefs.remove(AppConstant.SESSION_ID);

                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);

                finish();

                return true;

            }

            default: {
                return super.onOptionsItemSelected(item);
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == REQUEST_PERMISSION){

            if( resultCode == Activity.RESULT_OK ){
                viewFlipper.setDisplayedChild(AppConstant.STATUS_LOADING);
                presenter.createSession();
            } else {
                viewFlipper.setDisplayedChild(2);
            }

        }

    }
}
