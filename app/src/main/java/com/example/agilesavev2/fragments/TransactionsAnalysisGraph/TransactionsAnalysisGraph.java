package com.example.agilesavev2.fragments.TransactionsAnalysisGraph;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.agilesavev2.R;
import com.example.agilesavev2.utils.BarChartValueFormatter;
import com.example.agilesavev2.utils.ColorMisc;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;

// line graph imports

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionsAnalysisGraph#"newInstance"} factory method to
 * create an instance of this fragment.
 */
public class TransactionsAnalysisGraph extends Fragment implements TransactionsContract.TransactionsView, com.example.agilesavev2.assets.Fragment {

    private View layout;
    private Intent intent;
    private TransactionsContract.TransactionsPresenter presenter;
    private BarChart barChartLeft;
    private BarChart barChartRight;

    public TransactionsAnalysisGraph() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intent= getActivity().getIntent();
        return inflater.inflate(R.layout.fragment_transactions_analysis, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new TransactionsPresenter(intent,this, getContext());
        layout=view;

        // retrieve data
        getData();

        // create graph
        setupBarCharts();

        ((com.example.agilesavev2.assets.View)getActivity()).setAdapter(this);
    }

    @Override
    public void setupBarCharts(){

        barChartLeft=layout.findViewById(R.id.bar_chart_expenses_analysis_left);
        barChartRight=layout.findViewById(R.id.bar_chart_expenses_analysis_right);

        barChartLeft.getAxisLeft().setDrawGridLines(false);
        barChartLeft.getAxisRight().setDrawGridLines(false);
        barChartLeft.getXAxis().setDrawGridLines(false);

        // set color
        barChartLeft.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
        barChartLeft.getAxisRight().setTextColor(Color.WHITE); // left y-axis
        barChartLeft.getAxisRight().setEnabled(false);

        barChartLeft.getXAxis().setTextColor(Color.WHITE);
        barChartLeft.getLegend().setTextColor(Color.WHITE);
        barChartLeft.getDescription().setTextColor(Color.WHITE);
        barChartLeft.getDescription().setEnabled(false);

        List<String> xAxisValues = new ArrayList<>(Arrays.asList("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"));
        barChartLeft.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
        barChartLeft.getXAxis().setTextSize(10f);
        barChartLeft.getDescription().setEnabled(false);
        barChartLeft.getLegend().setEnabled(false);
        barChartLeft.getAxisLeft().setAxisLineWidth(1.0f);
        barChartLeft.getXAxis().setAxisLineWidth(1.0f);

        // bar right

        barChartRight.getAxisLeft().setDrawGridLines(false);
        barChartRight.getAxisRight().setDrawGridLines(false);
        barChartRight.getXAxis().setDrawGridLines(false);

        // set color
        barChartRight.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
        barChartRight.getAxisRight().setTextColor(Color.WHITE); // left y-axis
        barChartRight.getAxisRight().setEnabled(false);

        barChartRight.getXAxis().setTextColor(Color.WHITE);
        barChartRight.getLegend().setTextColor(Color.WHITE);
        barChartRight.getDescription().setTextColor(Color.WHITE);
        barChartRight.getDescription().setEnabled(false);

        barChartRight.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
        barChartRight.getXAxis().setTextSize(10f);
        barChartRight.getDescription().setEnabled(false);
        barChartRight.getLegend().setEnabled(false);

        barChartRight.getAxisLeft().setAxisLineWidth(1.0f);
        barChartRight.getXAxis().setAxisLineWidth(1.0f);


    }

