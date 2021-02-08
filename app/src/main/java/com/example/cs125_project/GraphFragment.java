package com.example.cs125_project;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// Uses MPAndroidChart library
// More documentation here
// https://weeklycoding.com/mpandroidchart-documentation/

public class GraphFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Declare BarChart as global variable
    BarChart barChart;

    public GraphFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GraphFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GraphFragment newInstance(String param1, String param2) {
        GraphFragment fragment = new GraphFragment();
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
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        // set the view to the bar graph
        barChart = view.findViewById(R.id.barChart);

        // create Chart
        createBarChart();
        return view;
    }

    private void createBarChart() {
        ArrayList<Integer> values = new ArrayList<Integer>();
        ArrayList<BarEntry> entries = new ArrayList<>();

        // temp add values (this is the number of hours slept)
        // Random times.. Slept 5 hours for 5 days.
        for ( int i = 0; i < 5; i++ ) {
            values.add(i);
        }

        // Add entries to Bar Chart
        // IMPORTANT! Need to change these to days
        for (int i = 0; i < values.size(); i++ ) {
            BarEntry entry = new BarEntry(i, values.get(i).floatValue());
            entries.add(entry);
        }

        // Now add bar to the View
        BarDataSet ds = new BarDataSet(entries, "# of hours slept");

        barDataSetSetting(ds);
        barChartSetting();

        // Shows the bar on the screen
        BarData data = new BarData(ds);
        barChart.setData(data);
        barChart.invalidate(); // redraws the chart
        // barChart.notifyDataSetChanged(); // Used for dynamic data aka Firebase
    }

    // This controls the VISUALS of the DATASET on the charts/graph
    private void barDataSetSetting(BarDataSet ds) {
        // Color of the bars
        //ds.setColors(Color.parseColor("#"));

        // Remove values on top of the bars
        ds.setDrawValues(false);
    }

    // This controls the VISUALS of the CHART itself
    private void barChartSetting() {
        // Animate x and y values
        barChart.animateXY(2000, 2000);

        // move xAxis labels on the bottom
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // move to bottom
        xAxis.setGranularity(1f); // the line-spacing between each bar
        xAxis.setDrawGridLines(false); // remove gridlines

        // yAxis labels and stuff
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawLabels(false); // remove labels on right side
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setGranularity(0.5f);

        // Description text
        Description desc = barChart.getDescription();
        desc.setEnabled(false); // remove desc from graph
    }
}

