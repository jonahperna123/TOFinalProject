package com.example.tofinalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Review> reviews;
    private Context mContext;

    public HomeRecyclerViewAdapter(ArrayList<Review> reviews, Context mContext) {
        this.reviews = reviews;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_item, parent, false);
        HomeRecyclerViewAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rating.setRating(reviews.get(position).starRating);
        holder.textViewReview.setText(reviews.get(position).review);
        holder.textViewUserEmail.setText(reviews.get(position).userEmail);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewAvatar, imageViewMoviePoster;
        TextView textViewUserEmail, textViewReview;
        RatingBar rating;
        RelativeLayout parentLayout, userLayout, reviewLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar_HomePageActivity);
            imageViewMoviePoster = itemView.findViewById(R.id.imageViewMoviePoster_HomePageActivity);
            textViewUserEmail = itemView.findViewById(R.id.textViewEmail_HomePageActivity);
            textViewReview = itemView.findViewById(R.id.textViewReview_HomePageActivity);
            rating = itemView.findViewById(R.id.ratingBar_HomePageActivity);
            parentLayout = itemView.findViewById(R.id.layout_HomePageActivity);
            userLayout = itemView.findViewById(R.id.userLayout_HomePageActivity);
            reviewLayout = itemView.findViewById(R.id.reviewLayout_HomePageActivity);

        }
    }
}
