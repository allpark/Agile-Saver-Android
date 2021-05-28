package com.example.agilesavev2.fragments.TransactionsAnalysisGraph;

import android.content.Context;
import android.content.Intent;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.transactions.TransactionSimple;
import com.example.agilesavev2.services.Services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransactionsPresenter implements TransactionsContract.TransactionsPresenter, Presenter {
    private TransactionsContract.TransactionsView view;
    private Intent intent;
    private HashMap<String, Map<String,Double>> data;
    private Services services;

    public TransactionsPresenter(Intent intent, TransactionsContract.TransactionsView view, Context context) {
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
        services.getAllUserTransactionsBetweenDates(map, this);
    }


    @Override
    public void handleOnReject() {
    }

    @Override
    public void handleOnReject(int code) {

    }

    public void handleDrawBarCharts( HashMap<String, double[]> transactionMap ){
        view.updateLabels(transactionMap);
        view.drawBarCharts(transactionMap);
    }

    public void parseBarChartData(ArrayList<TransactionSimple> data){

        // here i am!
        HashMap<String, double[]> transactionMap = new HashMap<>();

        for (String day : new String[] {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"}){
            transactionMap.put(day, new double[]{0,0});

        }
        for (TransactionSimple t : data){

            if (t.getType().equals("EXPENSE")){
                LocalDate transactionDate = LocalDate.parse(t.getDate());
                String dayOfWeek = transactionDate.getDayOfWeek().toString();
                transactionMap.get(dayOfWeek)[0] = transactionMap.get(dayOfWeek)[0] += 1;
                transactionMap.get(dayOfWeek)[1] = transactionMap.get(dayOfWeek)[1] + t.getTransaction();
            }
        }

        System.out.println("SEAN IS WATCHING YOU!");

        // only draw bar charts if they're points data points
        if (data.size() > 0){
            handleDrawBarCharts(transactionMap);
        }
    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {

        if (code==1){
            ArrayList<TransactionSimple> transactionsData = (ArrayList<TransactionSimple>)data;
            parseBarChartData(transactionsData);
        }
        else{
        }
    }


}

