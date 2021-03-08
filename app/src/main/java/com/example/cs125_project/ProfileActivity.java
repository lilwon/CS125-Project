package com.example.cs125_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ProfileActivity extends AppCompatActivity {

    private TextView usernameText;
    private TextView idealHrsText;
    private TextView responseText; // Create multiple responses if below/above/doing well

    // How users labeled in Firebase DB
    private static final String USERS = "Users";
    private String curUser;

    private Button toDashboard;

    DrawerLayout dl;

    // TAG for errors
    private final String TAG = this.getClass().getName().toUpperCase();

    // To Display average hours
    private TextView avgHrsText;

    private String year = getYear();
    private String month = getMonth();
    // Reading data from Firebase
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userid = user.getUid();

    private int idealHrsUserVal;
    private double avg; // Hold avg value outside

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toDashboard = (Button) findViewById(R.id.toDashboard);
        toDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDashboard();
            }
        });

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Get intent from MainActivity/login
        Intent i = getIntent();
        curUser = getIntent().getStringExtra("email");

        //  Use database Ref to get Firebase info instead
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USERS);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(USERS).child(userid).child("hourSlept").child(year).child(month);

        Log.v("CURRENT USER", curUser); // returns the Email of the user
        Log.v("USERID", userRef.getKey()); // the root = Users from Firebase DB

        // Changing this TextView
        usernameText = findViewById(R.id.welcomeBanner);
        idealHrsText = findViewById(R.id.ideal_hrs_txt);
        // Reading from the Firebase db for name
        userRef.addValueEventListener(new ValueEventListener() {
            String userFullName;
            String idealHrsString;
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // look through every instance of user
                for ( DataSnapshot ds : snapshot.getChildren() ) {
                    // if login email matches current email
                    if ( ds.child("email").getValue().equals(curUser) ) {
                        // get the user name from the Firebase db
                        // usernameText.setText(ds.child("fullname").getValue(String.class));

                        userFullName = ds.child("fullname").getValue(String.class);

                        idealHrsString = String.valueOf(ds.child("goals").child("idealHr").getValue(Integer.class));
                        idealHrsUserVal = Integer.valueOf(idealHrsString);

                        Log.v("sum", String.valueOf(idealHrsUserVal));

                        createResponse();

                        break;
                    }
                }
                // Change text to name
                usernameText.setText(userFullName);
                // Change ideal hours text
                idealHrsText.setText(idealHrsString);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.v(TAG, "Failed to read value.", error.toException());
            }
        });

        avgHrsText =(TextView) findViewById(R.id.avg_hr_sleep);
        // Calculating average from Firebase data
        ref.addValueEventListener(new ValueEventListener() {
            ArrayList<Integer> hoursList = new ArrayList<Integer>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Get all the values from Database
                hoursList.clear();
                for (DataSnapshot ds : snapshot.getChildren() ) {
                    int val;
                    Number hourVal = (Number) ds.child("hours").getValue();
                    val = hourVal.intValue();
                    hoursList.add(val);
                }

                // MAY NEED TO CHANGE TO DOUBLES LATER
                int sum = 0;
                for ( Integer d : hoursList ) {
                    sum += d;
                }

                Log.v("Sum", String.valueOf(sum));
                avg = (double)sum /(double) hoursList.size();
                String avgStr = String.format("%.2f", avg);
                Log.v("sum", avgStr);

                // Change text to avg hours
                avgHrsText.setText(avgStr);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Create some response to tell user that they're doing well/not

    }
    public void openSleepActivity() {
        Intent i = new Intent(this, sleep_activity.class);
        startActivity(i);
    }

    // Below is for Navigation Drawer
    public void ClickMenu(View view) {
        MainActivity.openDrawer(dl);
    }

    public void ClickLogo(View view) {
        MainActivity.closeDrawer(dl);
    }

    // ProfileActivity is now Home
    public void ClickHome(View view) {
        MainActivity.closeDrawer(dl);
    }

    public void ClickDashboard(View view) {
        MainActivity.redirectActivity(this, Dashboard.class);
    }

    public void toDashboard() {
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }


    @Override
    protected void onPause() {
        super.onPause();

        MainActivity.closeDrawer(dl);
    }

    // get month and year for Firebase db
    private String getYear () {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date yr = new Date();
        return dateFormat.format(yr);
    }

    private String getMonth() {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date month = new Date();
        return dateFormat.format(month);
    }

    private void createResponse() {
        responseText = (TextView) findViewById(R.id.response_text);

        // If your average is 2 hours below your ideal goal

        Log.v("sum1", String.valueOf(idealHrsUserVal));

        if ( avg < (double)idealHrsUserVal-2 ) {
            responseText.setText("You aren't getting enough sleep at night. " +
                    "Please follow our recommendations to help improve your sleep schedule.");
        }
        else  {
            responseText.setText("You are currently within your goal! Keep it up!");
        }

        //responseText.setText();
    }
}