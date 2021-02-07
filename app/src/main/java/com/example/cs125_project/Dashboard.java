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
    private Button enterSleepActivity;

    TabLayout tab;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dl = findViewById(R.id.drawer_layout);

        // For Tab Layout Fragments
        tab = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        ArrayList<String> tabArray = new ArrayList<>();
        tabArray.add("Main");
        tabArray.add("Graph");

        prepViewPager(viewPager, tabArray);

       tab.setupWithViewPager(viewPager);

    }

    private void prepViewPager(ViewPager viewPager, ArrayList<String> tabArray) {
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());

        MainFragment frag = new MainFragment();

        for (int i = 0; i < tabArray.size(); i++) {
            Bundle bundle= new Bundle();
            bundle.putString("title", tabArray.get(i) );

            frag.setArguments(bundle);

            adapter.addFragment(frag, tabArray.get(i));

            frag = new MainFragment();
        }

        // Set Page adapter
        viewPager.setAdapter(adapter);

    }

    private class PageAdapter extends FragmentPagerAdapter {
        ArrayList<String> tempList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        public void addFragment(Fragment frag, String fragName) {
            tempList.add(fragName);
            fragmentList.add(frag);
        }

        public PageAdapter(@NonNull FragmentManager fm) {
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
            return tempList.get(position);
        }
    }


    public void ClickMenu(View view) {
        MainActivity.openDrawer(dl);
    }

    public void ClickLogo(View view) {
        MainActivity.closeDrawer(dl);
    }

    public void ClickHome(View view) {
        MainActivity.redirectActivity(this, MainActivity.class);
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