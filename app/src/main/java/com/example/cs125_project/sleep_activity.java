package com.example.cs125_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class sleep_activity extends AppCompatActivity {

    private Button next_button;
    private Button back_button;
    public int hour1;
    public int minute1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_activity);

        TimePicker simpleTimePicker=(TimePicker)findViewById(R.id.simpleTimePicker); //initiate a time picker

        // Time is stored as 24H Military time
        hour1 = simpleTimePicker.getCurrentHour();
        minute1 = simpleTimePicker.getCurrentMinute();

        back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backto_home();
            }
        });


        Log.v("currentHour", String.valueOf(hour1));
        Log.v("currentMinute", String.valueOf(minute1));

        next_button = (Button) findViewById(R.id.next_button);
        next_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSleepActivity2(v);
            }
        });
    }

    public void openSleepActivity2(View view) {
        Intent i = new Intent(this, sleep_activity2.class);

        i.putExtra("hourInput1", hour1);
        i.putExtra("minuteInput1", minute1);

        startActivity(i);
    }

    public void backto_home() {
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);

    }
}
