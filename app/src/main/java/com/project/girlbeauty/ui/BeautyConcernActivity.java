package com.project.girlbeauty.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.girlbeauty.R;
import com.project.girlbeauty.databinding.ActivityBeautyConcernBinding;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BeautyConcernActivity extends AppCompatActivity {

    private ActivityBeautyConcernBinding binding;
    private Set<String> skinConcern = new HashSet<>();
    private Set<String> bodyConcern = new HashSet<>();
    private Set<String> hairConcern = new HashSet<>();
    private boolean isOnDehydrated = false;
    private boolean isOnAcne = false;
    private boolean isOnWrinkles = false;
    private boolean isOnSensitivity = false;
    private boolean isOnLargePores = false;
    private boolean isOnDullness = false;
    private boolean isOnHyperpigmentation1 = false;
    private boolean isOnRoughness = false;
    private boolean isOnAcneScars = false;
    private boolean isOnDarkUnderEyes = false;
    private boolean isOnSagging = false;
    private boolean isOnBlackOrWhiteHead = false;

    private boolean isOnStretchMark = false;
    private boolean isOnSensitivity2 = false;
    private boolean isOnDryness = false;
    private boolean isOnHyperpigmentation2= false;
    private boolean isOnCellulite = false;
    private boolean isOnBodyAcne = false;
    private boolean isOnUnevenSkinTone = false;
    private boolean isOnUnwantedHall = false;
    private boolean isOnDullness2 = false;
    private boolean isOnRoughness2 = false;

    private boolean isOnDamaged = false;
    private boolean isOnDandruff = false;
    private boolean isOnDryness3 = false;
    private boolean isOnFlatness = false;
    private boolean isOnFrizz = false;
    private boolean isOnGreyHair = false;
    private boolean isOnHairLoss = false;
    private boolean isOnOilyScalp = false;
    private boolean isOnSensitiveScalp = false;
    private boolean isOnSplitEnds = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBeautyConcernBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.dehydrated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnDehydrated) {
                    skinConcern.add("Dehydrated");
                    binding.dehydrated.setTextColor(getResources().getColor(R.color.pink));
                    isOnDehydrated = true;
                } else {
                    binding.dehydrated.setTextColor(getResources().getColor(R.color.black));
                    isOnDehydrated = false;
                }
            }
        });

        binding.acne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnAcne) {
                    skinConcern.add("Acne");
                    binding.acne.setTextColor(getResources().getColor(R.color.pink));
                    isOnAcne = true;
                } else {
                    binding.acne.setTextColor(getResources().getColor(R.color.black));
                    isOnAcne = false;
                }
            }
        });


        binding.wrinkles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnWrinkles) {
                    skinConcern.add("Wrinkles");
                    binding.wrinkles.setTextColor(getResources().getColor(R.color.pink));
                    isOnWrinkles = true;
                } else {
                    binding.wrinkles.setTextColor(getResources().getColor(R.color.black));
                    isOnWrinkles = false;
                }
            }
        });

        binding.sensitivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnSensitivity) {
                    skinConcern.add("Sensitivity");
                    binding.sensitivity.setTextColor(getResources().getColor(R.color.pink));
                    isOnSensitivity = true;
                } else {
                    binding.sensitivity.setTextColor(getResources().getColor(R.color.black));
                    isOnSensitivity = false;
                }
            }
        });

        binding.largePores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnLargePores) {
                    skinConcern.add("Large Pores");
                    binding.largePores.setTextColor(getResources().getColor(R.color.pink));
                    isOnLargePores = true;
                } else {
                    binding.largePores.setTextColor(getResources().getColor(R.color.black));
                    isOnLargePores = false;
                }

            }
        });


        binding.dullnes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnDullness) {
                    skinConcern.add("Dullness");
                    binding.dullnes.setTextColor(getResources().getColor(R.color.pink));
                    isOnDullness = true;
                } else {
                    binding.dullnes.setTextColor(getResources().getColor(R.color.black));
                    isOnDullness = false;
                }
            }
        });


        binding.hyperpigmentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnHyperpigmentation1) {
                    skinConcern.add("Hyperpigmentation / Uneven Skin Tone");
                    binding.hyperpigmentation.setTextColor(getResources().getColor(R.color.pink));
                    isOnHyperpigmentation1 = true;
                } else {
                    binding.hyperpigmentation.setTextColor(getResources().getColor(R.color.black));
                    isOnHyperpigmentation1 = false;
                }
            }
        });

        binding.roughness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnRoughness) {
                    skinConcern.add("Roughness");
                    binding.roughness.setTextColor(getResources().getColor(R.color.pink));
                    isOnRoughness = true;
                } else {
                    binding.roughness.setTextColor(getResources().getColor(R.color.black));
                    isOnRoughness = false;
                }

            }
        });


        binding.acneScars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnAcneScars) {
                    skinConcern.add("Acne Scars");
                    binding.acneScars.setTextColor(getResources().getColor(R.color.pink));
                    isOnAcneScars = true;
                } else {
                    binding.acneScars.setTextColor(getResources().getColor(R.color.black));
                    isOnAcneScars = false;
                }

            }
        });

        binding.darkUndereyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnDarkUnderEyes) {
                    skinConcern.add("Dark Under Eyes");
                    binding.darkUndereyes.setTextColor(getResources().getColor(R.color.pink));
                    isOnDarkUnderEyes = true;
                } else {
                    binding.darkUndereyes.setTextColor(getResources().getColor(R.color.black));
                    isOnDarkUnderEyes = false;
                }

            }
        });


        binding.Sagging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnSagging) {
                    skinConcern.add("Sagging");
                    binding.Sagging.setTextColor(getResources().getColor(R.color.pink));
                    isOnSagging = true;
                } else {
                    binding.Sagging.setTextColor(getResources().getColor(R.color.black));
                    isOnSagging = false;
                }

            }
        });



        binding.blackOrWhiteHeads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnBlackOrWhiteHead) {
                    skinConcern.add("Black or White Heads");
                    binding.blackOrWhiteHeads.setTextColor(getResources().getColor(R.color.pink));
                    isOnBlackOrWhiteHead = true;
                } else {
                    binding.blackOrWhiteHeads.setTextColor(getResources().getColor(R.color.black));
                    isOnBlackOrWhiteHead = false;
                }

            }
        });




        binding.strechmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnStretchMark) {
                    bodyConcern.add("Stretch Mark");
                    binding.strechmark.setTextColor(getResources().getColor(R.color.pink));
                    isOnStretchMark = true;
                } else {
                    binding.strechmark.setTextColor(getResources().getColor(R.color.black));
                    isOnStretchMark = false;
                }

            }
        });


        binding.sensitivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnSensitivity2) {
                    bodyConcern.add("Sensitivity");
                    binding.sensitivity2.setTextColor(getResources().getColor(R.color.pink));
                    isOnSensitivity2 = true;
                } else {
                    binding.sensitivity2.setTextColor(getResources().getColor(R.color.black));
                    isOnSensitivity2 = false;
                }

            }
        });

        binding.dryness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnDryness) {
                    bodyConcern.add("Dryness");
                    binding.dryness.setTextColor(getResources().getColor(R.color.pink));
                    isOnDryness = true;
                } else {
                    binding.dryness.setTextColor(getResources().getColor(R.color.black));
                    isOnDryness = false;
                }

            }
        });


        binding.hyperpigmentation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnHyperpigmentation2) {
                    bodyConcern.add("Hyperpigmentation");
                    binding.hyperpigmentation2.setTextColor(getResources().getColor(R.color.pink));
                    isOnHyperpigmentation2 = true;
                } else {
                    binding.hyperpigmentation2.setTextColor(getResources().getColor(R.color.black));
                    isOnHyperpigmentation2 = false;
                }

            }
        });

        binding.cellulite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnCellulite) {
                    bodyConcern.add("Cellulite");
                    binding.cellulite.setTextColor(getResources().getColor(R.color.pink));
                    isOnCellulite = true;
                } else {
                    binding.cellulite.setTextColor(getResources().getColor(R.color.black));
                    isOnCellulite = false;
                }

            }
        });


        binding.bodyAcne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnBodyAcne) {
                    bodyConcern.add("Body Acne");
                    binding.bodyAcne.setTextColor(getResources().getColor(R.color.pink));
                    isOnBodyAcne = true;
                } else {
                    binding.bodyAcne.setTextColor(getResources().getColor(R.color.black));
                    isOnBodyAcne = false;
                }

            }
        });

        binding.unevenSkinTone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnUnevenSkinTone) {
                    bodyConcern.add("Uneven Skin Tone");
                    binding.unevenSkinTone.setTextColor(getResources().getColor(R.color.pink));
                    isOnUnevenSkinTone = true;
                } else {
                    binding.unevenSkinTone.setTextColor(getResources().getColor(R.color.black));
                    isOnUnevenSkinTone = false;
                }

            }
        });


        binding.unwantedHall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnUnwantedHall) {
                    bodyConcern.add("Unwanted Hall");
                    binding.unwantedHall.setTextColor(getResources().getColor(R.color.pink));
                    isOnUnwantedHall = true;
                } else {
                    binding.unwantedHall.setTextColor(getResources().getColor(R.color.black));
                    isOnUnwantedHall = false;
                }

            }
        });

        binding.dullnes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnDullness2) {
                    bodyConcern.add("Dullness");
                    binding.dullnes2.setTextColor(getResources().getColor(R.color.pink));
                    isOnDullness2 = true;
                } else {
                    binding.dullnes2.setTextColor(getResources().getColor(R.color.black));
                    isOnDullness2 = false;
                }

            }
        });

        binding.roughness2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnRoughness2) {
                    bodyConcern.add("Roughness");
                    binding.roughness2.setTextColor(getResources().getColor(R.color.pink));
                    isOnRoughness2 = true;
                } else {
                    binding.roughness2.setTextColor(getResources().getColor(R.color.black));
                    isOnRoughness2 = false;
                }

            }
        });


        binding.damaged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnDamaged) {
                    hairConcern.add("Damaged");
                    binding.damaged.setTextColor(getResources().getColor(R.color.pink));
                    isOnDamaged = true;
                } else {
                    binding.damaged.setTextColor(getResources().getColor(R.color.black));
                    isOnDamaged = false;
                }

            }
        });

        binding.dandruff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnDandruff) {
                    hairConcern.add("Dandruff");
                    binding.dandruff.setTextColor(getResources().getColor(R.color.pink));
                    isOnDandruff = true;
                } else {
                    binding.dandruff.setTextColor(getResources().getColor(R.color.black));
                    isOnDandruff = false;
                }

            }
        });
        binding.dryness3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnDryness3) {
                    hairConcern.add("Dryness");
                    binding.dryness3.setTextColor(getResources().getColor(R.color.pink));
                    isOnDryness3 = true;
                } else {
                    binding.dryness3.setTextColor(getResources().getColor(R.color.black));
                    isOnDryness3 = false;
                }

            }
        });
        binding.flatness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnFlatness) {
                    hairConcern.add("Flatness");
                    binding.flatness.setTextColor(getResources().getColor(R.color.pink));
                    isOnFlatness = true;
                } else {
                    binding.flatness.setTextColor(getResources().getColor(R.color.black));
                    isOnFlatness = false;
                }

            }
        });

        binding.frizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnFrizz) {
                    hairConcern.add("Frizz");
                    binding.frizz.setTextColor(getResources().getColor(R.color.pink));
                    isOnFrizz = true;
                } else {
                    binding.frizz.setTextColor(getResources().getColor(R.color.black));
                    isOnFrizz = false;
                }

            }
        });
        binding.greyHair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnGreyHair) {
                    hairConcern.add("Grey Hair");
                    binding.greyHair.setTextColor(getResources().getColor(R.color.pink));
                    isOnGreyHair = true;
                } else {
                    binding.greyHair.setTextColor(getResources().getColor(R.color.black));
                    isOnGreyHair = false;
                }

            }
        });
        binding.hairLoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnHairLoss) {
                    hairConcern.add("Hair Loss");
                    binding.hairLoss.setTextColor(getResources().getColor(R.color.pink));
                    isOnHairLoss = true;
                } else {
                    binding.hairLoss.setTextColor(getResources().getColor(R.color.black));
                    isOnHairLoss = false;
                }

            }
        });
        binding.oilyScalp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnOilyScalp) {
                    hairConcern.add("Oily Scalp");
                    binding.oilyScalp.setTextColor(getResources().getColor(R.color.pink));
                    isOnOilyScalp = true;
                } else {
                    binding.oilyScalp.setTextColor(getResources().getColor(R.color.black));
                    isOnOilyScalp = false;
                }

            }
        });

        binding.sensitiveScalp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnSensitiveScalp) {
                    hairConcern.add("Sensitive Scalp");
                    binding.sensitiveScalp.setTextColor(getResources().getColor(R.color.pink));
                    isOnSensitiveScalp = true;
                } else {
                    binding.sensitiveScalp.setTextColor(getResources().getColor(R.color.black));
                    isOnSensitiveScalp = false;
                }

            }
        });
        binding.splitEnds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnSplitEnds) {
                    hairConcern.add("Split Ends");
                    binding.splitEnds.setTextColor(getResources().getColor(R.color.pink));
                    isOnSplitEnds = true;
                } else {
                    binding.splitEnds.setTextColor(getResources().getColor(R.color.black));
                    isOnSplitEnds = false;
                }

            }
        });

        binding.dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BeautyConcernActivity.this, HomepageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formValidation();
            }
        });
    }

    private void formValidation() {
        if(skinConcern == null) {
            Toast.makeText(this, "You must picked 1 Skin Concern", Toast.LENGTH_SHORT).show();
        } else if (bodyConcern == null) {
            Toast.makeText(this, "You must picked 1 Body Concern", Toast.LENGTH_SHORT).show();
        } else if (hairConcern == null) {
            Toast.makeText(this, "You must picked 1 Hair Concern", Toast.LENGTH_SHORT).show();
        } else {
            binding.progressBar.setVisibility(View.VISIBLE);
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // SIMPAN DATA PERALATAN KAMERA KE DATABASE
            Map<String, Object> product = new HashMap<>();
            product.put("skinConcern", skinConcern);
            product.put("bodyConcern", bodyConcern);
            product.put("hairConcern", hairConcern);


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
                .setTitle("Failure Update Beauty Concern")
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
                .setTitle("Success Update Beauty Concern")
                .setMessage("Operation success")
                .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
                .setPositiveButton("OK", (dialogInterface, i) -> {
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