package com.androidapp.sasmovies.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.androidapp.sasmovies.R;
import com.androidapp.sasmovies.util.AppConstant;
import com.pixplicity.easyprefs.library.Prefs;

public class AuthActivity extends BaseActivity {

    private WebView webView;

    private String requestToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        requestToken = getIntent().getStringExtra(AppConstant.KEY_ID);

        setToolbar(true);

        setWidgets();
        setActions();

    }

    @Override
    protected void setWidgets() {
        webView = findViewById(R.id.webView);
    }

    @Override
    protected void setActions() {

        webView.getSettings().setJavaScriptEnabled(true);

        WebViewClient webViewClient = new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);

                if (url.contains("/allow")) {

                    Prefs.putString(AppConstant.REQUEST_TOKEN, requestToken);

                    setResult(Activity.RESULT_OK);
                    finish();

                }

            }

        };

        webView.setWebViewClient(webViewClient);

        String requestUrl = AppConstant.API_AUTH_URL + requestToken;

        webView.loadUrl(requestUrl);

    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }

    }

}
