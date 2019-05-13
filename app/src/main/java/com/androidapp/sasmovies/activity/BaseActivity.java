package com.androidapp.sasmovies.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androidapp.sasmovies.R;
import com.google.firebase.auth.FirebaseAuth;

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    protected boolean allowProgressDialog;

    protected ProgressDialog progressDialog;

    protected FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        allowProgressDialog = false;

    }

    protected void setToolbar(boolean isInternalPage) {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (isInternalPage) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    protected abstract void setWidgets();

    protected abstract void setActions();

    public void setAllowProgressDialog(Boolean mAllowProgressDialog) {
        this.allowProgressDialog = mAllowProgressDialog;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home: {
                onBackPressed();
                return true;
            }


            default: {
                return super.onOptionsItemSelected(item);
            }

        }

    }

    protected void hideStatusBar(){
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

}
