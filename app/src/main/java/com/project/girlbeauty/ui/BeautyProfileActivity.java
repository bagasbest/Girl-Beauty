package com.project.girlbeauty.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.girlbeauty.databinding.ActivityBeautyProfileBinding;

public class BeautyProfileActivity extends AppCompatActivity {

    private ActivityBeautyProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBeautyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}