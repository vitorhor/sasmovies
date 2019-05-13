package com.androidapp.sasmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidapp.sasmovies.R;
import com.androidapp.sasmovies.adapter.MovieAdapter;
import com.androidapp.sasmovies.api.MoviesService;
import com.androidapp.sasmovies.contract.MovieContract;
import com.androidapp.sasmovies.delegate.ItemClickDelegate;
import com.androidapp.sasmovies.delegate.RequestDelegate;
import com.androidapp.sasmovies.entity.Movie;
import com.androidapp.sasmovies.presenter.MoviePresenter;
import com.androidapp.sasmovies.repository.MovieRepository;
import com.androidapp.sasmovies.util.AppConstant;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends BaseActivity implements MovieContract.View, RequestDelegate, ItemClickDelegate {

    private MovieContract.Presenter presenter;

    private ViewFlipper viewFlipper;

    private RecyclerView recyclerView;

    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar(false);

        setWidgets();
        setActions();

        MoviesService moviesService = new MoviesService(service);

        MovieRepository movieRepository = MovieRepository.getInstance(moviesService);
        presenter = new MoviePresenter(movieRepository, this);

        presenter.start();

    }

    @Override
    protected void setWidgets() {

        viewFlipper = findViewById(R.id.viewFlipper);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.addItemDecoration(new CustomDividerItemDecoration(this, null, false, true));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    protected void setActions() {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void setPresenter(MovieContract.Presenter presenter) {

        if( presenter != null ){
            this.presenter = presenter;
        }

    }

    @Override
    public void showMovies(List<Movie> movieList) {

        adapter = new MovieAdapter(this, this, movieList);
        recyclerView.setAdapter(adapter);

        viewFlipper.setDisplayedChild(AppConstant.STATUS_SHOW);

    }

    @Override
    public void onStartRequest() {

    }

    @Override
    public void onFinishRequest() {

    }

    @Override
    public void onItemClick(int id, View v) {

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

}
