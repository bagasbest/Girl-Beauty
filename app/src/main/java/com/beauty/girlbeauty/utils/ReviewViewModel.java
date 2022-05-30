package com.beauty.girlbeauty.utils;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ReviewViewModel extends ViewModel {


    private final MutableLiveData<ArrayList<ReviewModel>> listReview = new MutableLiveData<>();
    final ArrayList<ReviewModel> reviewModelArrayList = new ArrayList<>();

    private static final String TAG = ReviewViewModel.class.getSimpleName();

    public void setListReview(String productId) {
        reviewModelArrayList.clear();

        try {
            FirebaseFirestore
                    .getInstance()
                    .collection("product")
                    .document(productId)
                    .collection("review")
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                ReviewModel model = new ReviewModel();

                                model.setUid("" + document.get("uid"));
                                model.setBeautyProfile("" + document.get("beautyProfile"));
                                model.setImage("" + document.get("image"));
                                model.setName("" + document.get("name"));
                                model.setReview("" + document.get("review"));
                                model.setRating(document.getDouble("rating"));

                                reviewModelArrayList.add(model);
                            }
                            listReview.postValue(reviewModelArrayList);
                        } else {
                            Log.e(TAG, task.toString());
                        }
                    });
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public LiveData<ArrayList<ReviewModel>> getReview() {
        return listReview;
    }

}
