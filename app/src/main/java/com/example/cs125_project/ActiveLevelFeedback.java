package com.example.cs125_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class ActiveLevelFeedback extends AppCompatActivity {
    RatingBar ratingBar;
    Button activefb_back;
    Button activefb_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_level_feedback);
        ratingBar = (RatingBar)findViewById(R.id.active_rating);
        activefb_back = (Button)findViewById(R.id.activefb_backbtn);
        activefb_next = (Button)findViewById(R.id.activefb_nextbtn);

        activefb_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backto_SleepFeedback();
            }
        });

        activefb_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   String rating_num = String.valueOf(ratingBar.getRating());
             //   Toast.makeText(ActiveLevelFeedback.this, rating_num+"", Toast.LENGTH_SHORT).show();
                nextto_SleepStorage();
            }
        });
    }

    public void backto_SleepFeedback(){
        Intent i = new Intent(this, SleepFeedback.class);
        startActivity(i);
    }

    //Redirect to DashBoard For Now
    //Goal: Direct To Sleep Storage Page Contain Previously Set Schedules
    public void nextto_SleepStorage() {
        Intent i = new Intent(this, SleepRecommendation.class);
        startActivity(i);
    }
}