package com.project.girlbeauty.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.girlbeauty.R;
import com.project.girlbeauty.databinding.ActivityBeautyProfileBinding;

import java.util.HashMap;
import java.util.Map;

public class BeautyProfileActivity extends AppCompatActivity {

    private ActivityBeautyProfileBinding binding;
    private String skinType;
    private String skinTone;
    private String skinUnderTone;
    private String hairType;
    private String coloredHair;
    private String hijabers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBeautyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1451418280345-67a6b4d10bba?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8ZHJ5JTIwc2tpbnxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60")
                .into(binding.dry);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1588170984641-a4861c805027?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80")
                .into(binding.normal);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1620056897337-0f940bf24eaf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8c2FuZHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60")
                .into(binding.combination);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1596486932489-f631d4ccceaa?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80")
                .into(binding.oily);


        Glide.with(this)
                .load("https://images.unsplash.com/photo-1514846326710-096e4a8035e0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80")
                .into(binding.light);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1605369572399-05d8d64a0f6e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80")
                .into(binding.mediumLight);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1623083619467-2ec52530881c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80")
                .into(binding.medium);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1626108870272-eff10b2b6cd6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80")
                .into(binding.mediumDark);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1581464907815-29bdb6343d3c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80")
                .into(binding.dark);


        Glide.with(this)
                .load("https://images.unsplash.com/photo-1615715616181-6ba85d724137?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80")
                .into(binding.cool);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1605184861861-237bd76dde11?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=395&q=80")
                .into(binding.neutral);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1603277578692-c699f37c67d3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80")
                .into(binding.warm);


        Glide.with(this)
                .load("https://images.unsplash.com/photo-1605980625600-88b46abafa8d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=464&q=80")
                .into(binding.wavy);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1519421692594-d7a3f3e3fe5f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80")
                .into(binding.straight);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1595218841793-d38b949402d2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=405&q=80")
                .into(binding.curly);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1546561925-a427a021303a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=371&q=80")
                .into(binding.yes);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1599407365109-d62ea7d81a42?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=383&q=80")
                .into(binding.no);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1619545307432-9fc73f8135ff?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=464&q=80")
                .into(binding.yes2);

        Glide.with(this)
                .load("https://images.unsplash.com/photo-1599842057874-37393e9342df?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80")
                .into(binding.no2);


