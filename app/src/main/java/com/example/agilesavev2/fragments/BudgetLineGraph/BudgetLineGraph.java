package com.example.agilesavev2.fragments.BudgetLineGraph;

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
import com.example.agilesavev2.models.ai.Point;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;

// line graph imports

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetLineGraph#"newInstance"} factory method to
 * create an instance of this fragment.
 */
public class BudgetLineGraph extends Fragment implements BudgetContract.BudgetView, com.example.agilesavev2.assets.Fragment {

    private View layout;
    private Intent intent;
    private BudgetPresenter presenter;
    private LineChart lineChart;

    public BudgetLineGraph() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intent= getActivity().getIntent();
        return inflater.inflate(R.layout.fragment_budget_line_graph, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new BudgetPresenter(intent,this, getContext());
        layout=view;

        // retrieve data
        getData();

        // create graph
        setupLineChart();

        ((com.example.agilesavev2.assets.View)getActivity()).setAdapter(this);
    }

    @Override
    public void setupLineChart(){

        lineChart=layout.findViewById(R.id.line_chart_view);
        // set line chart properties
        lineChart.getAxisRight().setEnabled(false);

        // set chart minima / maxima
        lineChart.getAxisRight().setAxisMinimum(0);
        lineChart.getAxisLeft().setAxisMinimum(0);

        // set color
        lineChart.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
        lineChart.getXAxis().setTextColor(Color.WHITE);
        lineChart.getLegend().setTextColor(Color.WHITE);
        lineChart.getDescription().setTextColor(Color.WHITE);
        lineChart.getDescription().setEnabled(false);
        lineChart.getAxisLeft().setAxisLineWidth(1.0f);
        lineChart.getXAxis().setAxisLineWidth(1.0f);
        lineChart.getAxisRight().setAxisLineWidth(1.0f);

    }

