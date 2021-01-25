package com.example.cs125_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dl = findViewById(R.id.drawer_layout);

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
        recreate();
    }

    @Override
    protected void onPause() {
        super.onPause();

        MainActivity.closeDrawer(dl);
    }
}