package id.net.uninet.azwar_uninet.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import id.net.uninet.azwar_uninet.adapter.TVShowAdapter;
import id.net.uninet.azwar_uninet.adapter.UpComingMovieAdapter;
import id.net.uninet.azwar_uninet.models.Movie;
import id.net.uninet.azwar_uninet.models.TVShow;
import id.net.uninet.azwar_uninet.utils.Constants;
import id.net.uninet.azwar_uninet.utils.MainConfig;
import id.net.uninet.azwar_uninet.utils.Preferences;

/**
 * A simple {@link Fragment} subclass.
 * Achmad Azwar Misbah - PT. Uninet - 2023/08/24 01:06
 * create an instance of this fragment.
 */
public class TVFragment extends Fragment {

    @BindView(R.id.rvTVShow)
    RecyclerView rvTVShow;

    private TVShowAdapter tvShowAdapter;
    private ArrayList<TVShow> tvShowArrayList = new ArrayList<>();

    Gson gson;
    Preferences preferences;

    public TVFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_t_v, container, false);
        ButterKnife.bind(this, view);

        preferences = new Preferences(getContext());
        gson = new GsonBuilder().create();

        tvShowAdapter = new TVShowAdapter(getContext());

        getPopularTV();

        return view;
    }

    private void getPopularTV() {
        StringRequest request = new StringRequest(Request.Method.GET, MainConfig.POPULAR_TV,
                response -> {
                    try {
                        final JSONObject jsonObject = new JSONObject(response);
                        Type type = new TypeToken<List<TVShow>>() {
                        }.getType();

                        tvShowArrayList = gson.fromJson(jsonObject.getString("results"), type);

                        rvTVShow.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                        tvShowAdapter = new TVShowAdapter(getContext());

                        tvShowAdapter.setTVShowArrayList(tvShowArrayList);
                        rvTVShow.setAdapter(tvShowAdapter);

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