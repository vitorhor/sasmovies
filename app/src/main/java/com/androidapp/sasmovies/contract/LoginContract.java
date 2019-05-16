package com.androidapp.sasmovies.contract;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface LoginContract {

    interface View {

        void loginSuccess();

        void loginFailed();

    }

    interface Presenter {

        void prepareGoogleLogin();

        Intent signIn();

        void verifyLogin(Intent data);

        void firebaseAuthWithGoogle(GoogleSignInAccount acct);

    }

}
