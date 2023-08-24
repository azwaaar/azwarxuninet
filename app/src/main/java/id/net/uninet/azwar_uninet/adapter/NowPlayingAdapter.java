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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.net.uninet.azwar_uninet.R;
import id.net.uninet.azwar_uninet.activity.details.MovieDetailActivity;
import id.net.uninet.azwar_uninet.models.Movie;
import id.net.uninet.azwar_uninet.utils.MainConfig;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.myNp> {

    static Context context;
    private static ArrayList<Movie> nowPlayingArrayList;

    public NowPlayingAdapter(Context context) {
        NowPlayingAdapter.context = context;
    }

    public void setNowPlaying(ArrayList<Movie> c) {
        NowPlayingAdapter.nowPlayingArrayList = c;
    }

    @NonNull
    @Override
    public myNp onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_now_playing, parent, false);

        return new myNp(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingAdapter.myNp holder, int position) {
        final Movie movie = nowPlayingArrayList.get(position);

        String imgProfile = MainConfig.imageW500 + movie.getPoster_path();
        Glide.with(context)
                .load(imgProfile)
                .placeholder(R.drawable.baseline_access_time_24)
                .into(holder.posterNowPlaying);

        holder.titleNowPlaying.setText(movie.getTitle() != null ? movie.getTitle() : "N/A");
        holder.overviewNowPlaying.setText(movie.getOverview() != null ? movie.getOverview() : "N/A");
        holder.nowPlayingRate.setText(movie.getVote_average() != null ? movie.getVote_average() : "N/A");

        holder.nowPlayingCard.setOnClickListener(view -> {
            Intent send = new Intent(context, MovieDetailActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("serial_global", movie);
            send.putExtras(b);
            context.startActivity(send);
        });
    }

    @Override
    public int getItemCount() {
        return nowPlayingArrayList.size();
    }

    public static class myNp extends RecyclerView.ViewHolder {
        ImageView posterNowPlaying;
        ConstraintLayout nowPlayingCard;
        TextView titleNowPlaying, overviewNowPlaying, nowPlayingRate;
        public myNp(@NonNull View itemView) {
            super(itemView);
            nowPlayingCard = itemView.findViewById(R.id.nowPlayingCard);
            posterNowPlaying = itemView.findViewById(R.id.posterNowPlaying);
            titleNowPlaying = itemView.findViewById(R.id.titleNowPlaying);
            overviewNowPlaying = itemView.findViewById(R.id.overviewNowPlaying);
            nowPlayingRate = itemView.findViewById(R.id.nowPlayingRate);
        }
    }
}
