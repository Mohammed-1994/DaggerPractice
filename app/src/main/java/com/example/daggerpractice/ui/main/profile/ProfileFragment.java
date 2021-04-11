package com.example.daggerpractice.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.daggerpractice.R;
import com.example.daggerpractice.models.User;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {
    private static final String TAG = "ProfileFragment, myTag";

    ProfileViewModel profileViewModel;
    private TextView emailTextView, usernameTextView, websiteTextView;
    @Inject
    ViewModelProviderFactory providerFactory;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        emailTextView = view.findViewById(R.id.email);
        usernameTextView = view.findViewById(R.id.username);
        websiteTextView = view.findViewById(R.id.website);
        profileViewModel = ViewModelProviders.of(this, providerFactory).get(ProfileViewModel.class);


        subscribeObservers();
    }

    private void subscribeObservers() {
        profileViewModel.getAuthUser().removeObservers(getViewLifecycleOwner());

        profileViewModel.getAuthUser().observe(getViewLifecycleOwner(), userAuthResource -> {
            if (userAuthResource != null) {
                switch (userAuthResource.status) {
                    case AUTHENTICATED:
                        setUserDetails(userAuthResource.data);

                        break;
                    case ERROR:
                        setErrorDetails(userAuthResource.message);
                        break;

                }
            }
        });
    }

    private void setUserDetails(User data) {
        emailTextView.setText(data.getEmail());
        websiteTextView.setText(data.getWebsite());
        usernameTextView.setText(data.getUsername());
    }

    private void setErrorDetails(String message) {
        emailTextView.setText(message);
        websiteTextView.setText(getActivity().getString(R.string.error));
        usernameTextView.setText(getActivity().getString(R.string.error));
    }
}
