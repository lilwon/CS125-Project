package com.example.cs125_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // Sleep activity button
    private Button enterSleepActivity;

    // User Profile/Settings
    private Button userProfileBtn;

    // User Previous Recommendations
    private Button prevRecs;

    // Set Reminders
    private Button setReminderBtn;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Open Sleep activity
        enterSleepActivity = (Button) getView().findViewById(R.id.enterSleepActivity);
        enterSleepActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSleepActivity();
            }
        });

        // Open User Profile/Settings
       userProfileBtn = (Button) getView().findViewById(R.id.userProfile);
       userProfileBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openUserInfoActivity();
           }
       });

        // Open Prev Recommendations
        prevRecs = (Button) getView().findViewById(R.id.sleepRec);
        prevRecs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPrevRecActivity();
            }
        });

        setReminderBtn = (Button) getView().findViewById(R.id.reminderBtn);
        setReminderBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openReminderActivity();
            }
        });
    }

    public void openSleepActivity() {
        Intent i = new Intent(getContext(), sleep_activity.class);
        startActivity(i);
    }

    public void openUserInfoActivity() {
        Intent i = new Intent(getContext(), UserInfo.class);
        startActivity(i);
    }

    public void openPrevRecActivity() {
        Intent i = new Intent(getContext(), PreviousRecommendations.class);
        startActivity(i);
    }

    public void openReminderActivity() {
        Intent i = new Intent(getContext(), RemindersActivity.class );
        startActivity(i);
    }
}