package com.example.cs125_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button sleepBtn;

    DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = findViewById(R.id.drawer_layout);

        sleepBtn = (Button) findViewById(R.id.sleepBtn);
        sleepBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSleepActivity();
            }
        });
    }

    public void ClickMenu(View view) {
        openDrawer(dl);
    }

    public static void openDrawer(DrawerLayout dl) {
        dl.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        closeDrawer(dl);
    }

    public static void closeDrawer(DrawerLayout dl) {

        if ( dl.isDrawerOpen(GravityCompat.START )) {
            dl.closeDrawer(GravityCompat.START) ;
        }
    }

    public void ClickHome(View view) {
        recreate();
    }

    public void ClickDashboard(View view) {
        redirectActivity(this, Dashboard.class);
    }


    public static void redirectActivity(Activity activity, Class aClass) {
        Intent i = new Intent(activity, aClass);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(i);

    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(dl);
    }

    public void openSleepActivity() {
        Intent i = new Intent(this, sleep_activity.class);
        startActivity(i);
    }
}