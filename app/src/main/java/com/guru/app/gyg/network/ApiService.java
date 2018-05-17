package com.guru.app.gyg.network;


import com.guru.app.gyg.model.ReviewModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiService {

    @Headers("User-agent: android")
    @GET("berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/reviews.json")
    Observable<ReviewModel> getReviews();
}
