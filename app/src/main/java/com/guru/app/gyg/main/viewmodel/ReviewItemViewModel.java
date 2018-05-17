package com.guru.app.gyg.main.viewmodel;

import android.app.Application;

import com.guru.app.gyg.misc.AndroidBaseViewModel;
import com.guru.app.gyg.model.Review;

public class ReviewItemViewModel extends AndroidBaseViewModel {

    private final Review mReview;

    public ReviewItemViewModel(Application application, Review review) {
        super(application);
        mReview = review;
    }

    public String getRating() {
        return mReview.getRating();
    }

    public String getAuthor(){
        return mReview.getAuthor();
    }

    public String getTitle(){
        return mReview.getTitle();
    }

    public String getDate(){
        return mReview.getDate();
    }
}
