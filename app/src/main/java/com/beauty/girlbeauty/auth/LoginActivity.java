package com.beauty.girlbeauty.auth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.beauty.girlbeauty.R;
import com.beauty.girlbeauty.databinding.ActivityLoginBinding;
import com.beauty.girlbeauty.ui.HomepageActivity;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageView.setOnClickListener(view -> onBackPressed());

        binding.button.setOnClickListener(view -> formValidation());
    }

    private void formValidation() {
        String email = binding.email.getText().toString().trim();
        String password = binding.password.getText().toString().trim();
        if(email.isEmpty()) {
            Toast.makeText(this, "Email must be filled!", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Password must be filled!", Toast.LENGTH_SHORT).show();
        } else {

            binding.progressBar.setVisibility(View.VISIBLE);

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            binding.progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(LoginActivity.this, HomepageActivity.class));
                        } else {
                            binding.progressBar.setVisibility(View.GONE);
                            showFailureDialog();
                        }
                    });
        }
    }

    /// jika gagal login, munculkan alert dialog gagal
    private void showFailureDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Failure Login")
                .setMessage("Please check your email and password again, and check your internet connection too, and ty again login later!")
                .setIcon(R.drawable.ic_baseline_clear_24)
                .setPositiveButton("OKE", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}