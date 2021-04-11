package com.example.daggerpractice.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.daggerpractice.SessionManager;
import com.example.daggerpractice.models.User;
import com.example.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {

    private final SessionManager sessionManager;

    private static final String TAG = "ProfileViewModel, myTag";

    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        Log.d(TAG, "ProfileViewModel:  ccc");
    }

    public LiveData<AuthResource<User>> getAuthUser() {
        return sessionManager.getAuthUser();
    }
}
