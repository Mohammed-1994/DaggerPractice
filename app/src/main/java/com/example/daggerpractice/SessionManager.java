package com.example.daggerpractice;

import android.hardware.SensorManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.daggerpractice.models.User;
import com.example.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {
    private static final String TAG = "SessionManager myTag";

    MediatorLiveData<AuthResource<User>> cachedUser = new MediatorLiveData<>();


    @Inject
    public SessionManager() {
    }

    public void authenticateWitId(final LiveData<AuthResource<User>> source) {
        if (cachedUser != null) {
            cachedUser.setValue(AuthResource.loading(null));
            cachedUser.addSource(source, userAuthResource -> {
                cachedUser.setValue(userAuthResource);
                cachedUser.removeSource(source);

            });
        }
    }

    public void logout(){
        Log.d(TAG, "logout: logging out");
        cachedUser.setValue(AuthResource.logout());
    }

    public LiveData<AuthResource<User>> getAuthUser(){
        return cachedUser;
    }
}