    @Override
    public void updateLabels(HashMap<String, double[]> transactionMap){

        TextView budgetPeriodStartDate= layout.findViewById(R.id.budget_period_from_dynamic);

        //predicted_remaining_budget_dynamic
        TextView problemDay= layout.findViewById(R.id.bar_chart_expenses_analysis_label_problem_day_dynamic);

        float maxFreq = 0;
        float maxSpend = 0;
        String problemDayString = "MONDAY";

        for (String day : new String[] {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"}){

            double[] values = transactionMap.get(day);

            if ((float)values[0] >= maxFreq && (float)values[1] >=maxSpend){
                maxFreq=(float)values[0];
                maxSpend=(float)values[1];
                problemDayString = day;
            }

        }

        problemDay.setText(problemDayString);
        //predicted_remaining_budget_dynamic
        TextView budgetPeriodEndDate= layout.findViewById(R.id.budget_period_to_dynamic);

        // get budget period
        HashMap<String, String> dates = presenter.getBudgetPeriod();

        // set label text
        budgetPeriodStartDate.setText(dates.get("startDate"));
        budgetPeriodEndDate.setText(dates.get("endDate"));
    }



    @Override
    public void drawBarChartLeft(HashMap<String, double[]> transactionMap){

        barChartLeft.animateY(1000,Easing.EaseInOutQuad);

        // get days
        String[] days = new String[] {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"};

        ArrayList<BarEntry> entries = new ArrayList<>();


        float max = 0;
        float min = Float.MAX_VALUE;
        float i=0;
        for (String day : days){
            double[] values = transactionMap.get(day);
            entries.add(new BarEntry(i, (float)values[0]));
            max = Math.max(max,(float)values[0]);
            min = Math.min(min,(float)values[0]);
            i++;
        }

        int[] colors = new int[7];
        int k = 0;
        for (String day : days){
            double[] values = transactionMap.get(day);
            float value = (float)values[0];
            float h = 130 + (value - min) * (0 - 130) / (max - min);
            colors[k] = ColorMisc.HSBtoRGB(h/360.0f,0.8f,1.0f);
            k++;
        }

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setValueTextColors(Arrays.asList(colors[0], colors[1],colors[2],colors[3],colors[4],colors[5],colors[6]));
        dataSet.setColors(colors);

        BarData barData = new BarData(  dataSet);
        barData.setValueTextSize(10f);
        barData.setBarWidth(0.8f);

        // set value formatter to round values  the nearest integer
        dataSet.setValueFormatter(new BarChartValueFormatter(0));

        barChartLeft.setData(barData);
        barChartLeft.invalidate(); // refresh

    }
    @Override
    public void drawBarChartRight(HashMap<String, double[]> transactionMap){

        // animate
        barChartRight.animateY(1000,Easing.EaseInExpo);

        // get days
        String[] days = new String[] {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"};

        ArrayList<BarEntry> entries = new ArrayList<>();

        float max = 0;
        float min = Float.MAX_VALUE;

        float i=0;
        for (String day : days){

            double[] values = transactionMap.get(day);
            entries.add(new BarEntry(i, (float)values[1]));

            max = Math.max(max,(float)values[1]);
            min = Math.min(min,(float)values[1]);

            i++;
        }

        int[] colors = new int[7];
        int k = 0;

        for (String day : days){
            double[] values = transactionMap.get(day);
            float value = (float)values[1];
            float h = 130 + (value - min) * (0 - 130) / (max - min);

            colors[k] = ColorMisc.HSBtoRGB(h/360.0f,0.8f,1.0f);
            k++;
        }

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setValueTextColors(Arrays.asList(colors[0], colors[1],colors[2],colors[3],colors[4],colors[5],colors[6]));
        dataSet.setColors(colors);


        BarData barData = new BarData(  dataSet);
        barData.setValueTextSize(10f);
        barData.setBarWidth(0.8f);
        // set value formatter to round values  the nearest integer
        String symbol = Currency.getInstance(getActivity().getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();
        dataSet.setValueFormatter(new BarChartValueFormatter(1, symbol));

        barChartRight.setData(barData);
        barChartRight.invalidate(); // refresh
    }

    @Override
    public void drawBarCharts(HashMap<String, double[]> transactionMap){
        drawBarChartLeft(transactionMap);
        drawBarChartRight(transactionMap);
    }

    @Override
    public String getID() {
        Intent intent = getActivity().getIntent();
        return intent.getStringExtra("ID");
    }


    @Override
    public void getData(){
        presenter.getData();
    }
    @Override
    public void refreshFragment() {
    }

}
