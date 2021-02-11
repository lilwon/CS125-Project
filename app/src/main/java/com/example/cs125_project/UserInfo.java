package com.example.cs125_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserInfo extends AppCompatActivity  {

    private Button user_info_next_button;

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
    }

    public void openSleepActivity2() {
        Intent i = new Intent(this, sleep_activity2.class);
        startActivity(i);
    }
}
