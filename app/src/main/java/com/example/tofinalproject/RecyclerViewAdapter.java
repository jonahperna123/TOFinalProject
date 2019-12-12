package com.example.tofinalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder> {

    private ArrayList<Movie> movies;
    private Context nContext;

    RecyclerViewAdapter(ArrayList<Movie> movies, Context nContext) {
        this.movies = movies;
        this.nContext = nContext;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem, viewGroup, false);

        viewHolder view_holder = new viewHolder(view);
        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {

        holder.textViewTitle.setText(movies.get(position).titleMovie);
        holder.textViewId.setText(movies.get(position).idMovie);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(nContext, movies.get(position).idMovie, Toast.LENGTH_SHORT).show();

                final Intent intent;

                intent =  new Intent(nContext, MovieActivity.class);
                intent.putExtra("movie_id", movies.get(position).idMovie);

                nContext.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewId;
        LinearLayout parentLayout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewMovieLayoutItemTitle);
            textViewId = itemView.findViewById(R.id.textViewMovieLayoutItemId);
            parentLayout = itemView.findViewById(R.id.parent_layout);


        }
    }

}
