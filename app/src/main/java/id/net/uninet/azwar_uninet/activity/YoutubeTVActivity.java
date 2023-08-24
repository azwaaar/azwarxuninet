package id.net.uninet.azwar_uninet.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
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
import id.net.uninet.azwar_uninet.models.Movie;
import id.net.uninet.azwar_uninet.models.TVShow;
import id.net.uninet.azwar_uninet.models.Video;
import id.net.uninet.azwar_uninet.utils.Constants;
import id.net.uninet.azwar_uninet.utils.MainConfig;
import id.net.uninet.azwar_uninet.utils.Preferences;

public class YoutubeTVActivity extends AppCompatActivity {

    @BindView(R.id.youtubeTV)
    YouTubePlayerView youtubeTV;
    TVShow tvShow;

    Preferences preferences;
    Gson gson;
    private ArrayList<Video> videoArrayList = new ArrayList<>();

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_tv);
        ButterKnife.bind(this);

        preferences = new Preferences(this);
        gson = new GsonBuilder().create();

        actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvShow = (TVShow) getIntent().getSerializableExtra("serial_yt_tv");

        getTVVideo();
    }

    private void getTVVideo() {
        StringRequest request = new StringRequest(Request.Method.GET, MainConfig.VIDEOS_TV + tvShow.getId() + "/videos",
                response -> {
                    try {
                        final JSONObject jsonObject = new JSONObject(response);
                        Type type = new TypeToken<List<Video>>() {
                        }.getType();

                        videoArrayList = gson.fromJson(jsonObject.getString("results"), type);

                        if (videoArrayList.isEmpty()) {
                            FancyToast.makeText(YoutubeTVActivity.this, "Maaf, Konten Tidak Tersedia Pada API themoviedb", FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
                        } else {
                            actionBar.setTitle(videoArrayList.get(0).getSite());

                            getLifecycle().addObserver(youtubeTV);
                            youtubeTV.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                    String videoId = videoArrayList.get(videoArrayList.size() -1).getKey();
                                    youTubePlayer.loadVideo(videoId, 0);
                                }
                            });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        youtubeTV.release();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}