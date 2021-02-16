package com.example.cs125_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfo extends AppCompatActivity  {

    private Button user_info_next_button;
    private Button user_info_back_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        user_info_next_button = (Button) findViewById(R.id.user_info_next_button);
        user_info_next_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSleepActivity2();
            }
        });

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

    public void openDashboard() {
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }

}
