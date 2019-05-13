package com.androidapp.sasmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.androidapp.sasmovies.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar(false);

    }

    @Override
    protected void setWidgets() {

    }

    @Override
    protected void setActions() {

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
