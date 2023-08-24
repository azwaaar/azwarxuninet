package id.net.uninet.azwar_uninet.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
import id.net.uninet.azwar_uninet.adapter.NowPlayingAdapter;
import id.net.uninet.azwar_uninet.adapter.UpComingMovieAdapter;
import id.net.uninet.azwar_uninet.models.Movie;
import id.net.uninet.azwar_uninet.utils.Constants;
import id.net.uninet.azwar_uninet.utils.MainConfig;
import id.net.uninet.azwar_uninet.utils.Preferences;

/**
 * A simple {@link Fragment} subclass.
 * Achmad Azwar Misbah - PT. Uninet - 2023/08/24 01:06
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {

    @BindView(R.id.rvUpcomingMovie)
    RecyclerView rvUpComing;
    private UpComingMovieAdapter upComingMovieAdapter;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();

    @BindView(R.id.rvNowPlaying)
    RecyclerView rvNowPlaying;
    private NowPlayingAdapter nowPlayingAdapter;
    private ArrayList<Movie> nowPlayingArrayList = new ArrayList<>();

    Gson gson;
    Preferences preferences;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);

        preferences = new Preferences(getContext());
        gson = new GsonBuilder().create();

        upComingMovieAdapter = new UpComingMovieAdapter(getContext());
        nowPlayingAdapter = new NowPlayingAdapter(getContext());

        getUpComingMovies();
        getNowPlaying();

        return view;
    }

    private void getNowPlaying() {
        StringRequest request = new StringRequest(Request.Method.GET, MainConfig.NOW_PLAYING_MOVIE,
                response -> {
                    try {
                        final JSONObject jsonObject = new JSONObject(response);
                        Log.d("TAG", "getNowPlaying: " + response);
                        Type type = new TypeToken<List<Movie>>() {
                        }.getType();

                        nowPlayingArrayList = gson.fromJson(jsonObject.getString("results"), type);

                        rvNowPlaying.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        nowPlayingAdapter = new NowPlayingAdapter(getContext());

                        nowPlayingAdapter.setNowPlaying(nowPlayingArrayList);
                        rvNowPlaying.setAdapter(nowPlayingAdapter);

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

    private void getUpComingMovies() {
        StringRequest request = new StringRequest(Request.Method.GET, MainConfig.UPCOMING_MOVIE,
                response -> {
                    try {
                        final JSONObject jsonObject = new JSONObject(response);
                        Type type = new TypeToken<List<Movie>>() {
                        }.getType();

                        movieArrayList = gson.fromJson(jsonObject.getString("results"), type);
                        Log.d("up coming ", "getUpComingMovies: " + movieArrayList);

                        rvUpComing.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                        upComingMovieAdapter = new UpComingMovieAdapter(getContext());

                        upComingMovieAdapter.setUpComingArrayList(movieArrayList);
                        rvUpComing.setAdapter(upComingMovieAdapter);

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
}