package com.example.cs125_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class RemindersActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    // Need to set a default time to ~10pm or something.. if user doesn't
    // Set a time

    DrawerLayout dl;

    // User picks a Time to set
    private TimePicker picker;

    private Button returnBtn;

    // Time user set
    private TextView setTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);

        setTimeText = findViewById(R.id.time_set_text);

        picker = (TimePicker) findViewById(R.id.notification_time);
        picker.setIs24HourView(true);

        picker.setOnTimeChangedListener(this::onTimeSet);

        returnBtn = (Button) findViewById(R.id.returnDashboardBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToDashBoard();
            }
        });
    }
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

    // Return to dashboard
    public void returnToDashBoard() {
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker view, int hr, int min) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hr);
        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
        startAlarm(c);
    }

    private void updateTimeText(Calendar c) {
        // Only get hour and min
        String timeText = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        setTimeText.setText(timeText);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c) {
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);

        PendingIntent newIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        // Create alarm
        alarm.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), newIntent);
    }
}