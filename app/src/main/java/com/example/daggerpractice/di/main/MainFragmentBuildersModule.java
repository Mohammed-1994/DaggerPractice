package com.example.daggerpractice.di.main;

import com.example.daggerpractice.ui.main.posts.PostFragment;
import com.example.daggerpractice.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contributesProfileFragment();

    @ContributesAndroidInjector
    abstract PostFragment contributesPostsFragment();

}
