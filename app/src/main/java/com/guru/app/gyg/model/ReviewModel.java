package com.guru.app.gyg.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewModel {

    @SerializedName("total_reviews_comments")
    String totalReviews;

    @SerializedName("data")
    List<Review> reviews;

    public String getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(String totalReviews) {
        this.totalReviews = totalReviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
