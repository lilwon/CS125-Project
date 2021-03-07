package com.example.cs125_project;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PreviousRecommendations extends AppCompatActivity {
    // Array of strings...
    ListView simpleList;
    String mobileArray[] = {"sleep rec1", "sleep rec2", "sleep rec3", "sleep rec4", "sleep rec5", "sleep rec6"};

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userid = user.getUid();
    DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

    // Included a DrawerLayout so must add DL code
    DrawerLayout dl;

    private Button backBtn;

    @Override   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_recommendations_main);

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);

        simpleList = (ListView)findViewById(R.id.prev_recs);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_previous_recommendations, R.id.textView, mobileArray);
        simpleList.setAdapter(arrayAdapter);

        backBtn = (Button) findViewById(R.id.back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to Dashboard
                openDashboard();
            }
        });
    }

    // Drawer Layout navigation..
    public void ClickMenu(View view) {
        MainActivity.openDrawer(dl);
    }

    public void ClickLogo(View view) {
       MainActivity.closeDrawer(dl);
    }

    public void ClickHome(View view) {
        MainActivity.redirectActivity(this, Dashboard.class);
    }

    public void ClickDashBoard(View view) {
        MainActivity.redirectActivity(this, Dashboard.class);
    }

    public void openDashboard() {
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }
}