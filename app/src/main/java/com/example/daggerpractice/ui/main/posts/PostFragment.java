package com.example.daggerpractice.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daggerpractice.R;
import com.example.daggerpractice.util.VerticalSpacingItemDecoration;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;


import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostFragment extends DaggerFragment {

    private PostViewModel postViewModel;
    private RecyclerView recyclerView;
    private static final String TAG = "PostFragment, myTag";

    @Inject
    PostsRecyclerAdapter postsRecyclerAdapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    VerticalSpacingItemDecoration decoration;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        postViewModel = ViewModelProviders.of(this, providerFactory).get(PostViewModel.class);
        subscribeObservers();
        initRecycler();

    }

    private void subscribeObservers() {

        postViewModel.observePost().removeObservers(getViewLifecycleOwner());
        postViewModel.observePost().observe(getViewLifecycleOwner(), listResource -> {

            if (listResource != null) {
                switch (listResource.status) {
                    case SUCCESS:
                        postsRecyclerAdapter.setPosts(listResource.data);
                        break;
                    case ERROR:
                        Log.d(TAG, "subscribeObservers: error");
                        break;
                }

            }

        });

    }

    private void initRecycler() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        VerticalSpacingItemDecoration verticalSpacingItemDecoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(postsRecyclerAdapter);
    }
}
