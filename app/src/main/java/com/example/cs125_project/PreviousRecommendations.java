package com.example.cs125_project;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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

import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PreviousRecommendations extends AppCompatActivity {
    // Array of strings...
    ListView simpleList;
    ArrayList<String> previous_rec_list = new ArrayList<String>();
            //String mobileArray[] = {"sleep rec1", "sleep rec2", "sleep rec3", "sleep rec4", "sleep rec5", "sleep rec6"};

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userid = user.getUid();

    private static final String USERS = "Users";
    private String year = getYear();
    private String month = getMonth();

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = rootRef.child(USERS);

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(USERS).child(userid).child("hourSlept").child(year).child(month);


    // Included a DrawerLayout so must add DL code
    DrawerLayout dl;

    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_recommendations_main);

        previous_rec_list.clear();
        // READ DATA FROM FIREBASE
        ref.addValueEventListener(new ValueEventListener() {

            Integer best_rec;
            Integer better_rec;
            Integer mod_rec;

            String currentDate = getDateTime();

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                previous_rec_list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("best_rec").exists())
                    {
                        // ds.child("hourSlept").child(currentDate).child("best_rec").getValue())
                        best_rec = ds.child("best_rec").getValue(Integer.class);
                        better_rec = ds.child("better_rec").getValue(Integer.class);
                        mod_rec = ds.child("mod_rec").getValue(Integer.class);
                        String entry = "2021/03/" + ds.getKey() + ": \n" + "Best: " + best_rec + ", Better: " + better_rec + ", Moderate: " + mod_rec;
                        /*
                        if (!previous_rec_list.contains(entry)) {
                            previous_rec_list.add(String.valueOf(entry));
                        }
                        */
                        previous_rec_list.add(String.valueOf(entry));
                        Log.v("FULL ARRAY", String.valueOf(previous_rec_list));
//                    Log.v("best rec ", String.valueOf(previous_rec_list.get(0)));
//                    Log.v("better rec ", String.valueOf(previous_rec_list.get(1)));
//                    Log.v("mod rec ", String.valueOf(previous_rec_list.get(2)));
                    }
                }
                set_array();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }


        });


        dl = (DrawerLayout) findViewById(R.id.drawer_layout);

        backBtn = (Button) findViewById(R.id.back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to Dashboard
                openDashboard();
            }
        });

    }

    public void set_array() {
        simpleList = (ListView) findViewById(R.id.prev_recs);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_previous_recommendations, R.id.textView, previous_rec_list);
        simpleList.setAdapter(arrayAdapter);
    }



    // Drawer Layout navigation..
    public void ClickMenu(View view) {
        MainActivity.openDrawer(dl);
    }

    public void ClickLogo(View view) {
        MainActivity.closeDrawer(dl);
    }

    public void ClickHome(View view) {
        MainActivity.redirectActivity(this, ProfileActivity.class);
    }

    public void ClickDashboard(View view) {
        MainActivity.redirectActivity(this, Dashboard.class);
    }

    public void openDashboard() {
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }

    // get month and year for Firebase db
    private String getYear() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date yr = new Date();
        return dateFormat.format(yr);
    }

    private String getMonth() {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date month = new Date();
        return dateFormat.format(month);
    }

    private String getDay() {
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date day = new Date();
        return dateFormat.format(day);
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }



}