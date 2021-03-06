package com.beauty.girlbeauty.ui.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ProductVIewModel extends ViewModel {

    private final MutableLiveData<ArrayList<ProductModel>> listProduct = new MutableLiveData<>();
    final ArrayList<ProductModel> productModelArrayList = new ArrayList<>();

    private static final String TAG = ProductVIewModel.class.getSimpleName();

    public void setProductList() {
        productModelArrayList.clear();

        try {
            FirebaseFirestore
                    .getInstance()
                    .collection("product")
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                ProductModel model = new ProductModel();

                                model.setUid("" + document.get("uid"));
                                model.setName("" + document.get("name"));
                                model.setNameTemp("" + document.get("nameTemp"));
                                model.setDescription("" + document.get("description"));
                                model.setImage("" + document.get("image"));
                                model.setAvailableIn("" + document.get("availableIn"));
                                model.setPrice(document.getLong("price"));
                                model.setUserRecommended(document.getLong("userRecommended"));
                                model.setRating(document.getDouble("rating"));
                                model.setUserReview(document.getLong("userReview"));


                                productModelArrayList.add(model);
                            }
                            listProduct.postValue(productModelArrayList);
                        } else {
                            Log.e(TAG, task.toString());
                        }
                    });
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void setProductListByQuery(String query) {
        productModelArrayList.clear();

        try {
            FirebaseFirestore
                    .getInstance()
                    .collection("product")
                    .whereGreaterThanOrEqualTo("nameTemp", query)
                    .whereLessThanOrEqualTo("nameTemp", query + '\uf8ff')
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                ProductModel model = new ProductModel();

                                model.setUid("" + document.get("uid"));
                                model.setName("" + document.get("name"));
                                model.setNameTemp("" + document.get("nameTemp"));
                                model.setDescription("" + document.get("description"));
                                model.setImage("" + document.get("image"));
                                model.setAvailableIn("" + document.get("availableIn"));
                                model.setPrice(document.getLong("price"));
                                model.setUserRecommended(document.getLong("userRecommended"));
                                model.setRating(document.getDouble("rating"));
                                model.setUserReview(document.getLong("userReview"));

                                productModelArrayList.add(model);
                            }
                            listProduct.postValue(productModelArrayList);
                        } else {
                            Log.e(TAG, task.toString());
                        }
                    });
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void setProductListByUid(String uid) {
        productModelArrayList.clear();

        try {
            FirebaseFirestore
                    .getInstance()
                    .collection("product")
                    .whereEqualTo("userId", uid)
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                ProductModel model = new ProductModel();

                                model.setUid("" + document.get("uid"));
                                model.setName("" + document.get("name"));
                                model.setNameTemp("" + document.get("nameTemp"));
                                model.setDescription("" + document.get("description"));
                                model.setImage("" + document.get("image"));
                                model.setAvailableIn("" + document.get("availableIn"));
                                model.setPrice(document.getLong("price"));
                                model.setUserRecommended(document.getLong("userRecommended"));
                                model.setRating(document.getDouble("rating"));
                                model.setUserReview(document.getLong("userReview"));


                                productModelArrayList.add(model);
                            }
                            listProduct.postValue(productModelArrayList);
                        } else {
                            Log.e(TAG, task.toString());
                        }
                    });
        } catch (Exception error) {
            error.printStackTrace();
        }
    }


    public LiveData<ArrayList<ProductModel>> getProduct() {
        return listProduct;
    }



}
