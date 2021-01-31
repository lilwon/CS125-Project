package com.example.cs125_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {

    DrawerLayout dl;
    private Button enterSleepActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dl = findViewById(R.id.drawer_layout);

        enterSleepActivity = (Button) findViewById(R.id.enterSleepActivity);
        enterSleepActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSleepActivity();
            }
        });

    }

    public void openSleepActivity() {
        Intent i = new Intent(this, sleep_activity.class);
        startActivity(i);
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