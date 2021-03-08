package com.example.cs125_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class RemindersActivity extends AppCompatActivity {
    // Need to set a default time to ~10pm or something.. if user doesn't
    // Set a time

    DrawerLayout dl;

    // User picks a Time to set
    private TimePicker picker;

    private Button returnBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);

        picker = (TimePicker) findViewById(R.id.notification_time);
        picker.setIs24HourView(true);

        returnBtn = (Button) findViewById(R.id.returnDashboardBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToDashBoard();
            }
        });
    }
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
        MainActivity.redirectActivity(this, Dashboard.class);
    }

    // Return to dashboard
    public void returnToDashBoard() {
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }
}