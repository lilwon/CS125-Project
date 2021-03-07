package com.example.cs125_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfo extends AppCompatActivity  {

    //private Button user_info_next_button;
    private Button user_info_back_button;

    // If there's a DL you need to add the code for it.
    DrawerLayout dl;

    // Writing info to Firebase
    private DatabaseReference db;

    private EditText avgHrText;
    private EditText idealHrText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Don't need a next button.
        //user_info_next_button = (Button) findViewById(R.id.user_info_next_button);
        /*
        user_info_next_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSleepActivity2();
            }
        });
        */

        // Activate the drawer layout
        dl = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Get average amt of sleep user slept and their ideal amt of sleep
        avgHrText = (EditText) findViewById(R.id.avg_hour_sleep);
        idealHrText = (EditText) findViewById(R.id.ideal_sleep);

        // convert to Int
        String avgHr = avgHrText.getText().toString();
        String idealHr = idealHrText.getText().toString();

        int avgHrVal = Integer.parseInt(avgHr);
        int idealHrVal = Integer.parseInt(idealHr);

        // Store vals to DB
        storeToDatabase(avgHrVal, idealHrVal);

        user_info_back_button = (Button) findViewById(R.id.user_info_back_button);
        user_info_back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openDashboard();
            }
        });
    }

    public void openSleepActivity2() {
        Intent i = new Intent(this, sleep_activity2.class);
        startActivity(i);
    }

    public void ClickMenu(View view) {
        MainActivity.openDrawer(dl);
    }

    public void ClickLogo(View view) {
        MainActivity.closeDrawer(dl);
    }
    public void ClickHome(View view) {
        MainActivity.redirectActivity(this, Dashboard.class);
    }

    public void ClickDashboard(View view) {
        MainActivity.redirectActivity(this, Dashboard.class);
    }

    public void openDashboard() {
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }

    public void storeToDatabase(int avgHr, int idealHr) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String useruid = user.getUid();

        db = FirebaseDatabase.getInstance().getReference();

        // Create a new Node on database..
        db.child("Users").child(useruid).child("goals").child("currentAvg").setValue(avgHr);
        db.child("Users").child(useruid).child("goals").child("idealHr").setValue(idealHr);

    }
}
