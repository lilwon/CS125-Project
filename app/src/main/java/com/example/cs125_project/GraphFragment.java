package com.example.cs125_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraphFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        BarChart chart = (BarChart) view.findViewById(R.id.barChart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        // Temp values will need to accommodate for Firebase info given
        // when user logs their amt of sleep
        // x-axis can be per day logged
        // y-axis amt of time slept
        // Creating DataSet
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 0));
        entries.add(new BarEntry(10f, 0));
        entries.add(new BarEntry(0, 0));

        ArrayList<String> labels = new ArrayList<>();
        labels.add("February 1");
        labels.add("February 2");
        labels.add("February 3");
        labels.add("February 4");

        BarDataSet ugh= new BarDataSet(entries, "Hours Slept");

        BarData temp = new BarData(ugh);

        chart.setData(temp);
        chart.animateXY(2000, 2000);
        chart.invalidate();

        return view;
    }
/*
    public ArrayList createDataSet() {
        // data set to be returned



        return entries;
    }

    public ArrayList createXAxis() {
        // Define x-axis


        return labels;
    }
 */
}

