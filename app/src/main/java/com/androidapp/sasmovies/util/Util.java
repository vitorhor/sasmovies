package com.androidapp.sasmovies.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

public class Util {

    public static boolean isOnline(Context c) {

        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }

        return false;

    }

    public static void loadImage(String url, final ProgressBar pgImage, final ImageView imageView) {

        if (url != null && !url.isEmpty()) {

            ImageLoader.getInstance().displayImage(url, imageView, null, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                    if (pgImage != null) {
                        pgImage.setVisibility(View.VISIBLE);
                    }

                    view.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    if (pgImage != null) {
                        pgImage.setVisibility(View.GONE);
                    }

                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                    if (pgImage != null) {
                        pgImage.setVisibility(View.GONE);
                    }

                    view.setVisibility(View.VISIBLE);
                }
            });

        }

    }

    public static void initializeSSLContext(Context mContext){

        try {
            SSLContext.getInstance("TLSv1.2");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            ProviderInstaller.installIfNeeded(mContext.getApplicationContext());
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

}
