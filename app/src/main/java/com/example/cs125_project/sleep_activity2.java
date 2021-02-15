package com.example.cs125_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class sleep_activity2 extends AppCompatActivity {

    private Button next_button2;
    private Button back_button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_activity2);

        TimePicker simpleTimePicker=(TimePicker)findViewById(R.id.simpleTimePicker2); //initiate a time picker

        next_button2 = (Button) findViewById(R.id.next_button2);
        next_button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSleepActivity2();
            }
        });

        back_button2 = (Button) findViewById(R.id.back_button2);
        back_button2.setOnClickListener(new View.OnClickListener(){
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

    public void openSleepActivity2() {
        Intent i = new Intent(this, SleepFeedback.class);
        startActivity(i);
    }

}
