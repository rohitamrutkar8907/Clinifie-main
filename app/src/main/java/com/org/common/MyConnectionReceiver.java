package com.org.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;


public class MyConnectionReceiver extends BroadcastReceiver {

    private static final String TAG = "MyConnectionReceiver";
    private final Context context;
    private Snackbar snackbar;
    private Button button;
    private ProgressBar progressBar1;
    private ProgressBar progressBar2;

    public MyConnectionReceiver(Context context, Snackbar snackbar, Button button) {
        this.context = context;
        this.snackbar = snackbar;
        this.button = button;
    }

    public MyConnectionReceiver(Context context) {
        this.context = context;
    }

    public MyConnectionReceiver(Context context, Snackbar snackbar, ProgressBar progressBar1) {
        this.context = context;
        this.snackbar = snackbar;
        this.progressBar1 = progressBar1;
    }

    public MyConnectionReceiver(Context context, Snackbar snackbar, ProgressBar progressBar1, ProgressBar progressBar2) {
        this.context = context;
        this.snackbar = snackbar;
        this.progressBar1 = progressBar1;
        this.progressBar2 = progressBar2;
    }


    public MyConnectionReceiver(Context context, Snackbar snackbar, Button button, ProgressBar progressBar1) {
        this.context = context;
        this.snackbar = snackbar;
        this.button = button;
        this.progressBar1 = progressBar1;
    }

    public MyConnectionReceiver(Context context, Snackbar snackbar, Button button, ProgressBar progressBar1, ProgressBar progressBar2) {
        this.context = context;
        this.snackbar = snackbar;
        this.button = button;
        this.progressBar1 = progressBar1;
        this.progressBar2 = progressBar2;
    }

    public MyConnectionReceiver(Context applicationContext, Button saveData) {
        this.context = applicationContext;
        this.button = saveData;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (isOnline(context)) {
                if (snackbar != null) {
                    if (progressBar1 != null) {
                        progressBar1.setVisibility(View.GONE);
                    }
                    if (progressBar2 != null) {
                        progressBar2.setVisibility(View.GONE);
                    }
                    snackbar.dismiss();
                    if (button != null) {
                        button.setEnabled(true);
                    }

                }
            } else {
                snackbar.show();
                if (button != null) {
                    button.setEnabled(false);
                }

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}