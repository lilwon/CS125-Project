package com.example.cs125_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SleepRecommendation extends AppCompatActivity {
    private Button rec_back_button;

    private Button returnBtn;

    TextView a,b,c,d,e;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userid = user.getUid();
    DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_recommendation);

        //Display activity/sleep levels
        a = (TextView)findViewById(R.id.active_level_view);
        b = (TextView)findViewById(R.id.sleep_level_view);
        c = (TextView)findViewById(R.id.rec_hours_view);        //"Best" View
        d = (TextView)findViewById(R.id.rec_hrs_view2);         //"Better" View
        e = (TextView)findViewById(R.id.rec_hrs_view3);         //"Moderate" View

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String active = snapshot.child("activeRating").getValue().toString();
                String sleep = snapshot.child("sleepRating").getValue().toString();

                String hours_slept = snapshot.child("hourSlept").child("uniqueDate").child("hours").getValue().toString();
                String age = snapshot.child("age").getValue().toString();
                String rec_hours = calculate_sleep(hours_slept, age);

                a.setText(active);
                b.setText(sleep);
                c.setText(rec_hours);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        rec_back_button = (Button)findViewById(R.id.rec_back_button);
        rec_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backto_ActiveLevel();
            }
        });

        returnBtn = (Button) findViewById(R.id.returnDashboardBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { returnToDashboard(); }
        });

    }

    public void backto_ActiveLevel() {
        Intent i = new Intent(this, ActiveLevelFeedback.class);
        startActivity(i);
    }

    public void returnToDashboard() {
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }

    //AGE -> HOURS STATS (CDC)
    //0-3 Months = 14-17 hrs
    //4-12 Months = 12-16 hrs
    //1-2 Years = 11-14 hrs
    //3-5 Years = 10-13 hrs
    //6-12 Years = 9-12 hrs
    //13-18 Years = 8-10 hrs
    //18-60 Years = 7+ hrs
    //61-64 Years = 7-9 hrs
    //65+ Years = 7-8 hrs

    //WORK IN PROGRESS (ONLY TAKING AGE CURRENTLY)
    //To Do:
    //Have to take into account prev hours, activity feedback, sleep feedback, etc.
    //Have to get best, better, moderate
        //PLAN (MAYBE):
        //1) Calculate the sleep hours and then increment by 1 or decrement by 1 to get better/moderate

    public String calculate_sleep(String hours_slept, String age)
    {
        //Change into int to calculate
        Integer int_age = Integer.parseInt(age);
        Integer int_hrs_slept = Integer.parseInt(hours_slept);

        if (int_age < 0.33)                         //Age 0-3 Months
        {
            return "14";
        }
        if (int_age >= 0.33 && int_age <= 12)       //Age 4-12 Months
        {
            return "12";
        }
        if (int_age >= 1 && int_age <= 2)           //Age 1-2
        {
            return "14";
        }
        else if (int_age >= 3 && int_age <= 5)      //Age 3-5
        {
            return "10";
        }
        else if (int_age >= 6 && int_age <= 12)     //Age 6-12
        {
            return "9";
        }
        else if (int_age  >= 13 && int_age <= 18)   //Age 13-18
        {
            return "8";
        }
        else if (int_age >= 19 && int_age <= 60)    //Age 19-60
        {
            return "7";
        }
        else if (int_age >= 61 && int_age <= 64)    //Age 61-64
        {
            return "7";
        }
        else                                        //Age 65+
        {
            return "7";
        }

    }

}