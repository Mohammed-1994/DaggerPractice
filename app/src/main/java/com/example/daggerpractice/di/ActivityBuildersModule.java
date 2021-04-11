package com.example.daggerpractice.di;


import com.example.daggerpractice.di.auth.AuthModule;
import com.example.daggerpractice.di.auth.AuthViewModelsModule;
import com.example.daggerpractice.di.main.MainFragmentBuildersModule;
import com.example.daggerpractice.di.main.MainModule;
import com.example.daggerpractice.di.main.MainViewModelModul;
import com.example.daggerpractice.network.main.MainApi;
import com.example.daggerpractice.ui.auth.AuthActivity;
import com.example.daggerpractice.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {AuthViewModelsModule.class, AuthModule.class}
    )
    abstract AuthActivity contributesAuthActivity();


    @ContributesAndroidInjector(
            modules = {MainFragmentBuildersModule.class, MainViewModelModul.class, MainModule.class}
    )
    abstract MainActivity contributesMainActivity();
}
