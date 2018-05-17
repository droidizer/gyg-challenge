package com.guru.app.gyg.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Review {

    @SerializedName("review_id")
    long reviewId;

    @SerializedName("rating")
    String rating;

    @SerializedName("title")
    String title;

    @SerializedName("author")
    String author;

    @SerializedName("date")
    String date;

    @SerializedName("reviewerName")
    String reviewerName;

    @SerializedName("reviewerCountry")
    String reviewerCountry;

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewerCountry() {
        return reviewerCountry;
    }

    public void setReviewerCountry(String reviewerCountry) {
        this.reviewerCountry = reviewerCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return reviewId == review.reviewId &&
                Objects.equals(rating, review.rating) &&
                Objects.equals(title, review.title) &&
                Objects.equals(author, review.author) &&
                Objects.equals(date, review.date) &&
                Objects.equals(reviewerName, review.reviewerName) &&
                Objects.equals(reviewerCountry, review.reviewerCountry);
    }

    @Override
    public int hashCode() {

        return Objects.hash(reviewId, rating, title, author, date, reviewerName, reviewerCountry);
    }
}
