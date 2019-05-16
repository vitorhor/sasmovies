package com.androidapp.sasmovies.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.androidapp.sasmovies.R;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends BaseActivity {

    private ImageView imgMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        hideStatusBar();

        setWidgets();
        setActions();

    }

    @Override
    protected void setWidgets() {
        imgMain = findViewById(R.id.imgMain);
    }

    @Override
    protected void setActions() {

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLogin();
            }
        }, 2000);

    }

    private void checkLogin(){

        FirebaseUser currentUser = mAuth.getCurrentUser();

        Bundle bundle = new Bundle();

        Intent i = new Intent(this, MovieListActivity.class);

        if( currentUser == null ){

            if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
                imgMain.setTransitionName(getString(R.string.transition_main));
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this, imgMain, "imgMain");
                bundle = transitionActivityOptions.toBundle();
                getWindow().setExitTransition(null);
            }

            i = new Intent(this, LoginActivity.class);

        }

        startActivity(i, bundle);
        finish();

    }

}
