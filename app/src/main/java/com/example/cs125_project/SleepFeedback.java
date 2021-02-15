package com.example.cs125_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class SleepFeedback extends AppCompatActivity {
    RatingBar ratingBar;
    Button sleepfb_back;
    Button sleepfb_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_feedback);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        sleepfb_back = (Button)findViewById(R.id.sleepfb_backbtn);
        sleepfb_next = (Button)findViewById(R.id.sleepfb_nextbtn);
        sleepfb_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backto_SleepActivity2();
            }
        });
        sleepfb_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating_num = String.valueOf(ratingBar.getRating());
                Toast.makeText(SleepFeedback.this, rating_num+"", Toast.LENGTH_SHORT).show();
                nextto_ExeciseFeedBack();
            }
        });
    }

    public void backto_SleepActivity2() {
        Intent i = new Intent(this, sleep_activity2.class);
        startActivity(i);
    }

    public void nextto_ExeciseFeedBack(){
        Intent i = new Intent(this, ActiveLevelFeedback.class);
        startActivity(i);
    }
}