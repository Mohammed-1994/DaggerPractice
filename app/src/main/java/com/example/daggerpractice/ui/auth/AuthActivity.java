package com.example.daggerpractice.ui.auth;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.RequestManager;
import com.example.daggerpractice.R;
import com.example.daggerpractice.ui.main.MainActivity;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    private static final String TAG = "AuthActivity, myTag";

    private AuthViewModel viewModel;

    private EditText loginEditText;
    private ProgressBar progressBar;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setLogo();


        loginEditText = findViewById(R.id.user_id_input);
        progressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptToLogin();
            }
        });
        viewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel.class);
        subscribeObservers();
    }

    private void attemptToLogin() {
        if (TextUtils.isEmpty(loginEditText.getText().toString()))
            return;
        else {
            viewModel.authenticateWithId(Integer.parseInt(loginEditText.getText().toString()));
        }
    }

    private void subscribeObservers() {
        viewModel.observeAuthState().observe(this, userAuthResource -> {
            if (userAuthResource != null) {
                switch (userAuthResource.status) {
                    case LOADING:
                        showProgressBar(true);
                        Log.d(TAG, "subscribeObservers: LOADING");
                        break;
                    case ERROR:
                        showProgressBar(false);
                        Log.e(TAG, "subscribeObservers: " + userAuthResource.message);
                        break;
                    case AUTHENTICATED:
                        showProgressBar(false);
                        Log.d(TAG, "subscribeObservers: AUTHENTICATED");
                        Log.d(TAG, userAuthResource.data.toString());
                        onLoginSuccess();
                        break;
                    case NOT_AUTHENTICATED:
                        showProgressBar(false);
                        Log.d(TAG, "subscribeObservers: NOT_AUTHENTICATED");
                        break;
                }

            }
        });
    }

    private void onLoginSuccess(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void showProgressBar(boolean isVisible) {
        if (isVisible)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    private void setLogo() {

        requestManager
                .load(logo)
                .into((ImageView) findViewById(R.id.login_logo));


    }

}