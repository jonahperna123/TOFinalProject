package com.example.tofinalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder> {

    public ArrayList<Review> reviewList;
    public Context nContext;

    ReviewRecyclerViewAdapter(ArrayList<Review> reviewList, Context nContext) {
        this.reviewList = reviewList;
        this.nContext = nContext;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_review_content, parent, false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, final int position) {
        Review review = reviewList.get(position);
        holder.contentTitle.setText(review.contentTitle);
        holder.descriptionView.setText(review.review);
        holder.contentRating.setText(String.valueOf(review.starRating) + " / 5");
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView contentTitle, descriptionView, contentRating;
        ConstraintLayout parentLayout;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTitle = itemView.findViewById(R.id.contentTitle);
            descriptionView = itemView.findViewById(R.id.descriptionView);
            parentLayout = itemView.findViewById(R.id.reviewParentLayout);
            contentRating = itemView.findViewById(R.id.textViewRating);

        }
    }
}
