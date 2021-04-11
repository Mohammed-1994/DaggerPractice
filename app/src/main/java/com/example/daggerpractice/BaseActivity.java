package com.example.daggerpractice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.daggerpractice.models.User;
import com.example.daggerpractice.ui.auth.AuthActivity;
import com.example.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    private static final String TAG = "BaseActivity myTag";

    @Inject public SessionManager sessionManager ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        subscribeObservers();
    }

    protected  void subscribeObservers(){
        sessionManager.getAuthUser().observe(this, userAuthResource -> {
            if (userAuthResource != null) {
                switch (userAuthResource.status) {
                    case LOADING:
                        Log.d(TAG, "subscribeObservers: LOADING");
                        break;
                    case ERROR:
                        Log.e(TAG, "subscribeObservers: " + userAuthResource.message);
                        break;
                    case AUTHENTICATED:

                        break;
                    case NOT_AUTHENTICATED:
                        Log.d(TAG, "subscribeObservers: NOT_AUTHENTICATED");
                        navLoginScreen();
                        break;
                }

            }
        });
    }

    private void navLoginScreen(){
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }













}
