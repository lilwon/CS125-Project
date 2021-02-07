package com.example.cs125_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

// Linking pages with the page adapter for TabLayout
public class PagerAdapter extends FragmentPagerAdapter {
    // Name of the fragment titles to be added
    private final ArrayList<String> fragTitle = new ArrayList<>();
    // Name of the fragments to be added
    private final List<Fragment> fragmentList = new ArrayList<>();

    public void addFragment(Fragment frag, String fragName) {
        fragTitle.add(fragName);
        fragmentList.add(frag);
    }

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragTitle.get(position);
    }
}