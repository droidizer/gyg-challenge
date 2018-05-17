package com.guru.app.gyg.network;

import com.guru.app.gyg.model.ReviewModel;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RepositoryManager implements IRepositoryManager {

    private final ApiService mApiService;

    @Inject
    public RepositoryManager(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<ReviewModel> getReviews() {
        return mApiService.getReviews();
    }
}
