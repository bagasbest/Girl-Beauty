package com.project.girlbeauty.utils;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.girlbeauty.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    /// INISIASI ARRAY LIST SEBAGAI PENAMPUNG LIST DATA REVIEW
    private final ArrayList<ReviewModel> listReview = new ArrayList<>();
    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<ReviewModel> items) {
        listReview.clear();
        listReview.addAll(items);
        notifyDataSetChanged();
    }

    /// CASTING LAYOUT KE item_booking SUPAYA LIST REVIEW DAPAT DI TAMPILKAN BERBENTUK URUTAN
    @NonNull
    @NotNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReviewAdapter.ViewHolder holder, int position) {
        holder.bind(listReview.get(position));
    }

    @Override
    public int getItemCount() {
        return listReview.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView name, review, beautyProfile;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.title);
            beautyProfile = itemView.findViewById(R.id.beautyProfile);
            review = itemView.findViewById(R.id.review);
        }

        public void bind(ReviewModel model) {

            Glide.with(itemView.getContext())
                    .load(model.getImage())
                    .into(image);

            name.setText(model.getName());
            beautyProfile.setText(model.getBeautyProfile());
            review.setText(String.valueOf(model.getReview()));

        }
    }
}
