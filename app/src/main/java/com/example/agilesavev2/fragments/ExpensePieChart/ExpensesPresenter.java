package com.example.agilesavev2.fragments.ExpensePieChart;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.transactions.Spending;
import com.example.agilesavev2.models.transactions.Transaction;
import com.example.agilesavev2.models.users.User;
import com.example.agilesavev2.services.Services;
import com.example.agilesavev2.utils.CurrencyConvertion;
import com.example.agilesavev2.views.home.HomeContract;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExpensesPresenter implements ExpensesContract.ExpensesPresenter, Presenter {
    private ExpensesContract.ExpensesView view;
    private Intent intent;
    private HashMap<String, Map<String,Double>> data;
    private Services services;

    public ExpensesPresenter(Intent intent, ExpensesContract.ExpensesView view, Context context) {
        this.view = view;
        this.intent=intent;
        services = new Services(this, context);

    }

    @Override
    public void getExpenses() {
        data = new HashMap<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("userID",view.getID());
        map.put("accountID", view.getSelectedAccountID());
       services.getUserTransactionData(map,this);
    }

    public void getExpenses(String label) {
        view.showPieChart((HashMap<String, Double>) data.get(label));
    }

    @Override
    public void processExpenses(List<Transaction> spending) {
        for(Transaction spendingData : spending){
            if(spendingData.getType().equals("EXPENSE")) {
                String category = spendingData.getCategory();
                String name = spendingData.getName();
                double value = CurrencyConvertion.getConvertedCurrency(spendingData.getCurrency(), spendingData.getTransaction());
                if (data.get(category) == null) {
                    HashMap<String, Double> map = new HashMap<>();
                    map.put(name, value);
                    data.put(category, map);
                } else {
                    if (data.get(category).get(name) == null) {
                        data.get(category).put(name, value);
                    } else {
                        double nameValue = data.get(category).get(name);
                        data.get(category).put(name, nameValue + value);
                    }

                }
            }

        }

    }


    @Override
    public void inspectPieChart(View view) {
        View view2 =(View) view.getParent().getParent();
        Button back=(Button)view2.findViewById(R.id.goBack);
        back.setVisibility(View.VISIBLE);
        String val=this.view.getPieChartSelectedVal();
        getExpenses(this.view.getPieChartSelectedVal());
    }

    @Override
    public void handleBack(View view) {
        Button back=(Button)view;
        back.setVisibility(View.INVISIBLE);
        handleDisplayPieChart();
    }


    @Override
    public <T> void handleOnSuccess(int code, T data) {
        processExpenses((List<Transaction>)data);
        handleDisplayPieChart();


    }
    public void handleDisplayPieChart(){

        HashMap<String, Double> map = new HashMap<>();
        Iterator<Map.Entry<String, Map<String,Double>>> iterator = data.entrySet().iterator();
        HashMap<String,Double> spendingData = new HashMap<>();
        while(iterator.hasNext()){
            Map.Entry<String,Map<String,Double>> entry = iterator.next();
            double total=0;
            for(Map.Entry<String,Double> entryData : entry.getValue().entrySet()){
                total+=entryData.getValue();
            }
            spendingData.put(entry.getKey(),total);
            //System.out.println("TEST: "+entry);

        }
        System.out.println(spendingData);
        view.showPieChart(spendingData);

    }

    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }
}




//    @Override
//    public void getExpenses() {
//        Iterator<Map.Entry<String, Map<String,Integer>>> iterator = test.entrySet().iterator();
//        HashMap<String,Integer> data = new HashMap<>();
//        while(iterator.hasNext()){
//            Map.Entry<String,Map<String,Integer>> entry = iterator.next();
//            int total=0;
//            for(Map.Entry<String,Integer> entryData : entry.getValue().entrySet()){
//                total+=entryData.getValue();
//            }
//            data.put(entry.getKey(),total);
//            //System.out.println("TEST: "+entry);
//
//        }
//        view.showPieChart(data);
//    }