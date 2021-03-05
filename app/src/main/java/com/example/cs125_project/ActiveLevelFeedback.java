package com.example.cs125_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActiveLevelFeedback extends AppCompatActivity {
    RatingBar ratingBar;
    Button activefb_back;
    Button activefb_next;
    public float rating;
    String currentDate;

    // get the current user
    private static final String USERS = "Users";

    // Lets try to write to Firebase DB
    private DatabaseReference db;

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
                String rating_num = String.valueOf(ratingBar.getRating());
                Toast.makeText(ActiveLevelFeedback.this, rating_num+"", Toast.LENGTH_SHORT).show();
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
        rating = ratingBar.getRating();

        Intent i = new Intent(this, SleepRecommendation.class);

        Log.v("activeRating", String.valueOf(rating));

        // Create a Bundle so we can use putExtras (vs putExtra)
        Bundle sendData = new Bundle();
        sendData.putFloat("activeRating", rating);
        i.putExtras(sendData);

        storeToDatabase();

        startActivity(i);
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    //ORIGINAL
    /*public void storeToDatabase() {

        // Get current user thats logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String useruid = user.getUid(); // get their unique id
        db = FirebaseDatabase.getInstance().getReference(); // now change that user's Firebase data

        // WRITING data to a user
        // Need to make it dynamic to store in Firebase
        // currentDate would be the day they slept
        // And then from there you would add the hours slept
        // thinking there may be a more efficient way to store this data...
        currentDate = getDateTime();
        db.child("Users").child(useruid).child("activeRating").push().child(currentDate).child("activeRating").setValue(rating);

    }*/

    //By: Vivian
    //Only writing the current feedback
    //Plan to bundle up active_level feedback, sleep_level feedback, hours, data into one node

    public void storeToDatabase() {

        // Get current user thats logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String useruid = user.getUid(); // get their unique id
        db = FirebaseDatabase.getInstance().getReference(); // now change that user's Firebase data

        // WRITING data to a user
        // Need to make it dynamic to store in Firebase
        // currentDate would be the day they slept
        // And then from there you would add the hours slept
        // thinking there may be a more efficient way to store this data...
        currentDate = getDateTime();
        db.child("Users").child(useruid).child("hourSlept").child(currentDate).child("activeRating").setValue(rating);

    }

}