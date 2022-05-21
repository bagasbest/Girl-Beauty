package com.project.girlbeauty.ui.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.girlbeauty.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    /// INISIASI ARRAY LIST SEBAGAI PENAMPUNG LIST DATA PRODUK
    private final ArrayList<ProductModel> listProduct = new ArrayList<>();
    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<ProductModel> items) {
        listProduct.clear();
        listProduct.addAll(items);
        notifyDataSetChanged();
    }

    /// CASTING LAYOUT KE item_booking SUPAYA LIST PRODUK DAPAT DI TAMPILKAN BERBENTUK URUTAN
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.bind(listProduct.get(position));
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }



    //// FUNGSI UNTUK MEMASUKKAN DATA DARI ARRAY LIST DIATAS KEDALAM ATRIBUT, SEHINGGA TERLIHAT NAMA, DESKRIPSI, RATING
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cv, owner;
        private final ImageView image, userDp;
        private final TextView name;
        private final TextView description;
        private final TextView rating;
        private final TextView userReview;
        private final TextView username;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            owner = itemView.findViewById(R.id.owner);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            rating = itemView.findViewById(R.id.rating);
            userReview = itemView.findViewById(R.id.userReview);
            username = itemView.findViewById(R.id.username);
            userDp = itemView.findViewById(R.id.userDp);
        }

        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void bind(ProductModel model) {

            Glide.with(itemView.getContext())
                    .load(model.getImage())
                    .into(image);

            if(model.getRole().equals("user")) {
                owner.setVisibility(View.VISIBLE);
                if(!model.getUserDp().equals("")) {
                    Glide.with(itemView.getContext())
                            .load(model.getUserDp())
                            .into(userDp);
                }
                username.setText(model.getUsername());
            } else {
                owner.setVisibility(View.GONE);
            }


            name.setText(model.getName());
            description.setText(model.getDescription());
            rating.setText(String.format("%.1f",model.getRating()));
            userReview.setText("(" + model.getUserReview() + ")");


            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                    intent.putExtra(ProductDetailActivity.EXTRA_DATA, model);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
