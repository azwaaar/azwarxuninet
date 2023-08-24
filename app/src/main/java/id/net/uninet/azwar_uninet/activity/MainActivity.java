package id.net.uninet.azwar_uninet.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.net.uninet.azwar_uninet.R;
import id.net.uninet.azwar_uninet.adapter.ViewPagerAdapter;
import id.net.uninet.azwar_uninet.fragment.MovieFragment;
import id.net.uninet.azwar_uninet.fragment.ProfileFragment;
import id.net.uninet.azwar_uninet.fragment.TVFragment;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    public static MainActivity mainActivity;

    boolean doubleBackToExitPressedOnce = false;

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            ActivityCompat.finishAffinity(this);
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan kembali untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    ViewPagerAdapter viewPagerAdapter;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.navigation)
    AnimatedBottomBar navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainActivity = this;

        setupViewPager(viewPager);

        viewPager.setAdapter(viewPagerAdapter);
        navigation.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new MovieFragment(), this.getString(R.string.movie));
        viewPagerAdapter.addFragment(new TVFragment(), this.getString(R.string.tv));
        viewPagerAdapter.addFragment(new ProfileFragment(), this.getString(R.string.profile));
        viewPager.setAdapter(viewPagerAdapter);
    }
}