        binding.dry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skinType = "Dry";
                binding.dry.setBorderColor(getResources().getColor(R.color.pink));
                binding.normal.setBorderColor(getResources().getColor(R.color.white));
                binding.combination.setBorderColor(getResources().getColor(R.color.white));
                binding.oily.setBorderColor(getResources().getColor(R.color.white));
            }
        });

        binding.normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skinType = "Normal";
                binding.dry.setBorderColor(getResources().getColor(R.color.white));
                binding.normal.setBorderColor(getResources().getColor(R.color.pink));
                binding.combination.setBorderColor(getResources().getColor(R.color.white));
                binding.oily.setBorderColor(getResources().getColor(R.color.white));
            }
        });

        binding.combination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skinType = "Combination";
                binding.dry.setBorderColor(getResources().getColor(R.color.white));
                binding.normal.setBorderColor(getResources().getColor(R.color.white));
                binding.combination.setBorderColor(getResources().getColor(R.color.pink));
                binding.oily.setBorderColor(getResources().getColor(R.color.white));
            }
        });

        binding.oily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skinType = "Oily";
                binding.dry.setBorderColor(getResources().getColor(R.color.white));
                binding.normal.setBorderColor(getResources().getColor(R.color.white));
                binding.combination.setBorderColor(getResources().getColor(R.color.white));
                binding.oily.setBorderColor(getResources().getColor(R.color.pink));
            }
        });

        binding.dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BeautyProfileActivity.this, HomepageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });



        binding.light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skinTone = "Light";
                binding.light.setBorderColor(getResources().getColor(R.color.pink));
                binding.mediumLight.setBorderColor(getResources().getColor(R.color.white));
                binding.medium.setBorderColor(getResources().getColor(R.color.white));
                binding.mediumDark.setBorderColor(getResources().getColor(R.color.white));
                binding.dark.setBorderColor(getResources().getColor(R.color.white));
            }
        });

        binding.mediumLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skinTone = "Medium Light";
                binding.light.setBorderColor(getResources().getColor(R.color.white));
                binding.mediumLight.setBorderColor(getResources().getColor(R.color.pink));
                binding.medium.setBorderColor(getResources().getColor(R.color.white));
                binding.mediumDark.setBorderColor(getResources().getColor(R.color.white));
                binding.dark.setBorderColor(getResources().getColor(R.color.white));
            }
        });

        binding.medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skinTone = "Light";
                binding.light.setBorderColor(getResources().getColor(R.color.white));
                binding.mediumLight.setBorderColor(getResources().getColor(R.color.white));
                binding.medium.setBorderColor(getResources().getColor(R.color.pink));
                binding.mediumDark.setBorderColor(getResources().getColor(R.color.white));
                binding.dark.setBorderColor(getResources().getColor(R.color.white));
            }
        });

        binding.mediumDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skinTone = "Medium Dark";
                binding.light.setBorderColor(getResources().getColor(R.color.white));
                binding.mediumLight.setBorderColor(getResources().getColor(R.color.white));
                binding.medium.setBorderColor(getResources().getColor(R.color.white));
                binding.mediumDark.setBorderColor(getResources().getColor(R.color.pink));
                binding.dark.setBorderColor(getResources().getColor(R.color.white));
            }
        });

        binding.dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skinTone = "Dark";
                binding.light.setBorderColor(getResources().getColor(R.color.white));
                binding.mediumLight.setBorderColor(getResources().getColor(R.color.white));
                binding.medium.setBorderColor(getResources().getColor(R.color.white));
                binding.mediumDark.setBorderColor(getResources().getColor(R.color.white));
                binding.dark.setBorderColor(getResources().getColor(R.color.pink));
            }
        });



        binding.cool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skinUnderTone = "Cool";
                binding.cool.setBorderColor(getResources().getColor(R.color.pink));
                binding.neutral.setBorderColor(getResources().getColor(R.color.white));
                binding.warm.setBorderColor(getResources().getColor(R.color.white));
            }
        });


        binding.neutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skinUnderTone = "Neutral";
                binding.cool.setBorderColor(getResources().getColor(R.color.white));
                binding.neutral.setBorderColor(getResources().getColor(R.color.pink));
                binding.warm.setBorderColor(getResources().getColor(R.color.white));
            }
        });

        binding.warm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skinUnderTone = "Warm";
                binding.cool.setBorderColor(getResources().getColor(R.color.white));
                binding.neutral.setBorderColor(getResources().getColor(R.color.white));
                binding.warm.setBorderColor(getResources().getColor(R.color.pink));
            }
        });




        binding.wavy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hairType = "Wavy";
                binding.wavy.setBorderColor(getResources().getColor(R.color.pink));
                binding.straight.setBorderColor(getResources().getColor(R.color.white));
                binding.curly.setBorderColor(getResources().getColor(R.color.white));
            }
        });


        binding.straight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hairType = "Straight";
                binding.wavy.setBorderColor(getResources().getColor(R.color.white));
                binding.straight.setBorderColor(getResources().getColor(R.color.pink));
                binding.curly.setBorderColor(getResources().getColor(R.color.white));
            }
        });

        binding.curly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hairType = "Curly";
                binding.wavy.setBorderColor(getResources().getColor(R.color.white));
                binding.straight.setBorderColor(getResources().getColor(R.color.white));
                binding.curly.setBorderColor(getResources().getColor(R.color.pink));
            }
        });


        binding.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coloredHair = "Colored Hair";
                binding.yes.setBorderColor(getResources().getColor(R.color.pink));
                binding.no.setBorderColor(getResources().getColor(R.color.white));
            }
        });


        binding.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coloredHair = "No Colored Hair";
                binding.yes.setBorderColor(getResources().getColor(R.color.white));
                binding.no.setBorderColor(getResources().getColor(R.color.pink));
            }
        });




        binding.yes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hijabers = "Hijabers";
                binding.yes2.setBorderColor(getResources().getColor(R.color.pink));
                binding.no2.setBorderColor(getResources().getColor(R.color.white));
            }
        });


        binding.no2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hijabers = "No Hijabers";
                binding.yes2.setBorderColor(getResources().getColor(R.color.white));
                binding.no2.setBorderColor(getResources().getColor(R.color.pink));
            }
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formValidation();
            }
        });


        binding.skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BeautyProfileActivity.this, BeautyConcernActivity.class));
            }
        });

    }

    private void formValidation() {
        if (skinType == null) {
            Toast.makeText(this, "Please choose skin type!", Toast.LENGTH_SHORT).show();
        } else if (skinTone == null) {
            Toast.makeText(this, "Please choose skin tone!", Toast.LENGTH_SHORT).show();
        } else if (skinUnderTone == null) {
            Toast.makeText(this, "Please choose skin under tone!", Toast.LENGTH_SHORT).show();
        } else if (hairType == null) {
            Toast.makeText(this, "Please choose hair type!", Toast.LENGTH_SHORT).show();
        } else if (coloredHair == null) {
            Toast.makeText(this, "Please choose colored hair or not!", Toast.LENGTH_SHORT).show();
        } else if (hijabers == null) {
            Toast.makeText(this, "Please choose hijaber or not!", Toast.LENGTH_SHORT).show();
        }
        else {
            binding.progressBar.setVisibility(View.VISIBLE);
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // SIMPAN DATA PERALATAN KAMERA KE DATABASE
            Map<String, Object> product = new HashMap<>();
            product.put("skinType", skinType);
            product.put("skinTone", skinTone);
            product.put("skinUnderTone", skinUnderTone);
            product.put("hairType", hairType);
            product.put("coloredHair", coloredHair);
            product.put("hijabers", hijabers);


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
                .setTitle("Failure Update Beauty Profile")
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
                .setTitle("Success Update Beauty Profile")
                .setMessage("Operation success")
                .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    binding.skipBtn.setVisibility(View.VISIBLE);
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}