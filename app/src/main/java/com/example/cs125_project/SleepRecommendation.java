package com.example.cs125_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class SleepRecommendation extends AppCompatActivity {
    private Button rec_back_button;

    private Button returnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_recommendation);

        rec_back_button = (Button)findViewById(R.id.rec_back_button);
        rec_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backto_ActiveLevel();
            }
        });

        returnBtn = (Button) findViewById(R.id.returnDashboardBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { returnToDashboard(); }
        });

    }

    public void backto_ActiveLevel() {
        Intent i = new Intent(this, ActiveLevelFeedback.class);
        startActivity(i);
    }

    public void returnToDashboard() {
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }

}