    @Override
    public void updateLabels(float daysUntilBudgetRunsOut , float predictedBudgetBeforePayday){

         //days_until_label_dynamic
        TextView daysUntil= layout.findViewById(R.id.days_until_label_dynamic);

        //predicted_remaining_budget_dynamic
        TextView predictedBudget= layout.findViewById(R.id.predicted_remaining_budget_dynamic);

        //predicted_remaining_budget_dynamic
        TextView budgetPeriodStartDate= layout.findViewById(R.id.budget_period_from_dynamic);

        //predicted_remaining_budget_dynamic
        TextView budgetPeriodEndDate= layout.findViewById(R.id.budget_period_to_dynamic);

        // get budget period
        HashMap<String, String> dates = presenter.getBudgetPeriod();

        // set label text
        budgetPeriodStartDate.setText(dates.get("startDate"));
        budgetPeriodEndDate.setText(dates.get("endDate"));

        int colorLabel = Color.RED;

        if (predictedBudgetBeforePayday >=0){
            colorLabel = Color.GREEN;
        }

        daysUntil.setTextColor(colorLabel);
        predictedBudget.setTextColor(colorLabel);

        // clamp days between 0 and 30
        int days = (int)daysUntilBudgetRunsOut;
        String daysAsString = days > 31 ? ">31":Integer.toString(Math.min(days, 31));

        // create minus sign if budget is negative
        String sign = predictedBudgetBeforePayday < 0 ? "-" : "";
        String symbol = Currency.getInstance(getActivity().getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();
        // create budget as string
        String budgetAsString =  sign + symbol+String.format("%.2f", (double)Math.abs(predictedBudgetBeforePayday));

        daysUntil.setText(daysAsString);
        predictedBudget.setText(budgetAsString);

    }
    @Override
    public void drawLineGraphData(List<Point> dataPoints, List<Point>lineOfBestFit, float daysUntilBudgetRunsOut, float gradient, float yintercept, boolean budgetRanOut, float domainXMax, float domainYMax ){


        // animate ?
        lineChart.animateY(1000,Easing.EaseInOutQuad);

        // update line chart Y domain
        lineChart.getAxisRight().setAxisMaximum(domainYMax);
        lineChart.getAxisLeft().setAxisMaximum(domainYMax);
        lineChart.getXAxis().setAxisMaximum(domainXMax);

        if (dataPoints == null || dataPoints.size() == 0){
            System.out.print("no budget / transaction data points... terminating");
            return;
        }

        // data points dataset for line graph
        ArrayList<Entry> pointsDataSet = new ArrayList<>();

        // data points for line of best fit that spans from start of the data set to the end
        ArrayList<Entry> lineOfBestFitDataSet = new ArrayList<>();

        // data points from the last added point to period end
        ArrayList<Entry> lineOfBestFitRollingDataSet = new ArrayList<>();

        // get line of best fit points
        float startX = dataPoints.get( dataPoints.size()-1).getX();
        float startY = startX * gradient + yintercept;

        float endX = lineOfBestFit.get(1).getX();
        float endY = lineOfBestFit.get(1).getY();

        lineOfBestFitRollingDataSet.add(new Entry(startX, startY ));
        lineOfBestFitRollingDataSet.add(new Entry(endX, endY));

        // for data item in chart data packet
        for (Point point: dataPoints){
            pointsDataSet.add(new Entry(point.getX(),point.getY()));
        }

        // add custom labels to budget and predicted budget line
        LegendEntry l1=new LegendEntry("Remaining Budget", Legend.LegendForm.CIRCLE,10f,2f,null, Color.GRAY);
        LegendEntry l2=new LegendEntry("Predicted Budget", Legend.LegendForm.CIRCLE,10f,2f,null, Color.GREEN);

        lineOfBestFitDataSet.add(new Entry(lineOfBestFit.get(0).getX(),lineOfBestFit.get(0).getY() ));
        lineOfBestFitDataSet.add(new Entry(lineOfBestFit.get(1).getX(),lineOfBestFit.get(1).getY() ));

        LineDataSet lineLineOfBestFitDataSet = new LineDataSet(lineOfBestFitDataSet,"Predicted Budget");
        LineDataSet lineLineOfBestFitRollingDataSet = new LineDataSet(lineOfBestFitRollingDataSet,"");
        lineChart.getLegend().setCustom(new LegendEntry[]{l1,l2});

        lineLineOfBestFitDataSet.setDrawValues(false);
        lineLineOfBestFitRollingDataSet.setDrawValues(false);
        lineLineOfBestFitDataSet.setDrawCircles(false);
        lineLineOfBestFitRollingDataSet.setDrawCircles(false);

        // compute color
        int lineColor = Color.GREEN;

        if (budgetRanOut){
            lineColor = Color.RED;
        }

        // set color
        lineLineOfBestFitDataSet.setColor(lineColor, 100);
        lineLineOfBestFitRollingDataSet.setColor(lineColor, 255);
        l2.formColor = lineColor;


        LineDataSet lineDataSet = new LineDataSet(pointsDataSet,"Remaining budget");
        lineDataSet.setColor(Color.GRAY);
        lineDataSet.enableDashedLine(0, 1, 0);
        lineDataSet.setCircleColor(Color.GRAY);
        lineDataSet.setDrawValues(false);

        ArrayList<ILineDataSet> dataSets=new ArrayList<>();

        // draw line of best fit estimation overlay
        for (int i=1; i<11; i++){

            ArrayList<Entry> lineOfBestFitAccuracy = new ArrayList<>();

            float alpha  =  (float)(i)/10.0f;

            float endX2 = (-10-yintercept)/gradient;
            float endY2 = ((-10-yintercept)/gradient) * gradient + yintercept;

            float startX2 = startX + (endX2-startX) * alpha;
            float startY2 = startX2 * gradient + yintercept;

            lineOfBestFitAccuracy.add(new Entry(startX2, startY2 ));
            lineOfBestFitAccuracy.add(new Entry(endX2, endY2));

            LineDataSet lineOfBestFitAccuracyDataSet = new LineDataSet(lineOfBestFitAccuracy,"");
            lineOfBestFitAccuracyDataSet.setLineWidth(12f);
            lineOfBestFitAccuracyDataSet.setColor(lineColor,25);
            lineOfBestFitAccuracyDataSet.setDrawValues(false);
            lineOfBestFitAccuracyDataSet.setDrawCircles(false);

            dataSets.add(lineOfBestFitAccuracyDataSet);
        }
        // add data
        dataSets.add(lineLineOfBestFitDataSet);
        dataSets.add(lineDataSet);
        dataSets.add(lineLineOfBestFitRollingDataSet);


        LineData lineData = new LineData(dataSets);

        lineChart.setData(lineData);
        lineChart.invalidate();
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
