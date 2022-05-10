package com.project.girlbeauty.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.girlbeauty.databinding.ActivityPersonalInfoBinding;

public class PersonalInfoActivity extends AppCompatActivity {


    private ActivityPersonalInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}