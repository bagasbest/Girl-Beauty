package com.project.girlbeauty.ui.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.project.girlbeauty.MainActivity;
import com.project.girlbeauty.R;
import com.project.girlbeauty.auth.LoginActivity;
import com.project.girlbeauty.databinding.FragmentProfileBinding;
import com.project.girlbeauty.ui.ui.home.ProductAdapter;
import com.project.girlbeauty.ui.ui.home.ProductVIewModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProductAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
        initViewModel();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        checkLogin();

        return root;
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
            binding.noLogin.setVisibility(View.GONE);
            binding.content.setVisibility(View.VISIBLE);
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