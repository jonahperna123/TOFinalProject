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
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviews.get(position);

        holder.rating.setRating(review.starRating);
        holder.movieTitle.setText(review.contentTitle);
        holder.review.setText(review.review);
        holder.username.setText(review.userEmail);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView username, movieTitle, review;
        RatingBar rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.imageViewAvatar_HomePageActivity);
            username = itemView.findViewById(R.id.textViewUsername_HomePageActivity);
            movieTitle = itemView.findViewById(R.id.textViewMovieTitle_HomePageActivity);
            review = itemView.findViewById(R.id.textViewReview_HomePageActivity);
            rating = itemView.findViewById(R.id.ratingBar_HomePageActivity);
        }
    }
}
