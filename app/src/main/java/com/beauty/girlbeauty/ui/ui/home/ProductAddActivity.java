package com.beauty.girlbeauty.ui.ui.home;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.beauty.girlbeauty.R;
import com.beauty.girlbeauty.databinding.ActivityProductAddBinding;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProductAddActivity extends AppCompatActivity {

    private ActivityProductAddBinding binding;
    private String dp;
    private String availableIn;
    private static final int REQUEST_FROM_GALLERY = 1001;
    private static final int REQUEST_FROM_AVAILABLE_IN = 1002;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageHint.setOnClickListener(view12 -> {
            ImagePicker.with(this)
                    .galleryOnly()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start(REQUEST_FROM_GALLERY);
        });

        binding.availableOnImageHint.setOnClickListener(view13 -> {
            ImagePicker.with(this)
                    .galleryOnly()
                    .compress(1024)
                    .start(REQUEST_FROM_AVAILABLE_IN);
        });

        binding.saveBtn.setOnClickListener(view14 -> formValidation());

    }

    private void formValidation() {
        String name = binding.productName.getText().toString().trim();
        String description = binding.productDescription.getText().toString().trim();
        String price = binding.productPrice.getText().toString();

        /// ini merpakan validasi kolom inputan, semua kolom wajib diisi
        if (name.isEmpty()) {
            Toast.makeText(this, "Product name must be filled!", Toast.LENGTH_SHORT).show();
        } else if (description.isEmpty()) {
            Toast.makeText(this, "Product description must be filled!", Toast.LENGTH_SHORT).show();
        } else if (price.isEmpty()) {
            Toast.makeText(this, "Product price must be filled!", Toast.LENGTH_SHORT).show();
        } else if(dp == null) {
            Toast.makeText(this, "Product image must be filled!", Toast.LENGTH_SHORT).show();
        } else if (availableIn == null){
            Toast.makeText(this, "Available in, must be filled!", Toast.LENGTH_SHORT).show();
        }
        else {
            binding.progressBar.setVisibility(View.VISIBLE);
            String uid = String.valueOf(System.currentTimeMillis());

            // SIMPAN DATA PERALATAN KAMERA KE DATABASE
            Map<String, Object> product = new HashMap<>();
            product.put("name", name);
            product.put("nameTemp", name.toLowerCase());
            product.put("description", description);
            product.put("image", dp);
            product.put("availableIn", availableIn);
            product.put("price", Long.parseLong(price));
            product.put("userReview", 0);
            product.put("userRecommended", 0);
            product.put("rating", 0.0);
            product.put("uid", uid);

            FirebaseFirestore
                    .getInstance()
                    .collection("product")
                    .document(uid)
                    .set(product)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            binding.progressBar.setVisibility(View.GONE);
                            showSuccessDialog();
                        } else {
                            binding.progressBar.setVisibility(View.GONE);
                            showFailureDialog();
                        }
                    });
        }
    }

    /// tampilkan dialog box ketika gagal mengupload
    private void showFailureDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Failure Upload Product")
                .setMessage("Ups, your internet connection fail to register, please check your internet connection and try again later!")
                .setIcon(R.drawable.ic_baseline_clear_24)
                .setPositiveButton("OKE", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .show();
    }

    /// tampilkan dialog box ketika sukses mengupload
    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Success Upload Product")
                .setMessage("Product will be released soon!")
                .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
                .setPositiveButton("OKE", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    binding.productName.setText("");
                    binding.productDescription.setText("");
                    binding.productPrice.setText("");
                    dp = null;
                    availableIn = null;
                })
                .show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_FROM_GALLERY) {
                uploadDp(data.getData(), "image");
            } else if (requestCode == REQUEST_FROM_AVAILABLE_IN){
                uploadDp(data.getData(), "available");
            }
        }
    }



    /// fungsi untuk mengupload foto kedalam cloud storage
    private void uploadDp(Uri data, String option) {
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Uploading process...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        String imageFileName = option + "/data_" + System.currentTimeMillis() + ".png";

        mStorageRef.child(imageFileName).putFile(data)
                .addOnSuccessListener(taskSnapshot ->
                        mStorageRef.child(imageFileName).getDownloadUrl()
                                .addOnSuccessListener(uri -> {
                                    mProgressDialog.dismiss();
                                    if (Objects.equals(option, "image")) {
                                        dp = uri.toString();
                                        Glide
                                                .with(this)
                                                .load(dp)
                                                .into(binding.image);
                                    } else {
                                        availableIn = uri.toString();
                                        Glide
                                                .with(this)
                                                .load(availableIn)
                                                .into(binding.availableOnImage);
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(this, "Failure upload image", Toast.LENGTH_SHORT).show();
                                    Log.d("imageDp: ", e.toString());
                                }))
                .addOnFailureListener(e -> {
                    mProgressDialog.dismiss();
                    Toast.makeText(this, "Failure upload image", Toast.LENGTH_SHORT).show();
                    Log.d("imageDp: ", e.toString());
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}