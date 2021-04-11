package com.example.daggerpractice;


import android.util.Log;

import com.example.daggerpractice.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BaseApplication extends DaggerApplication {
    private static final String TAG = "BaseApplication, myTag";

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        Log.d(TAG, "applicationInjector: ");
        return DaggerAppComponent.builder().application(this).build();
    }
}
