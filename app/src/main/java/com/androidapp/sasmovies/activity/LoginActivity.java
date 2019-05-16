package com.androidapp.sasmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidapp.sasmovies.R;
import com.androidapp.sasmovies.api.AuthenticationService;
import com.androidapp.sasmovies.contract.LoginContract;
import com.androidapp.sasmovies.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private static final int REQUEST_FIREBASE_LOGIN = 5562;

    private LoginContract.Presenter presenter;

    private TextView txLogin;

    private ImageView imgMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setWidgets();
        setActions();

        presenter = new LoginPresenter(this, this);
        presenter.prepareGoogleLogin();

        if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
            getWindow().setEnterTransition(null);
            imgMain.setTransitionName(getString(R.string.transition_main));
        }

    }

    @Override
    protected void setWidgets() {
        txLogin = findViewById(R.id.txLogin);
        imgMain = findViewById(R.id.imgMain);
    }

    @Override
    protected void setActions() {

        txLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private void signIn() {
        Intent signInIntent = presenter.signIn();
        startActivityForResult(signInIntent, REQUEST_FIREBASE_LOGIN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FIREBASE_LOGIN) {
            presenter.verifyLogin(data);
        }

    }

    @Override
    public void loginSuccess() {

        Intent i = new Intent(LoginActivity.this, MovieListActivity.class);
        startActivity(i);

        finish();

    }

    @Override
    public void loginFailed() {
        Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_LONG).show();
    }

}
