package com.beauty.girlbeauty.ui.ui.home;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductModel implements Parcelable {

    private String name;
    private String nameTemp;
    private String uid;
    private String description;
    private Long price;
    private Long userReview;
    private Long userRecommended;
    private double rating;
    private String image;
    private String availableIn;

    public ProductModel() {
    }

    protected ProductModel(Parcel in) {
        name = in.readString();
        nameTemp = in.readString();
        uid = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readLong();
        }
        if (in.readByte() == 0) {
            userReview = null;
        } else {
            userReview = in.readLong();
        }
        if (in.readByte() == 0) {
            userRecommended = null;
        } else {
            userRecommended = in.readLong();
        }
        rating = in.readDouble();
        image = in.readString();
        availableIn = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(nameTemp);
        dest.writeString(uid);
        dest.writeString(description);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(price);
        }
        if (userReview == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(userReview);
        }
        if (userRecommended == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(userRecommended);
        }
        dest.writeDouble(rating);
        dest.writeString(image);
        dest.writeString(availableIn);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameTemp() {
        return nameTemp;
    }

    public void setNameTemp(String nameTemp) {
        this.nameTemp = nameTemp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getUserReview() {
        return userReview;
    }

    public void setUserReview(Long userReview) {
        this.userReview = userReview;
    }

    public Long getUserRecommended() {
        return userRecommended;
    }

    public void setUserRecommended(Long userRecommended) {
        this.userRecommended = userRecommended;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAvailableIn() {
        return availableIn;
    }

    public void setAvailableIn(String availableIn) {
        this.availableIn = availableIn;
    }
}
