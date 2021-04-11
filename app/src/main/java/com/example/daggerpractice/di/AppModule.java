package com.example.daggerpractice.di;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.daggerpractice.R;
import com.example.daggerpractice.util.Constant;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    private static final String TAG = "AppModule, myTag";

    @Singleton
    @Provides
    static Retrofit ProvidesRetrofitInstanst() {
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    @Singleton
    @Provides
    static FragmentActivity proFragmentActivity(){
        return new FragmentActivity();
    }
    @Singleton
    @Provides
    static RequestOptions provideRequestOptions() {
        Log.d(TAG, "provideRequestOptions: ");
        return RequestOptions
                .placeholderOf(R.drawable.white_background)
                .error(R.drawable.white_background);
    }

    @Singleton
    @Provides
    static RequestManager provideRequestManager(Application application, RequestOptions requestOptions) {
        Log.d(TAG, "provideRequestManager: ");
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }

    @Singleton
    @Provides
    static Drawable provideAppDrawable(Application application) {
        Log.d(TAG, "provideAppDrawable: ");
        return ContextCompat.getDrawable(application, R.drawable.logo);
    }


}
