package com.project.girlbeauty.ui.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.project.girlbeauty.databinding.FragmentHomeBinding;
import com.project.girlbeauty.ui.ui.SearchActivity;

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

        return root;
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