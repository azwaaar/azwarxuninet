package id.net.uninet.azwar_uninet.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragments;
    private final ArrayList<String> strings;

    public ViewPagerAdapter(FragmentManager context) {
        super(context, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fragments = new ArrayList<>();
        strings = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int azwar) {
        return strings.get(azwar);
    }

    @NonNull
    @Override
    public Fragment getItem(int azwar) {
        return fragments.get(azwar);
    }

    public void addFragment(Fragment fragment, String string) {
        strings.add(string);
        fragments.add(fragment);
    }
}
