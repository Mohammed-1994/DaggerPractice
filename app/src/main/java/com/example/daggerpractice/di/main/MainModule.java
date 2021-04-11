package com.example.daggerpractice.di.main;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.daggerpractice.network.main.MainApi;
import com.example.daggerpractice.ui.main.posts.PostsRecyclerAdapter;
import com.example.daggerpractice.util.VerticalSpacingItemDecoration;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    static PostsRecyclerAdapter postsRecyclerAdapter() {
        return new PostsRecyclerAdapter();
    }

    @Provides
    static VerticalSpacingItemDecoration decoration() {
        return new VerticalSpacingItemDecoration(15);
    }

    @Provides
    LinearLayoutManager layoutManager(FragmentActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }
}
