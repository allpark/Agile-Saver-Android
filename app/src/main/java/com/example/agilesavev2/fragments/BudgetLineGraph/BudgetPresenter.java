package com.example.agilesavev2.fragments.BudgetLineGraph;

import android.content.Context;
import android.content.Intent;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.ai.LineGraphData;
import com.example.agilesavev2.models.ai.Point;
import com.example.agilesavev2.services.Services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetPresenter implements BudgetContract.BudgetPresenter, Presenter {
    private BudgetContract.BudgetView view;
    private Intent intent;
    private HashMap<String, Map<String,Double>> data;
    private Services services;

    public BudgetPresenter(Intent intent,BudgetContract.BudgetView view, Context context) {
        this.view = view;
        this.intent=intent;
        services = new Services(this, context);
    }


    public HashMap<String, String>  getBudgetPeriod(){

        HashMap<String, String> map = new HashMap<>();

        // ================= IMPORTANT ==========================
        // retrieve payday default start date / end date
        LocalDate monthlyPayDayStart = LocalDate.now().minusDays(LocalDate.now().getDayOfMonth()-1);
        LocalDate monthlyPayDayEnd   = monthlyPayDayStart.plusMonths(1);
        // =======================================================

        int periodDays  = (int) ChronoUnit.DAYS.between(monthlyPayDayStart, monthlyPayDayEnd);

        // convert them to string
        String startDate = monthlyPayDayStart.format(DateTimeFormatter.ofPattern("MM.dd.yyyy"));
        String endDate = monthlyPayDayEnd.format(DateTimeFormatter.ofPattern("MM.dd.yyyy"));

        // add to map
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("period",  Integer.toString(periodDays));

        return map;
    }
    @Override
    public void getData() {
        HashMap<String, String> map = getBudgetPeriod();
        map.put("userID", intent.getStringExtra("ID"));
        services.getAILineChartData(map, this);
    }

    @Override
    public void parseData() {
        //iew.displayLineChart(data);//datasets retrieved from service and sends to view section
    }


    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }

    public void handleDrawLineGraph(List<Point>dataPoints, List<Point>lineOfBestFit, float daysUntilBudgetRunsOut, float predictedBudgetBeforePayday, float gradient, float yintercept,  float domainXMax, float domainYMax){
        view.updateLabels(daysUntilBudgetRunsOut,predictedBudgetBeforePayday);
        view.drawLineGraphData(dataPoints, lineOfBestFit, daysUntilBudgetRunsOut, gradient, yintercept, predictedBudgetBeforePayday < 0, domainXMax, domainYMax);
    }

    public void parseLineGraphData(LineGraphData data){

        List<Point> lineOfBestFit = new ArrayList<Point>();

        int lastBudgetEntry = (int)data.getLinePoints().get(data.getLinePoints().size()-1).getX();
        int daysTillBudgetRunsOut = Math.max( (int)(-data.getComputedYIntercept()/data.getComputedGradient()), 0) - lastBudgetEntry ;

        float predictedBudgetPayDay = data.getLineOfBestFitEnd().getY();

        lineOfBestFit.add(0,data.getLineOfBestFitStart());
        lineOfBestFit.add(1,data.getLineOfBestFitEnd());


        handleDrawLineGraph(
                data.getLinePoints(),
                lineOfBestFit,
                daysTillBudgetRunsOut,
                predictedBudgetPayDay,
                data.getComputedGradient(),
                data.getComputedYIntercept(),
                data.getDomainXMax(),
                data.getDomainYMax()
        );

    }
    @Override
    public <T> void handleOnSuccess(int code, T data) {

        if (code==1){
            ArrayList<LineGraphData> graphDataArray = (ArrayList<LineGraphData>)data;
            parseLineGraphData(graphDataArray.get(0));
        }
        else{

            System.out.println("err..." + code);
        }
    }


}

