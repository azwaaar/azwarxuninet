package id.net.uninet.azwar_uninet.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.net.uninet.azwar_uninet.R;
import id.net.uninet.azwar_uninet.models.Credits;
import id.net.uninet.azwar_uninet.utils.MainConfig;

public class CastMovieAdapter extends RecyclerView.Adapter<CastMovieAdapter.myCast> {

    private static Context context;
    private static ArrayList<Credits> creditsArrayList;

    public CastMovieAdapter(Context context) {
        CastMovieAdapter.context = context;
    }

    public void setUpComingArrayList(ArrayList<Credits> c) {
        CastMovieAdapter.creditsArrayList = c;
    }

    @NonNull
    @Override
    public CastMovieAdapter.myCast onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item, parent, false);

        return new CastMovieAdapter.myCast(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastMovieAdapter.myCast holder, int position) {
        final Credits credits = creditsArrayList.get(position);

        String posterImg = MainConfig.imageW500 + credits.getProfile_path();
        Glide.with(context)
                .load(posterImg)
                .placeholder(R.drawable.baseline_access_time_24)
                .into(holder.castPoster);

        holder.popularityTV.setText(credits.getPopularity());
        holder.nameTV.setText(credits.getOriginal_name());
        holder.charactersTV.setText(credits.getCharacter());
    }

    @Override
    public int getItemCount() {
        return creditsArrayList.size();
    }

    public static class myCast extends RecyclerView.ViewHolder {
        ImageView castPoster;
        TextView popularityTV, nameTV, charactersTV;
        public myCast(@NonNull View itemView) {
            super(itemView);
            castPoster = itemView.findViewById(R.id.castPoster);
            popularityTV = itemView.findViewById(R.id.popularityTV);
            nameTV = itemView.findViewById(R.id.nameTV);
            charactersTV = itemView.findViewById(R.id.charactersTV);
        }
    }
}
