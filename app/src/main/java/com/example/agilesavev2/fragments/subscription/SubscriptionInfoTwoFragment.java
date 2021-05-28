package com.example.agilesavev2.fragments.subscription;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.agilesavev2.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class SubscriptionInfoTwoFragment extends Fragment {
    private String info1, info2;
    private HorizontalBarChart barChart;
    private ArrayList<Double> values;

    public SubscriptionInfoTwoFragment() {
        // Required empty public constructor
    }
    public SubscriptionInfoTwoFragment(String info1 , String info2, ArrayList<Double> values) {
        // Required empty public constructor
        this.info1 = info1;
        this.info2 = info2;
        this.values = values;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscription_info_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView info1Tv = view.findViewById(R.id.info1);
        TextView info2Tv = view.findViewById(R.id.info2);

        info1Tv.setText(info1);
        info2Tv.setText(info2);
        if(info2.trim().equals("")){
            info2Tv.setHeight(0);
            info2Tv.setVisibility(View.INVISIBLE);
        }



        //setting up bar chart
        barChart = view.findViewById(R.id.barChart);
        setData(2, 50);



    }
    private void setData(int count, double range){
        ArrayList<BarEntry> y_vals = new ArrayList<>();
        float barWidth = 9f;
        float spacedBar = 1f;
        for(int i=0; i<values.size(); i++){
            double val = values.get(i);
            y_vals.add(new BarEntry(i*spacedBar, (float) val));
        }
        ArrayList<Integer> colours = new ArrayList<>();
        colours.add(R.color.agileSaverPurple2);
        colours.add(Color.RED);

        BarDataSet set;
        set = new BarDataSet(y_vals, "Differences per month");
        set.setColors(colours);
        BarData data = new BarData(set);
        barChart.getXAxis().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setTextColor(Color.WHITE);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setData(data);


    }
}