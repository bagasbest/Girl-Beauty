package com.project.girlbeauty.ui;

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
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.girlbeauty.R;
import com.project.girlbeauty.databinding.ActivityPersonalInfoBinding;
import java.util.HashMap;
import java.util.Map;

public class PersonalInfoActivity extends AppCompatActivity {


    private ActivityPersonalInfoBinding binding;
    private String dp;
    private static final int REQUEST_FROM_GALLERY = 1001;
    private ProgressDialog mProgressDialog;
    private String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.imageHint.setOnClickListener(view12 -> {
            ImagePicker.with(this)
                    .galleryOnly()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start(REQUEST_FROM_GALLERY);
        });

        binding.radioGroup.setOnCheckedChangeListener((radioGroup, id) -> {
            switch (id) {
                case R.id.male:
                    gender = "Male";
                    break;
                case R.id.female:
                    gender = "Female";
                    break;
            }
        });

        binding.saveBtn.setOnClickListener(view14 -> formValidation());

        binding.skipBtn.setOnClickListener(view -> startActivity(new Intent(PersonalInfoActivity.this, BeautyProfileActivity.class)));
    }

    private void formValidation() {
        String fullName = binding.fullName.getText().toString().trim();
        String location = binding.location.getText().toString();

        /// ini merpakan validasi kolom inputan, semua kolom wajib diisi
        if (fullName.isEmpty()) {
            Toast.makeText(this, "Full Name must be filled!", Toast.LENGTH_SHORT).show();
        } else if (dp == null) {
            Toast.makeText(this, "User Image must be filled!", Toast.LENGTH_SHORT).show();
        } else if (gender == null) {
            Toast.makeText(this, "Gender must be filled!", Toast.LENGTH_SHORT).show();
        } else if (location.isEmpty()) {
            Toast.makeText(this, "Location must be filled!", Toast.LENGTH_SHORT).show();
        }
        else {
            binding.progressBar.setVisibility(View.VISIBLE);
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // SIMPAN DATA PERALATAN KAMERA KE DATABASE
            Map<String, Object> product = new HashMap<>();
            product.put("fullName", fullName);
            product.put("gender", gender);
            product.put("location", location);
            product.put("image", dp);


            FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(userId)
                    .update(product)
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
                .setTitle("Failure Update Personal Info")
                .setMessage("Ups, your internet connection fail to register, please check your internet connection and try again later!")
                .setIcon(R.drawable.ic_baseline_clear_24)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .show();
    }

    /// tampilkan dialog box ketika sukses mengupload
    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Failure Update Personal Info")
                .setMessage("Operation success")
                .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    binding.skipBtn.setVisibility(View.VISIBLE);
                })
                .show();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_FROM_GALLERY) {
                uploadDp(data.getData());
            }
        }
    }



    /// fungsi untuk mengupload foto kedalam cloud storage
    private void uploadDp(Uri data) {
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Uploading process...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        String imageFileName = "users/data_" + System.currentTimeMillis() + ".png";

        mStorageRef.child(imageFileName).putFile(data)
                .addOnSuccessListener(taskSnapshot ->
                        mStorageRef.child(imageFileName).getDownloadUrl()
                                .addOnSuccessListener(uri -> {
                                    mProgressDialog.dismiss();
                                    dp = uri.toString();
                                    Glide
                                            .with(this)
                                            .load(dp)
                                            .into(binding.image);
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