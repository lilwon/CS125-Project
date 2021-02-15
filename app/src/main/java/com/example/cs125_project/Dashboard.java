package com.example.cs125_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    DrawerLayout dl;
    // Moved to Main Fragment
    // private Button enterSleepActivity;

    // Used for sliding tabs
    private PagerAdapter pagerAdapter;
    private TabLayout tab;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dl = findViewById(R.id.drawer_layout);

        // For Tab Layout Fragments
        tab = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // Setting up Adapter to link
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new MainFragment(), "Main");
        pagerAdapter.addFragment(new GraphFragment(), "Activity");

        viewPager.setAdapter(pagerAdapter);
        tab.setupWithViewPager(viewPager);
    }


    // Below is for Navigation Drawer
    public void ClickMenu(View view) {
        MainActivity.openDrawer(dl);
    }

    public void ClickLogo(View view) {
        MainActivity.closeDrawer(dl);
    }

    public void ClickHome(View view) {
        MainActivity.redirectActivity(this, ProfileActivity.class);
    }

    public void ClickDashboard(View view) {
        MainActivity.closeDrawer(dl);
    }


    @Override
    protected void onPause() {
        super.onPause();

        MainActivity.closeDrawer(dl);
    }
}