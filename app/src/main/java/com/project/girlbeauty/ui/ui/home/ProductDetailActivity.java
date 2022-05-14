package com.project.girlbeauty.ui.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.girlbeauty.MainActivity;
import com.project.girlbeauty.R;
import com.project.girlbeauty.databinding.ActivityProductDetailBinding;
import com.project.girlbeauty.utils.ReviewAdapter;
import com.project.girlbeauty.utils.ReviewModel;
import com.project.girlbeauty.utils.ReviewViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "data";
    private ActivityProductDetailBinding binding;
    private ProductModel model;
    private ReviewAdapter adapter;
    private final ArrayList<ReviewModel> listReview = new ArrayList<>();
    private boolean isRecommended = false;
    private int totalUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            binding.noLogin.setVisibility(View.GONE);
            binding.content.setVisibility(View.VISIBLE);

            model = getIntent().getParcelableExtra(EXTRA_DATA);
            initRecyclerView();
            getRatingAndReview();
            getRecommendedUsers();
            Glide.with(this)
                    .load(model.getImage())
                    .into(binding.image);

            Glide.with(this)
                    .load(model.getAvailableIn())
                    .into(binding.availableOnImage);


            binding.name.setText(model.getName());
            binding.description.setText(model.getDescription());


            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            SharedPreferences prefs = getSharedPreferences("MySharedPref", MODE_PRIVATE);

            boolean isRecommendedBefore = prefs.getBoolean(uid + model.getUid(), false);
            if (isRecommendedBefore) {
                binding.recommendedBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_rounded_btn));
                binding.recommendedBtn.setTextColor(ContextCompat.getColor(this, R.color.white));
                isRecommended = true;
            }

            binding.backButton.setOnClickListener(view -> {
                onBackPressed();
            });

            binding.addReviewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupReview();
                }
            });

            binding.recommendedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkIsRecommendedBefore();
                }
            });

        } else {
            binding.loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ProductDetailActivity.this, MainActivity.class));
                }
            });
        }
    }

    @SuppressLint("ResourceType")
    private void checkIsRecommendedBefore() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        SharedPreferences prefs = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = prefs.edit();
        long userRecommended = model.getUserRecommended();
        if (isRecommended) {
            model.setUserRecommended(userRecommended - 1);
            getRecommendedUsers();
            binding.recommendedBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_border_phone));
            binding.recommendedBtn.setTextColor(ContextCompat.getColor(this, R.color.pink));
            isRecommended = false;

            myEdit.putBoolean(uid + model.getUid(), false);
        } else {
            model.setUserRecommended(userRecommended + 1);
            getRecommendedUsers();
            binding.recommendedBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_rounded_btn));
            binding.recommendedBtn.setTextColor(ContextCompat.getColor(this, R.color.white));
            isRecommended = true;

            myEdit.putBoolean(uid + model.getUid(), true);
        }
        updateRecommendedUser();
        myEdit.apply();
    }

    private void updateRecommendedUser() {
            FirebaseFirestore
                    .getInstance()
                    .collection("product")
                    .document(model.getUid())
                    .update("userRecommended", model.getUserRecommended());
    }

    private void showPopupReview() {
        Dialog dialog;
        Button btnSubmitRating, btnDismiss;
        EditText reviewEt;
        RatingBar ratingBar;
        ProgressBar pb;

        dialog = new Dialog(this);

        dialog.setContentView(R.layout.popup_rating);
        dialog.setCanceledOnTouchOutside(false);


        btnSubmitRating = dialog.findViewById(R.id.submitRating);
        btnDismiss = dialog.findViewById(R.id.dismissBtn);
        ratingBar = dialog.findViewById(R.id.ratingBar);
        pb = dialog.findViewById(R.id.progress_bar);
        reviewEt = dialog.findViewById(R.id.review);


        btnSubmitRating.setOnClickListener(view -> {
            String review = reviewEt.getText().toString().trim();

            if (ratingBar.getRating() == 0.0) {
                Toast.makeText(this, "Sorry, you must give rating from 1 - 5", Toast.LENGTH_SHORT).show();
            } else if (review.isEmpty()) {
                Toast.makeText(this, "Review must be filled", Toast.LENGTH_SHORT).show();
            } else {
                pb.setVisibility(View.VISIBLE);
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                saveRatingInDatabase(ratingBar.getRating(), pb, review, uid, dialog);
            }
        });

        btnDismiss.setOnClickListener(view -> dialog.dismiss());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void saveRatingInDatabase(float rating, ProgressBar pb, String review, String uid, Dialog dialog) {
        FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String username = "" + documentSnapshot.get("username");
                        String skinType = "" + documentSnapshot.get("skinType");
                        String skinTone = "" + documentSnapshot.get("skinTone");
                        String hairType = "" + documentSnapshot.get("hairType");
                        String image = "" + documentSnapshot.get("image");

                        Map<String, Object> users = new HashMap<>();
                        users.put("uid", uid);
                        users.put("name", username);
                        users.put("review", review);
                        users.put("beautyProfile", skinType + ", " + skinTone + ", " + hairType);
                        users.put("image", image);
                        users.put("rating", rating);


                        FirebaseFirestore
                                .getInstance()
                                .collection("product")
                                .document(model.getUid())
                                .collection(review)
                                .document(uid)
                                .set(users)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {

                                            /// get new review on db
                                            ReviewViewModel viewModel = new ViewModelProvider(ProductDetailActivity.this).get(ReviewViewModel.class);
                                            viewModel.setListReview(model.getUid());
                                            viewModel.getReview().observe(ProductDetailActivity.this, reviewList -> {
                                                if (reviewList.size() > 0) {
                                                    double totalStar = 0.0;
                                                    /// get average rating, get userReview
                                                    for (int i = 0; i < reviewList.size(); i++) {
                                                        totalStar += listReview.get(i).getRating();
                                                    }

                                                    Map<String, Object> data = new HashMap<>();
                                                    data.put("userReview", listReview.size());
                                                    data.put("rating", totalStar / Double.parseDouble(String.valueOf(listReview.size())));

                                                    FirebaseFirestore
                                                            .getInstance()
                                                            .collection("product")
                                                            .document(model.getUid())
                                                            .update(data)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        pb.setVisibility(View.GONE);
                                                                        Toast.makeText(ProductDetailActivity.this, "Success give rating", Toast.LENGTH_SHORT).show();
                                                                        initRecyclerView();
                                                                        getRatingAndReview();
                                                                    } else {
                                                                        pb.setVisibility(View.GONE);
                                                                        Toast.makeText(ProductDetailActivity.this, "Ups, failure to give rating and review, please check your internet connection!", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });

                                                }
                                            });


                                        } else {
                                            pb.setVisibility(View.GONE);
                                            Toast.makeText(ProductDetailActivity.this, "Ups, failure to give rating and review, please check your internet connection!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
    }

    private void getRecommendedUsers() {
        long recommendedUser = model.getUserRecommended();

        Log.e("taf", String.valueOf(recommendedUser));

        FirebaseFirestore
                .getInstance()
                .collection("users")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint({"DefaultLocale", "SetTextI18n"})
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        totalUser = queryDocumentSnapshots.size();
                        double percentage = (Double.parseDouble(String.valueOf(recommendedUser)) / Double.parseDouble(String.valueOf(totalUser))) * 100;
                        binding.userRecommended.setText(String.format("%.0f", percentage) + "% users\nRecommended this");
                    }
                });
    }

    private void initRecyclerView() {
        binding.reviewRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReviewAdapter();
        binding.reviewRv.setAdapter(adapter);
    }

    private void getRatingAndReview() {
        ReviewViewModel viewModel = new ViewModelProvider(this).get(ReviewViewModel.class);

        binding.progressBar.setVisibility(View.VISIBLE);
        viewModel.setListReview(model.getUid());
        viewModel.getReview().observe(this, reviewList -> {
            if (reviewList.size() > 0) {
                binding.noData.setVisibility(View.GONE);
                listReview.clear();
                listReview.addAll(reviewList);
                adapter.setData(listReview);
            } else {
                binding.noData.setVisibility(View.VISIBLE);
            }
            binding.progressBar.setVisibility(View.GONE);
        });

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void run() {

                /// get average rating
                double totalStar = 0.0;
                for (int i = 0; i < listReview.size(); i++) {
                    totalStar += listReview.get(i).getRating();
                }

                if (totalStar > 0) {
                    binding.rating.setText(String.format("%.1f", totalStar / Double.parseDouble(String.valueOf(listReview.size()))));
                }


                /// get total star
                int star5 = 0;
                int star4 = 0;
                int star3 = 0;
                int star2 = 0;
                int star1 = 0;
                for (int i = 0; i < listReview.size(); i++) {
                    if (listReview.get(i).getRating() == 1.0) {
                        star1++;
                    } else if (listReview.get(i).getRating() == 2.0) {
                        star2++;
                    } else if (listReview.get(i).getRating() == 3.0) {
                        star3++;
                    } else if (listReview.get(i).getRating() == 4.0) {
                        star4++;
                    } else if (listReview.get(i).getRating() == 5.0) {
                        star5++;
                    }
                }


                binding.star1.setText("" + star1);
                binding.star2.setText("" + star2);
                binding.star3.setText("" + star3);
                binding.star4.setText("" + star4);
                binding.star5.setText("" + star5);

                // Get the constraint layout of the parent constraint view.
                ConstraintLayout mConstraintLayout = binding.test;
                // Define a constraint set that will be used to modify the constraint layout parameters of the child.
                ConstraintSet mConstraintSet = new ConstraintSet();
                // Start with a copy the original constraints.
                mConstraintSet.clone(mConstraintLayout);


                double view1 = Double.parseDouble(String.valueOf(star1)) / Double.parseDouble(String.valueOf(listReview.size()));
                // Define new constraints for the child (or multiple children as the case may be).
                if (view1 <= 0.50) {
                    mConstraintSet.constrainPercentWidth(R.id.view17, Float.parseFloat(String.valueOf(view1)));
                } else if (view1 > 0.50) {
                    mConstraintSet.constrainPercentWidth(R.id.view17, 0.50F);
                }

                double view2 = Double.parseDouble(String.valueOf(star2)) / Double.parseDouble(String.valueOf(listReview.size()));
                // Define new constraints for the child (or multiple children as the case may be).
                if (view2 <= 0.50) {
                    mConstraintSet.constrainPercentWidth(R.id.view16, Float.parseFloat(String.valueOf(view2)));
                } else if (view2 > 0.50) {
                    mConstraintSet.constrainPercentWidth(R.id.view16, 0.50F);
                }

                double view3 = Double.parseDouble(String.valueOf(star3)) / Double.parseDouble(String.valueOf(listReview.size()));
                // Define new constraints for the child (or multiple children as the case may be).
                if (view3 <= 0.50) {
                    mConstraintSet.constrainPercentWidth(R.id.view15, Float.parseFloat(String.valueOf(view3)));
                } else if (view3 > 0.50) {
                    mConstraintSet.constrainPercentWidth(R.id.view15, 0.50F);
                }

                double view4 = Double.parseDouble(String.valueOf(star4)) / Double.parseDouble(String.valueOf(listReview.size()));
                // Define new constraints for the child (or multiple children as the case may be).
                if (view4 <= 0.50) {
                    mConstraintSet.constrainPercentWidth(R.id.view14, Float.parseFloat(String.valueOf(view4)));
                } else if (view4 > 0.50) {
                    mConstraintSet.constrainPercentWidth(R.id.view14, 0.50F);
                }

                double view5 = Double.parseDouble(String.valueOf(star5)) / Double.parseDouble(String.valueOf(listReview.size()));
                // Define new constraints for the child (or multiple children as the case may be).
                if (view5 <= 0.50) {
                    mConstraintSet.constrainPercentWidth(R.id.view13, Float.parseFloat(String.valueOf(view5)));
                } else if (view5 > 0.50) {
                    mConstraintSet.constrainPercentWidth(R.id.view13, 0.50F);
                }

                // Apply the constraints for the child view to the parent layout.
                mConstraintSet.applyTo(mConstraintLayout);
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}