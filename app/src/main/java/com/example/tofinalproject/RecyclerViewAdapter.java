package com.example.tofinalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder> {

    private ArrayList<Movie> movies;
    private Context mContext;



    RecyclerViewAdapter(ArrayList<Movie> movies, Context mContext) {
        this.movies = movies;
        this.mContext = mContext;
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

        holder.textViewTitle.setText(movies.get(position).movie_title);
        holder.textViewId.setText(movies.get(position).movie_id);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, movies.get(position).movie_title, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewId;
        RelativeLayout parentLayout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textView);
            textViewId = itemView.findViewById(R.id.textView2);
            parentLayout = itemView.findViewById(R.id.parent_layout);


        }
    }
}
