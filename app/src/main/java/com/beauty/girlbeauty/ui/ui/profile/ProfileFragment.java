package com.beauty.girlbeauty.ui.ui.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.beauty.girlbeauty.MainActivity;
import com.beauty.girlbeauty.R;
import com.beauty.girlbeauty.databinding.FragmentProfileBinding;
import com.beauty.girlbeauty.ui.ui.home.ProductAdapter;
import com.beauty.girlbeauty.ui.ui.home.ProductVIewModel;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProductAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        checkLogin();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    private void initRecyclerView() {
        binding.rvProduct.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new ProductAdapter();
        binding.rvProduct.setAdapter(adapter);
    }

    private void initViewModel() {
        ProductVIewModel viewModel = new ViewModelProvider(this).get(ProductVIewModel.class);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        binding.progressBar.setVisibility(View.VISIBLE);
        viewModel.setProductListByUid(uid);
        viewModel.getProduct().observe(getViewLifecycleOwner(), productList -> {
            if (productList.size() > 0) {
                binding.noData.setVisibility(View.GONE);
                adapter.setData(productList);
            } else {
                binding.noData.setVisibility(View.VISIBLE);
            }
            binding.progressBar.setVisibility(View.GONE);
        });
    }

    private void checkLogin() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            initRecyclerView();
            initViewModel();

            binding.noLogin.setVisibility(View.GONE);
            binding.content.setVisibility(View.VISIBLE);

            /// get user data
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(uid)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @SuppressLint({"SetTextI18n", "ResourceType", "UseCompatLoadingForDrawables"})
                        @Override
                        public void onSuccess(DocumentSnapshot it) {

                            String image = "" + it.get("image");
                            String fullName = "" + it.get("fullName");
                            String username = "" + it.get("username");
                            String email = "" + it.get("email");
                            String skinType = "" + it.get("skinType");
                            String skinTone = "" + it.get("skinTone");
                            String skinUnderTone = "" + it.get("skinUnderTone");
                            String hairType = "" + it.get("hairType");
                            String coloredHair = "" + it.get("coloredHair");
                            String hijabers = "" + it.get("hijabers");

                            ArrayList<String> skinConcern = (ArrayList<String>) it.get("skinConcern");
                            ArrayList<String> bodyConcern = (ArrayList<String>) it.get("bodyConcern");
                            ArrayList<String> hairConcern = (ArrayList<String>) it.get("hairConcern");

                            if(!image.equals("")) {
                                Glide.with(requireContext())
                                        .load("" + it.get("image"))
                                        .into(binding.image);
                            }

                            binding.fullName.setText("" + fullName);
                            binding.username.setText("" + username);
                            binding.email.setText("" + email);
                            binding.skinType.setText("Skin type: " + skinType);
                            binding.skinTone.setText("Skin tone: " + skinTone);
                            binding.skinUnderTone.setText("Skin undertone:" + skinUnderTone);
                            binding.hairType.setText("Hair type: " + hairType);
                            binding.coloredHair.setText("Colored Hair: " + coloredHair);
                            binding.hijabers.setText("Hijaber: " + hijabers);

                           if(skinConcern != null) {
                               String words = "";
                               for(int i = 0; i<skinConcern.size(); i++) {
                                   words = skinConcern.get(i);

                                   TextView valueTV = new TextView(getActivity());
                                   valueTV.setText(words);
                                   valueTV.setId(i);
                                   valueTV.setTextColor(getResources().getColor(R.color.white));
                                   valueTV.setBackground(getResources().getDrawable(R.drawable.bg_rounded_btn));
                                   valueTV.setPadding(10, 5, 10, 5);

                                   LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                   params.setMarginStart(16);
                                   valueTV.setLayoutParams(params);
                                   ((LinearLayout) binding.ll1).addView(valueTV);

                               }
                           }


                            if(bodyConcern != null) {
                                String words = "";
                                for(int i = 0; i<bodyConcern.size(); i++) {
                                    words = bodyConcern.get(i);

                                    TextView valueTV = new TextView(getActivity());
                                    valueTV.setText(words);
                                    valueTV.setId(i);
                                    valueTV.setTextColor(getResources().getColor(R.color.white));
                                    valueTV.setBackground(getResources().getDrawable(R.drawable.bg_rounded_btn));
                                    valueTV.setPadding(10, 5, 10, 5);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    params.setMarginStart(16);
                                    valueTV.setLayoutParams(params);
                                    ((LinearLayout) binding.ll2).addView(valueTV);

                                }
                            }

                            if(hairConcern != null) {
                                String words = "";
                                for(int i = 0; i<hairConcern.size(); i++) {
                                    words = hairConcern.get(i);

                                    TextView valueTV = new TextView(getActivity());
                                    valueTV.setText(words);
                                    valueTV.setId(i);
                                    valueTV.setTextColor(getResources().getColor(R.color.white));
                                    valueTV.setBackground(getResources().getDrawable(R.drawable.bg_rounded_btn));
                                    valueTV.setPadding(10, 5, 10, 5);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    params.setMarginStart(16);
                                    valueTV.setLayoutParams(params);
                                    ((LinearLayout) binding.ll3).addView(valueTV);

                                }
                            }

                        }
                    });
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

         binding.loginBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(getActivity(), MainActivity.class));
             }
         });
    }

    private void signOut() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirm Sign-Out")
                .setMessage("Are you sure want to sign-out from application ?")
                .setIcon(R.drawable.ic_baseline_exit_to_app_24)
                .setPositiveButton("YES", (dialogInterface, i) -> {
                    // sign out dari firebase autentikasi
                    FirebaseAuth.getInstance().signOut();

                    // go to login activity
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    dialogInterface.dismiss();
                    startActivity(intent);
                    getActivity().finish();
                })
                .setNegativeButton("NO", (dialog, i) -> {
                    dialog.dismiss();
                })
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}