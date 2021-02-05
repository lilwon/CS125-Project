package com.example.cs125_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {
    private Button sleepBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sleepBtn = (Button) findViewById(R.id.sleeptestBtn);
        sleepBtn.setOnClickListener(new View.OnClickListener(){
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
}