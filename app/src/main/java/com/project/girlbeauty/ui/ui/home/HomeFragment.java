package com.project.girlbeauty.ui.ui.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.girlbeauty.R;
import com.project.girlbeauty.databinding.FragmentHomeBinding;
import com.project.girlbeauty.ui.BeautyConcernActivity;
import com.project.girlbeauty.ui.BeautyProfileActivity;
import com.project.girlbeauty.ui.PersonalInfoActivity;
import com.project.girlbeauty.ui.ui.SearchActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ProductAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
        initViewModel();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        showOnBoardingImage();
        checkIsLoginOrNot();
        return root;
    }

    @SuppressLint("SetTextI18n")
    private void checkIsLoginOrNot() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(uid)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            binding.textView18.setText("Hello, " + documentSnapshot.get("username"));

                            String fullName = "" + documentSnapshot.get("fullName");
                            String skinType = "" + documentSnapshot.get("skinType");
                            ArrayList<String> skinConcern = (ArrayList<String>) documentSnapshot.get("skinConcern");

                            if(fullName.equals("")) {
                                showPopupAccountStatus("personalInfo");
                            } else if (skinType.equals("")) {
                                showPopupAccountStatus("beautyProfile");
                            } else if (skinConcern == null) {
                                showPopupAccountStatus("beautyConcern");
                            }

                        }
                    });
        }
    }

    private void showPopupAccountStatus(String status) {
        Dialog dialog;
        Button completeProfileBtn;
        ImageView cancel, pic, piw, bpc, bpw;

        dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.popup_account_status);
        dialog.setCanceledOnTouchOutside(false);

        completeProfileBtn = dialog.findViewById(R.id.completeProfileBtn);
        cancel = dialog.findViewById(R.id.cancel);
        pic = dialog.findViewById(R.id.personalInfoCheck);
        piw = dialog.findViewById(R.id.personalInfoWarning);
        bpc = dialog.findViewById(R.id.beautyProfileCheck);
        bpw = dialog.findViewById(R.id.beautyProfileWarning);

        if (status.equals("beautyProfile")) {
            piw.setVisibility(View.GONE);
            pic.setVisibility(View.VISIBLE);
        } else if (status.equals("beautyConcern")){
            piw.setVisibility(View.GONE);
            pic.setVisibility(View.VISIBLE);
            bpw.setVisibility(View.GONE);
            bpc.setVisibility(View.VISIBLE);
        }

        completeProfileBtn.setOnClickListener(view -> {
           if(status.equals("personalInfo")) {
               dialog.dismiss();
               startActivity(new Intent(getActivity(), PersonalInfoActivity.class));
           } else if (status.equals("beautyProfile")) {
               dialog.dismiss();
               startActivity(new Intent(getActivity(), BeautyProfileActivity.class));
           } else {
               dialog.dismiss();
               startActivity(new Intent(getActivity(), BeautyConcernActivity.class));
           }
        });

        cancel.setOnClickListener(view -> dialog.dismiss());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void showOnBoardingImage() {
        final ArrayList<SlideModel> imageList = new ArrayList<>();// Create image list


        imageList.add(new SlideModel("https://images.unsplash.com/photo-1612817288484-6f916006741a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://images.unsplash.com/photo-1596462502278-27bfdc403348?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://images.unsplash.com/photo-1570172619644-dfd03ed5d881?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://images.unsplash.com/photo-1599847987657-881f11b92a75?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=435&q=80", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://images.unsplash.com/photo-1590393802710-dbf451560939?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=392&q=80", ScaleTypes.CENTER_CROP));

        binding.imageSlider.setImageList(imageList);
    }

    private void initRecyclerView() {
        binding.rvProduct.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new ProductAdapter();
        binding.rvProduct.setAdapter(adapter);
    }

    private void initViewModel() {
        ProductVIewModel viewModel = new ViewModelProvider(this).get(ProductVIewModel.class);

        binding.progressBar.setVisibility(View.VISIBLE);
        viewModel.setProductList();
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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.search.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), SearchActivity.class)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}