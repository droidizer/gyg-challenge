package com.guru.app.gyg.network;

import com.guru.app.gyg.model.ReviewModel;

import io.reactivex.Observable;

public interface IRepositoryManager {

    Observable<ReviewModel> getReviews();
}
