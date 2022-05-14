package com.project.girlbeauty.utils;

public class ReviewModel {

    private String uid;
    private String name;
    private String review;
    private String beautyProfile;
    private String image;
    private double rating;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getBeautyProfile() {
        return beautyProfile;
    }

    public void setBeautyProfile(String beautyProfile) {
        this.beautyProfile = beautyProfile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
