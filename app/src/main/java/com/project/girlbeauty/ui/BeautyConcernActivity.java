package com.project.girlbeauty.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.girlbeauty.databinding.ActivityBeautyConcernBinding;

public class BeautyConcernActivity extends AppCompatActivity {

    private ActivityBeautyConcernBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBeautyConcernBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}