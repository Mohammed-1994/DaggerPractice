package com.example.daggerpractice.ui.main.posts;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.daggerpractice.SessionManager;
import com.example.daggerpractice.models.Post;
import com.example.daggerpractice.network.main.MainApi;
import com.example.daggerpractice.util.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PostViewModel extends ViewModel {


    private static final String TAG = "PostViewModel, myTag";

    private final SessionManager sessionManager;
    private final MainApi mainApi;

    private MediatorLiveData<Resource<List<Post>>> posts;

    @Inject
    public PostViewModel(SessionManager sessionManager, MainApi mainApi) {
        this.sessionManager = sessionManager;
        this.mainApi = mainApi;

        Log.d(TAG, "PostViewModel: viewModel is working");
    }

    public LiveData<Resource<List<Post>>> observePost() {

        if (posts == null) {
            Log.d(TAG, "observePost: posts is null");
            posts = new MediatorLiveData<>();
            posts.setValue(Resource.loading(null));


            final LiveData<Resource<List<Post>>> source = LiveDataReactiveStreams
                    .fromPublisher(mainApi.getPostsFromUser(sessionManager.getAuthUser().getValue().data.getId())
                            .onErrorReturn(throwable -> {
                                Log.e(TAG, "observePost: " + throwable.getMessage());
                                Post post = new Post();
                                post.setId(-1);
                                ArrayList<Post> posts = new ArrayList<>();
                                posts.add(post);
                                return posts;
                            })
                            .map((Function<List<Post>, Resource<List<Post>>>) posts -> {
                                Log.d(TAG, "observePost: map ");
                                if (posts.size() > 0) {
                                    Log.d(TAG, "observePost: posts size :" + posts.size());
                                    if (posts.get(0).getId() == -1) {
                                        Log.e(TAG, "observePost: EEE ");
                                        return Resource.error("Error", null);
                                    }
                                }
                                return Resource.success(posts);
                            })
                            .subscribeOn(Schedulers.io())
                    );

            posts.addSource(source, listResource -> {
                posts.setValue(listResource);
                posts.removeSource(source);
            });

        }

        return posts;
    }
}
