package com.example.cs125_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sleep_activity2 extends AppCompatActivity {

    private Button next_button2;
    private Button back_button2;

    // hold data for sleep_activity2
    private int hr2;
    private int min2;

    // This is the time slept from sleep_activity
    private int hr1;
    private int min1;

    // Data to send to next activity and database
    private int minTime;
    private int hrTime;

    // get the current user
    private static final String USERS = "Users";

    // Lets try to write to Firebase DB
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_activity2);

        // bundle contains the data from the previous sleep_activity
        Bundle timeSlept = getIntent().getExtras();

        // Store the items
        hr1 = timeSlept.getInt("hrInput1");
        min1 = timeSlept.getInt("minInput1");

        // Verify with Log.v
        Log.v("TimeSleptHr", String.valueOf(hr1));
        Log.v("TimeSleptMin", String.valueOf(hr2));

        TimePicker simpleTimePicker=(TimePicker)findViewById(R.id.simpleTimePicker2); //initiate a time picker

        next_button2 = (Button) findViewById(R.id.next_button2);
        next_button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                hr2 = simpleTimePicker.getCurrentHour();
                min2 = simpleTimePicker.getCurrentMinute();

                // Calculate how many hours slept that night
                calculateHoursSlept();

                // Store to DB here?
                storeToDatabase();
                openSleepActivity2(); // open Sleep Feedback
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

    public void calculateHoursSlept ( ) {
        // calculate hours
        // remember that time is in 24 hour
        // assuming that no one sleeps for more than 24 hours

        if ( min2 >= min1 ) {
            minTime = Math.abs(min2-min1);
        }

        // round up the minTime so we dont need to deal with decimal Time
        if ( minTime > 30 ) {
            hrTime++;
        }

        // lazy coding.. so only going to take into account user sleep at night
        // and wake up in the morning
        int tempHour = 24 - hr1;
        hrTime += hr2 + tempHour; // Save this to Firebase DB

        // Probably going to remove using minutes anyways..
    }

    public void storeToDatabase() {
        /*
        // Get the current user
        // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if ( user != null ) {
            // String name = user.getUid(); // Gets userId
            //Log.v("USER UID", name);
            // Start storing info

            user.child()
        }
         */
        // Get current user thats logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String useruid = user.getUid(); // get their unique id
        db = FirebaseDatabase.getInstance().getReference(); // now change that user's Firebase data

        // WRITING data to a user
        // Need to make it dynamic to store in Firebase
        // uniqueDate would be the day they slept
        // And then from there you would add the hours slept
        db.child("Users").child(useruid).child("hourSlept").child("uniqueDate").setValue("dateValue"); // MUST CHANGE uniqueDate for every new entry
        db.child("Users").child(useruid).child("hourSlept").child("uniqueDate").child("hours").setValue(hrTime);

    }

}
