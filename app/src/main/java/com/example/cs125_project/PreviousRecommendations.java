package com.example.cs125_project;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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


    @Override   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_recommendations_main);



        simpleList = (ListView)findViewById(R.id.prev_recs);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_previous_recommendations, R.id.textView, mobileArray);
        simpleList.setAdapter(arrayAdapter);
    }

}