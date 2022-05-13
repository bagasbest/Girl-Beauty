package com.project.girlbeauty.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.girlbeauty.R;
import com.project.girlbeauty.databinding.ActivityRegisterBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private String phone;
    private String getBackendOtp;
    private long timeLeftInMillis;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.imageView.setOnClickListener(view -> {
            countDownTimer.cancel();
            onBackPressed();
        });

        binding.nextBtn.setOnClickListener(view -> {
            phone = binding.phone.getText().toString().trim();
            if (phone.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Phone number must be filled!", Toast.LENGTH_SHORT).show();
            } else if(phone.charAt(0) != '+') {
                Toast.makeText(RegisterActivity.this, "Phone number must followed by code +62", Toast.LENGTH_SHORT).show();
            }
            else {
                binding.phone.setVisibility(View.GONE);
                binding.textView.setVisibility(View.GONE);
                binding.textView3.setVisibility(View.GONE);
                binding.nextBtn.setVisibility(View.GONE);
                binding.textView2.setVisibility(View.VISIBLE);
                binding.textView4.setVisibility(View.VISIBLE);
                binding.phoneNumber.setVisibility(View.VISIBLE);
                binding.linearLayout.setVisibility(View.VISIBLE);
                binding.countdownTimer.setVisibility(View.VISIBLE);
                binding.verifyBtn.setVisibility(View.VISIBLE);

                sendOTP();
                moveToNext();
                countdownTimer();
                binding.phoneNumber.setText(phone);
            }
        });


        binding.verifyBtn.setOnClickListener(view -> formValidationOTP());

        binding.countdownTimer.setOnClickListener(view -> {
            Log.e("taf", phone);
            sendOTP();
            countdownTimer();
        });

        binding.registerBtn.setOnClickListener(view -> formValidationRegister());

    }

    private void moveToNext() {
        binding.otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    binding.otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    binding.otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    binding.otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    binding.otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    binding.otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void formValidationRegister() {
        String email = binding.email.getText().toString().trim();
        String username = binding.username.getText().toString().trim();
        String password = binding.password.getText().toString().trim();
        String dob = binding.dob.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Email must be filled", Toast.LENGTH_SHORT).show();
        } else if (username.isEmpty()) {
            Toast.makeText(this, "Username must be filled", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Password must be filled", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(this, "Password minimum 6 character", Toast.LENGTH_SHORT).show();
        } else if (dob.isEmpty()) {
            Toast.makeText(this, "Date of birth must be filled", Toast.LENGTH_SHORT).show();
        }
        else {

            binding.progressBar.setVisibility(View.VISIBLE);
            FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            saveDataToDB(email, username, dob);
                        } else {
                            binding.progressBar.setVisibility(View.GONE);
                            try {
                                throw Objects.requireNonNull(task.getException());
                            } catch (FirebaseAuthUserCollisionException error) {
                                showFailureDialog("This email already registered, pick another email");
                            } catch (java.lang.Exception error) {
                                Log.e("TAG", error.getMessage());
                            }
                        }
                    });
        }
    }

    private void saveDataToDB(String email, String username, String dob) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HashMap<String, Object> data = new HashMap<>();
        data.put("uid", uid);
        data.put("username", username);
        data.put("email", email);
        data.put("phone", phone);
        data.put("dob", dob);
        data.put("role", "user");
        data.put("image", "");
        data.put("fullName", "");
        data.put("gender", "");
        data.put("location", "");
        data.put("skinType", "");
        data.put("skinTone", "");
        data.put("skinUnderTone", "");
        data.put("hairType", "");
        data.put("coloredHair", "");
        data.put("hijabers", "");
        data.put("skinConcern", null);
        data.put("bodyConcern", null);
        data.put("hairConcern", null);

        FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(uid)
                .set(data)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        binding.progressBar.setVisibility(View.GONE);
                        showSuccessDialog();
                    } else {
                        binding.progressBar.setVisibility(View.GONE);
                        showFailureDialog("Ups, your internet connection fail to register, please check your internet connection and try again later!");
                    }
                });
    }

    /// munculkan dialog ketika gagal registrasi
    private void showFailureDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Failure Register")
                .setMessage(message)
                .setIcon(R.drawable.ic_baseline_clear_24)
                .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    /// munculkan dialog ketika sukses registrasi
    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Success Register")
                .setMessage("Please Login")
                .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    onBackPressed();
                })
                .show();
    }

    private void formValidationOTP() {
        String et1 = binding.otp1.getText().toString().trim();
        String et2 = binding.otp2.getText().toString().trim();
        String et3 = binding.otp3.getText().toString().trim();
        String et4 = binding.otp4.getText().toString().trim();
        String et5 = binding.otp5.getText().toString().trim();
        String et6 = binding.otp6.getText().toString().trim();

        if (et1.isEmpty() || et2.isEmpty() || et3.isEmpty() || et4.isEmpty() || et5.isEmpty() || et6.isEmpty()) {
            Toast.makeText(this, "Kolom OTP tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            String getUserOtp = et1 + et2 + et3 + et4 + et5 + et6;

            if (getBackendOtp != null) {
                binding.verifyBtn.setEnabled(false);
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getBackendOtp, getUserOtp);
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                binding.textView2.setVisibility(View.GONE);
                                binding.textView4.setVisibility(View.GONE);
                                binding.phoneNumber.setVisibility(View.GONE);
                                binding.linearLayout.setVisibility(View.GONE);
                                binding.countdownTimer.setVisibility(View.GONE);
                                binding.verifyBtn.setVisibility(View.GONE);

                                binding.complete1.setVisibility(View.VISIBLE);
                                binding.textView5.setVisibility(View.VISIBLE);
                                binding.linearLayout2.setVisibility(View.VISIBLE);
                            } else {
                                binding.verifyBtn.setEnabled(true);
                                Toast.makeText(RegisterActivity.this, "Enter corrent OTP", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }

    private void sendOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                RegisterActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String backendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        getBackendOtp = backendOtp;
                        Toast.makeText(RegisterActivity.this, "OTP Send Successfully!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void countdownTimer() {
        timeLeftInMillis = 60000;
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                updateTimer();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                binding.countdownTimer.setEnabled(true);
                binding.countdownTimer.setText("Resend OTP");
            }
        }.start();
    }

    private void updateTimer() {
        int second = (int) timeLeftInMillis / 1000;
        String time = "00:" + second;
        binding.countdownTimer.setText(time);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            countDownTimer.cancel();
            onBackPressed();
            return true;
        }
        return false;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}