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

public class SleepFeedback extends AppCompatActivity {
    RatingBar ratingBar;
    Button sleepfb_back;
    Button sleepfb_next;
    public float rating;
    String currentDate;

    // get the current user
    private static final String USERS = "Users";

    // Lets try to write to Firebase DB
    private DatabaseReference db;

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
        rating = ratingBar.getRating();

        Intent i = new Intent(this, ActiveLevelFeedback.class);

        Log.v("rating", String.valueOf(rating));

        // Create a Bundle so we can use putExtras (vs putExtra)
        Bundle sendData = new Bundle();
        sendData.putFloat("rating", rating);
        i.putExtras(sendData);

        storeToDatabase();

        startActivity(i);
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void storeToDatabase() {

        // Get current user thats logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String useruid = user.getUid(); // get their unique id
        db = FirebaseDatabase.getInstance().getReference(); // now change that user's Firebase data

        // WRITING data to a user
        // Need to make it dynamic to store in Firebase
        // uniqueDate would be the day they slept
        // And then from there you would add the hours slept
        currentDate = getDateTime();
        db.child("Users").child(useruid).child("sleepRating").push().child(currentDate).child("sleepRating").setValue(rating);

    }
}