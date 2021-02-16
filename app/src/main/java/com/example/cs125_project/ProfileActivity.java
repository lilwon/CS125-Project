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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity {
    private Button sleepBtn;

    private TextView usernameText;

    // How users labeled in Firebase DB
    private static final String USERS = "Users";
    private String curUser;

    DrawerLayout dl;

    // TAG for errors
    private final String TAG = this.getClass().getName().toUpperCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Get intent from MainActivity/login
        Intent i = getIntent();
        curUser = getIntent().getStringExtra("email");

        //  Use database Ref to get Firebase info instead
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USERS);

        Log.v("CURRENT USER", curUser); // returns the Email of the user
        Log.v("USERID", userRef.getKey()); // the root = Users from Firebase DB

        // Changing this TextView
        usernameText = findViewById(R.id.welcomeBanner);

        // Reading from the Firebase db
        userRef.addValueEventListener(new ValueEventListener() {
            String userFullName;
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // look through every instance of user
                for ( DataSnapshot ds : snapshot.getChildren() ) {
                    // if login email matches current email
                    if ( ds.child("email").getValue().equals(curUser) ) {
                        // get the user name from the Firebase db
                        // usernameText.setText(ds.child("fullname").getValue(String.class));

                        // somehow Database value of fullname changed to fullName..
                        userFullName = ds.child("fullName").getValue(String.class);
                        break;
                    }
                }
                // Change text to name
                usernameText.setText(userFullName);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.v(TAG, "Failed to read value.", error.toException());
            }
        });

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


    @Override
    protected void onPause() {
        super.onPause();

        MainActivity.closeDrawer(dl);
    }
}