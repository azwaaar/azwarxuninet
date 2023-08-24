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
import id.net.uninet.azwar_uninet.activity.details.TVDetailActivity;
import id.net.uninet.azwar_uninet.models.TVShow;
import id.net.uninet.azwar_uninet.utils.MainConfig;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TV> {

    static Context context;
    private static ArrayList<TVShow> tvShowArrayList;

    public TVShowAdapter(Context context) {
        TVShowAdapter.context = context;
    }

    public void setTVShowArrayList(ArrayList<TVShow> c) {
        TVShowAdapter.tvShowArrayList = c;
    }

    @NonNull
    @Override
    public TV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_now_playing, parent, false);

        return new TV(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowAdapter.TV holder, int position) {
        final TVShow tv = tvShowArrayList.get(position);

        String imgProfile = MainConfig.imageW500 + tv.getPoster_path();
        Glide.with(context)
                .load(imgProfile)
                .placeholder(R.drawable.baseline_access_time_24)
                .into(holder.posterNowPlaying);

        holder.titleNowPlaying.setText(tv.getName() != null ? tv.getName() : "N/A");
        holder.overviewNowPlaying.setText(tv.getOverview() != null ? tv.getOverview() : "N/A");
        holder.nowPlayingRate.setText(tv.getVote_average() != null ? tv.getVote_average() : "N/A");

        holder.nowPlayingCard.setOnClickListener(view -> {
            Intent send = new Intent(context, TVDetailActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("serial_tv", tv);
            send.putExtras(b);
            context.startActivity(send);
        });
    }

    @Override
    public int getItemCount() {
        return tvShowArrayList.size();
    }

    public static class TV extends RecyclerView.ViewHolder {
        ImageView posterNowPlaying;
        ConstraintLayout nowPlayingCard;
        TextView titleNowPlaying, overviewNowPlaying, nowPlayingRate;
        public TV(@NonNull View itemView) {
            super(itemView);
            nowPlayingCard = itemView.findViewById(R.id.nowPlayingCard);
            posterNowPlaying = itemView.findViewById(R.id.posterNowPlaying);
            titleNowPlaying = itemView.findViewById(R.id.titleNowPlaying);
            overviewNowPlaying = itemView.findViewById(R.id.overviewNowPlaying);
            nowPlayingRate = itemView.findViewById(R.id.nowPlayingRate);
        }
    }
}
