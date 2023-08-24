package id.net.uninet.azwar_uninet.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.net.uninet.azwar_uninet.activity.details.MovieDetailActivity;
import id.net.uninet.azwar_uninet.utils.MainConfig;
import id.net.uninet.azwar_uninet.R;
import id.net.uninet.azwar_uninet.models.Movie;

public class UpComingMovieAdapter extends RecyclerView.Adapter<UpComingMovieAdapter.myUMA> {

    static Context context;
    private static ArrayList<Movie> movieArrayList;

    public UpComingMovieAdapter(Context context) {
        UpComingMovieAdapter.context = context;
    }

    public void setUpComingArrayList(ArrayList<Movie> c) {
        UpComingMovieAdapter.movieArrayList = c;
    }

    @NonNull
    @Override
    public myUMA onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming_movie, parent, false);

        return new myUMA(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingMovieAdapter.myUMA holder, int position) {
        final Movie movie = movieArrayList.get(position);

        String imgProfile = MainConfig.imageW500 + movie.getPoster_path();
        Glide.with(context)
                .load(imgProfile)
                .placeholder(R.drawable.baseline_access_time_24)
                .into(holder.imgPoster);

        holder.titleMovie.setText(movie.getTitle() != null ? movie.getTitle() : "N/A");
        holder.rateMovie.setText(movie.getVote_average() != null ? movie.getVote_average() : "N/A");
        holder.movieRelease.setText(movie.getRelease_date() != null ? movie.getRelease_date() : "N/A");

        holder.cardMovieUpcoming.setOnClickListener(view -> {
            Intent send = new Intent(context, MovieDetailActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("serial_global", movie);
            send.putExtras(b);
            context.startActivity(send);
        });
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public static class myUMA extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        CardView cardMovieUpcoming;
        TextView titleMovie, rateMovie, movieRelease;
        public myUMA(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.posterMovie);
            cardMovieUpcoming = itemView.findViewById(R.id.cardMovieUpcoming);
            titleMovie = itemView.findViewById(R.id.movieTitle);
            rateMovie = itemView.findViewById(R.id.movieRate);
            movieRelease = itemView.findViewById(R.id.movieRelease);
        }
    }
}
