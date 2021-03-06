package com.example.cs125_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.Date;

public class SleepRecommendation extends AppCompatActivity {
    private Button rec_back_button;

    private Button returnBtn;

    private String year;
    private String month;
    private String day;

    String rec_hours;
    Integer int_best;
    Integer int_better;
    Integer int_mod;
    Float flt_activefb;
    Float flt_sleepfb;
    Integer best_start_sleep;
    Integer best_end_sleep;
    Integer bet_end_sleep;
    Integer mod_end_sleep;


    //Initialize all recommendations
    TextView best1,best2,best3,bet1,bet2,bet3,mod1,mod2,mod3;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userid = user.getUid();
    DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_recommendation);

        //Display activity/sleep levels
        best1 = (TextView)findViewById(R.id.best_view1);
        best2 = (TextView)findViewById(R.id.best_view2);
        best3 = (TextView)findViewById(R.id.best_view3);
        bet1 = (TextView)findViewById(R.id.better_view1);
        bet2 = (TextView)findViewById(R.id.better_view2);
        bet3 = (TextView)findViewById(R.id.better_view3);
        mod1 = (TextView)findViewById(R.id.mod_view1);
        mod2 = (TextView)findViewById(R.id.mod_view2);
        mod3 = (TextView)findViewById(R.id.mod_view3);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                year = getYear();
                month = getMonth();
                day = getDay();

                //Get info from Firebase
                //String hours_slept = snapshot.child("hourSlept").child("uniqueDate").child("hours").getValue().toString();
                String hours_slept = snapshot.child("hourSlept").child(year).child(month).child(day).child("hours").getValue().toString();
                String active = snapshot.child("hourSlept").child(year).child(month).child(day).child("activeRating").getValue().toString();
                String sleep = snapshot.child("hourSlept").child(year).child(month).child(day).child("sleepRating").getValue().toString();
                String age = snapshot.child("age").getValue().toString();

                //Calculate best, better, moderate hours
                //Calculated based on age, active feedback, sleep feedback
                rec_hours = calculate_sleep(age);
                int_best = Integer.parseInt(rec_hours);
                flt_activefb = Float.parseFloat(active);
                flt_sleepfb = Float.parseFloat(sleep);

                //If active levels = high and sleep levels = low, increment hour to sleep_rec
                if (flt_activefb >= 3 && flt_sleepfb <= 3)
                {
                    int_best++;
                }
                int_better = int_best + 1;
                int_mod = int_best + 2;

                //Calculate sleep schedules
                calc_schedule(int_best, age);

                //Display text on page
                best1.setText(best_start_sleep + ":00PM - " + best_end_sleep + ":00AM" + " (" + int_best + " hours)");
                best2.setText((best_start_sleep + 1) + ":00PM - " + (best_end_sleep + 1) + ":00AM" + " (" + int_best + " hours)");
                best3.setText((best_start_sleep - 1) + ":00PM - " + (best_end_sleep - 1) + ":00AM" + " (" + int_best + " hours)");
                bet1.setText(best_start_sleep + ":00PM - " + bet_end_sleep + ":00AM" + " (" + int_better + " hours)");
                bet2.setText((best_start_sleep + 1) + ":00PM - " + (bet_end_sleep + 1) + ":00AM" + " (" + int_better + " hours)");
                bet3.setText((best_start_sleep - 1) + ":00PM - " + (bet_end_sleep - 1) + ":00AM" + " (" + int_better + " hours)");
                mod1.setText(best_start_sleep + ":00PM - " + mod_end_sleep + ":00AM" + " (" + int_mod + " hours)");
                mod2.setText((best_start_sleep + 1) + ":00PM - " + (mod_end_sleep + 1) + ":00AM" + " (" + int_mod + " hours)");
                mod3.setText((best_start_sleep - 1) + ":00PM - " + (mod_end_sleep - 1) + ":00AM" + " (" + int_mod + " hours)");
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

        storeToDatabase();
        startActivity(i);
    }


    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void storeToDatabase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String useruid = user.getUid();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        String currentDate = getDateTime();
        // Create a new Node on database..
        db.child("Users").child(useruid).child("hourSlept").child(currentDate).child("best_rec").setValue(int_best);
        db.child("Users").child(useruid).child("hourSlept").child(currentDate).child("better_rec").setValue(int_better);
        db.child("Users").child(useruid).child("hourSlept").child(currentDate).child("mod_rec").setValue(int_mod);

    }

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

    private String getDay() {
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date d= new Date();
        return dateFormat.format(d);
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

    public String calculate_sleep(String age)
    {
        Integer int_age = Integer.parseInt(age);

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
    public void calc_schedule(Integer int_best, String age)
    {
        Integer int_age = Integer.parseInt(age);

        if (int_age < 0.33)                                     //Age 0-3 Months
        {
            best_start_sleep = 7;
        }
        if (int_age >= 0.33 && int_age <= 12)                   //Age 4-12 Months
        {
            best_start_sleep = 7;
        }
        if (int_age >= 1 && int_age <= 2)                       //Age 1-2
        {
            best_start_sleep = 8;
        }
        else if (int_age >= 3 && int_age <= 5)                  //Age 3-5
        {
            best_start_sleep = 8;
        }
        else if (int_age >= 6 && int_age <= 12)                //Age 6-12
        {
            best_start_sleep = 9;
        }
        else if (int_age  >= 13 && int_age <= 18)              //Age 13-18
        {
            best_start_sleep = 10;
        }
        else if (int_age >= 19 && int_age <= 60)               //Age 19-60
        {
            best_start_sleep = 10;
        }
        else if (int_age >= 61 && int_age <= 64)               //Age 61-64
        {
            best_start_sleep = 10;
        }
        else                                                   //Age 65+
        {
            best_start_sleep = 10;
        }
        best_end_sleep = (best_start_sleep + int_best) - 12;
        bet_end_sleep = (best_start_sleep + int_better) - 12;
        mod_end_sleep = (best_start_sleep + int_mod) - 12;

    }



}