package com.example.agilesavev2.fragments.pie_chart;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.agilesavev2.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PieChartFragment extends Fragment implements PieChartContract.PieChartView{
    private HashMap<String, Double> data;
    private String centreValue="",centreValueTop="",centreValueBottom="";
    private boolean tagsVisibile=true;
    private float holeRadius = 10f;
    private int textColor = Color.BLACK;
    private float textSize=10;
    private ArrayList<Integer> customColour;

    public PieChartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data = data==null ? new HashMap<>() : data;
        drawPieChart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pie_chart, container, false);
    }

    @Override
    public void parseData(HashMap<String, Double> map) {
        if(map!=null) data=map;
    }

    public void setCustomColour(ArrayList<Integer> customColour){
        this.customColour = customColour;
    }


    @Override
    public void drawPieChart() {
        //get pie chart
        PieChart pieChart = getView().findViewById(R.id.pie_chart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setHoleColor(getResources().getColor(R.color.transparent));
        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.getLegend().setTextColor(Color.BLACK);
        pieChart.setHoleRadius(holeRadius);
        pieChart.setCenterTextColor(textColor);
        pieChart.setCenterTextSize(textSize);


        if(!tagsVisibile){
            pieChart.getLegend().setEnabled(false);
        }


        pieChart .getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        pieChart .getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        pieChart .getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);

        //entries
        ArrayList<PieEntry> entries = new ArrayList<>();


        int totalExpense = 0;
        boolean empty=false;
        if(data==null || data.isEmpty()){//show an empty pie chart is data given is empty
            entries.add(new PieEntry(100,""));
            empty=true;

        }else {
            for (Map.Entry<String, Double> entry : data.entrySet()) {
                entries.add(new PieEntry(Float.parseFloat(String.valueOf(entry.getValue())), entry.getKey()));
                totalExpense += entry.getValue();
                pieChart.setDrawEntryLabels(false);
                pieChart.setClickable(false);
            }

            pieChart.setCenterText(centreValueTop+"\n"+centreValue+"\n"+centreValueBottom);
        }



        //get colours
        ArrayList<Integer> colours = new ArrayList<>();
        if(customColour==null || customColour.isEmpty()) {
            for (int color : ColorTemplate.MATERIAL_COLORS) colours.add(color);
            for (int color : ColorTemplate.VORDIPLOM_COLORS) colours.add(color);
        }else {
            colours = customColour;
        }

        //dataset
        PieDataSet dataSet = new PieDataSet(entries,"");
        dataSet.setSliceSpace(4f);
        dataSet.setSelectionShift(4f);

        if(empty){
            dataSet.setColors(Color.GRAY);
        }else{
            dataSet.setColors(colours);
        }

        PieData pieData= new PieData(dataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);



        pieChart.setData(pieData);
        pieChart.invalidate();

        pieChart.setDrawEntryLabels(false);
        pieChart.getData().setDrawValues(false);

    }



    @Override
    public void setCentreValue(String string) {
        centreValue=string;
    }

    @Override
    public void setCentreValueTop(String string) {
        centreValueTop=string;
    }

    @Override
    public void setCentreValueBottom(String string) {
        centreValueBottom=string;
    }

    @Override
    public void setTagsVisible(Boolean bool) {
        tagsVisibile=bool;
    }

    @Override
    public void setHoleRadius(float val) {
        holeRadius=val;
    }

    @Override
    public void setFontColor(int color) {
        textColor=color;
    }

    @Override
    public void setTextSize(float size) {
        textSize=size;
    }
}