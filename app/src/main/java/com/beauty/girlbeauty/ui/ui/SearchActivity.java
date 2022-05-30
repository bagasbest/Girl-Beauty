package com.beauty.girlbeauty.ui.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.beauty.girlbeauty.databinding.ActivitySearchBinding;
import com.beauty.girlbeauty.ui.ui.home.ProductAdapter;
import com.beauty.girlbeauty.ui.ui.home.ProductVIewModel;

public class SearchActivity extends AppCompatActivity {


    private ActivitySearchBinding binding;
    private SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initRecyclerView();
        initViewModel("");

        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()) {
                    String query = editable.toString();
                    initRecyclerView();
                    initViewModel(query);
                } else {
                    initRecyclerView();
                    initViewModel("");
                }
            }
        });

        binding.cancel.setOnClickListener(view -> onBackPressed());
    }

    private void initRecyclerView() {
        binding.rvProduct.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter();
        binding.rvProduct.setAdapter(adapter);
    }

    private void initViewModel(String query) {
        ProductVIewModel viewModel = new ViewModelProvider(this).get(ProductVIewModel.class);

        binding.progressBar.setVisibility(View.VISIBLE);
        if(query.equals("")) {
            viewModel.setProductList();
        } else {
            viewModel.setProductListByQuery(query);
        }
        viewModel.getProduct().observe((this), productList -> {
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
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}