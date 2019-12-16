package com.example.tofinalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProfileRecyclerViewAdapter extends RecyclerView.Adapter<ProfileRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Movie> movieArrayList;
    private ArrayList<Episode> tvArrayList;
    private Context nContext;
    private boolean showMovie;

    ProfileRecyclerViewAdapter(ArrayList<Movie> movieList, ArrayList<Episode> tvArrayList, Context nContext, boolean showMovie) {
        this.movieArrayList = movieList;
        this.nContext = nContext;
        this.tvArrayList = tvArrayList;
        this.showMovie = showMovie;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_list_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (showMovie) {
            Movie movie = movieArrayList.get(position);
            holder.contentName.setText(String.valueOf(position + 1) + ". " + movie.titleMovie);
            holder.textViewYearReleased.setText(movie.yearReleased);
        } else {
            Episode episode = tvArrayList.get(position);
            holder.contentName.setText(String.valueOf(position + 1) + ". " + episode.seriesTitle + ": " + episode.episodeTitle);
            holder.textViewYearReleased.setText(episode.yearReleased);
        }

    }

    @Override
    public int getItemCount() {
        if (showMovie) {
            return movieArrayList.size();
        }

        return tvArrayList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView contentName, textViewYearReleased;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contentName = itemView.findViewById(R.id.contentRecyclerView);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            textViewYearReleased = itemView.findViewById(R.id.textViewYearReleased);
        }
    }
}
