package id.net.uninet.azwar_uninet.activity.details;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.net.uninet.azwar_uninet.MainApplication;
import id.net.uninet.azwar_uninet.R;
import id.net.uninet.azwar_uninet.activity.YoutubeActivity;
import id.net.uninet.azwar_uninet.activity.YoutubeTVActivity;
import id.net.uninet.azwar_uninet.adapter.CastMovieAdapter;
import id.net.uninet.azwar_uninet.models.Credits;
import id.net.uninet.azwar_uninet.models.Movie;
import id.net.uninet.azwar_uninet.models.Review;
import id.net.uninet.azwar_uninet.models.TVShow;
import id.net.uninet.azwar_uninet.utils.Constants;
import id.net.uninet.azwar_uninet.utils.MainConfig;
import id.net.uninet.azwar_uninet.utils.Preferences;

public class TVDetailActivity extends AppCompatActivity {

    TVShow tvShow;

    @BindView(R.id.titleTV)
    TextView titleTV;

    @BindView(R.id.tvRelease)
    TextView tvRelease;

    @BindView(R.id.tvRate)
    TextView tvRate;

    @BindView(R.id.overviewTV)
    TextView overviewTV;

    @BindView(R.id.backgroundImage)
    ImageView backgroundImage;

    @BindView(R.id.posterTVShow)
    ImageView posterTVShow;

    @BindView(R.id.playFab)
    FloatingActionButton playFab;

    ActionBar actionBar;

    @BindView(R.id.rv_cast)
    RecyclerView rv_cast;

    @BindView(R.id.authorImage)
    ImageView authorImage;

    @BindView(R.id.authorName)
    TextView authorName;

    @BindView(R.id.authorRate)
    TextView authorRate;

    @BindView(R.id.authorReview)
    TextView authorReview;

    private CastMovieAdapter castMovieAdapter;
    private ArrayList<Credits> creditsArrayList = new ArrayList<>();
    private ArrayList<Review> reviewArrayList = new ArrayList<>();

    Gson gson;
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvdetail);
        ButterKnife.bind(this);

        tvShow = (TVShow) getIntent().getSerializableExtra("serial_tv");

        actionBar = getSupportActionBar();
        actionBar.setTitle(tvShow.getName());
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        playFab.setOnClickListener(view -> {
            Intent send = new Intent(TVDetailActivity.this, YoutubeTVActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("serial_yt_tv", tvShow);
            send.putExtras(b);
            startActivity(send);
        });

        String backgroundPoster = MainConfig.imageOriginal + tvShow.getBackdrop_path();
        Glide.with(this)
                .load(backgroundPoster)
                .placeholder(R.drawable.baseline_access_time_24)
                .into(backgroundImage);

        String poster = MainConfig.imageW500 + tvShow.getPoster_path();
        Glide.with(this)
                .load(poster)
                .placeholder(R.drawable.baseline_access_time_24)
                .into(posterTVShow);

        titleTV.setText(tvShow.getName() != null ? tvShow.getName() : "N/A");
        tvRelease.setText(tvShow.getFirst_air_date() != null ? tvShow.getFirst_air_date() : "N/A");
        tvRate.setText(tvShow.getVote_average() != null ? tvShow.getVote_average() : "N/A");
        overviewTV.setText(tvShow.getOverview() != null ? tvShow.getOverview() : "N/A");

        preferences = new Preferences(this);
        gson = new GsonBuilder().create();

        castMovieAdapter = new CastMovieAdapter(this);

        getCasting();
        getReviews();
    }

    private void getReviews() {
        StringRequest request = new StringRequest(Request.Method.GET, MainConfig.REVIEW_TV + tvShow.getId() + "/reviews",
                response -> {
                    try {
                        final JSONObject jsonObject = new JSONObject(response);
                        Type type = new TypeToken<List<Review>>() {
                        }.getType();

                        reviewArrayList = gson.fromJson(jsonObject.getString("results"), type);

                        if (reviewArrayList.isEmpty()) {
                            FancyToast.makeText(TVDetailActivity.this, "Maaf, konten review tidak tersedia pada API themoviedb", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                            String reviewPoster = "N/A";
                            Glide.with(this)
                                    .load(reviewPoster)
                                    .placeholder(R.drawable.baseline_access_time_24)
                                    .into(authorImage);

                            authorName.setText("N/A");
                            authorRate.setText("N/A");
                            authorReview.setText("N/A");
                        } else {
                            String reviewPoster = MainConfig.imageW500 + reviewArrayList.get(0).getAuthor_details().getAvatar_path();
                            Glide.with(this)
                                    .load(reviewPoster)
                                    .placeholder(R.drawable.baseline_access_time_24)
                                    .into(authorImage);

                            authorName.setText(reviewArrayList.get(0).getAuthor());
                            authorRate.setText(reviewArrayList.get(0).getAuthor_details().getRating());
                            authorReview.setText(reviewArrayList.get(0).getContent());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {


                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("Content-ComplainNature", "application/x-www-form-urlencoded");
                params.put("Authorization", "Bearer " + Constants.API_ACCESS_TOKEN);

                return params;
            }
        };
        MainApplication.getInstance().addToRequestQueue(request);
    }

    private void getCasting() {
        StringRequest request = new StringRequest(Request.Method.GET, MainConfig.CREDITS_TV + tvShow.getId() + "/credits",
                response -> {
                    try {
                        final JSONObject jsonObject = new JSONObject(response);
                        Type type = new TypeToken<List<Credits>>() {
                        }.getType();

                        creditsArrayList = gson.fromJson(jsonObject.getString("cast"), type);

                        rv_cast.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                        castMovieAdapter = new CastMovieAdapter(this);

                        castMovieAdapter.setUpComingArrayList(creditsArrayList);
                        rv_cast.setAdapter(castMovieAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("Content-ComplainNature", "application/x-www-form-urlencoded");
                params.put("Authorization", "Bearer " + Constants.API_ACCESS_TOKEN);

                return params;
            }
        };
        MainApplication.getInstance().addToRequestQueue(request);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}