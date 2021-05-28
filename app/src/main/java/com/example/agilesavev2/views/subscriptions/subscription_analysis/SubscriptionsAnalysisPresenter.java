package com.example.agilesavev2.views.subscriptions.subscription_analysis;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.subscriptions.Subscription;
import com.example.agilesavev2.services.Services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class SubscriptionsAnalysisPresenter implements SubscriptionAnalysisContract.SubscriptionAnalysisPresenter, Presenter {
    private SubscriptionAnalysisContract.SubscriptionAnalysisView view;
    private Services services;
    private ArrayList<String> sub_list;
    private int index;
    private HashMap<String, Double> pieDataSet, avg;
    private List<Subscription> list;
    private ImageView backButton, nextButton;

    public SubscriptionsAnalysisPresenter(SubscriptionAnalysisContract.SubscriptionAnalysisView view) {
        this.view = view;
        services = new Services(this, view.getContext());
    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {
        backButton =  this.view.getBackButton();
        nextButton =  this.view.getNextButton();
        if(code==5){
            list = (List<Subscription>) data;
            pieDataSet = new HashMap<>();
            avg = services.getSubscriptionAvgValues();
            for(Subscription subscription : list) pieDataSet.put(subscription.getName(),subscription.getSubscriptionValue());
            sub_list = new ArrayList<>();
//            for(Map.Entry<String,Double> entry : list.entrySet()){
//                sub_list.add(entry.getKey());
//            }
            for(Subscription subscription : list){
                if(!sub_list.contains(subscription.getCategory())) {
                    sub_list.add(subscription.getCategory());
                }
            }
            index=0;
            if(list.size()<=1){
                backButton.setVisibility(View.INVISIBLE);
                nextButton.setVisibility(View.INVISIBLE);
            }

            this.view.showAnalysis();
            handleProcessAnalysis(sub_list.get(index));

        }
    }
    public void handleProcessAnalysis(String category){
        ArrayList<Subscription> selected = new ArrayList<>();
        double selectedTotal = 0;
        double total =0;
        double percentage = 0;
        for(Map.Entry<String,Double> entry : pieDataSet.entrySet()){
            for (Subscription subscription : list){
                if(subscription.getName().equals(entry.getKey()) &&
                        subscription.getCategory().equals(category)){
                    selected.add(subscription);
                    selectedTotal+=(subscription.getSubscriptionValue());
                }else{
                }
            }
            total+= entry.getValue();

        }
        ArrayList<String> subNames = new ArrayList<>();
        percentage = selectedTotal * (100/total);
        for(Subscription subscription : selected) subNames.add(subscription.getName());
        this.view.setTopAnalysisPart(category, percentage,subNames);

        //cases
        double save=0.0;
        try {
            if(selected.size()==1){
                ArrayList<Double> dataSet = new ArrayList<>();

                dataSet.add(avg.get(selected.get(0).getCategory()));
                dataSet.add(selected.get(0).getSubscriptionValue());


                double data = selected.get(0).getSubscriptionValue()*12;
                double avgData = avg.get(selected.get(0).getCategory())*12;


                if(avgData < data){
                    save = Math.round(data - avgData);
                    String info1 = "By finding alternatives to bring down your "+category+" subscription";
                    String info2 = "You could save up to "+this.view.getSymbol()+save+".";
                    this.view.setSecondAnalysisPart(info1,info2, dataSet);
                }
                if(avgData == data){
                    String info1 = "You already seem to have the best subscription";
                    String info2 = "";
                    this.view.setSecondAnalysisPart(info1,info2, dataSet);
                }

                if(avgData > data){
                    save = Math.round(avgData - data);
                    String info1 = "You already seem to have the best subscription";
                    String info2 = "You are saving "+view.getSymbol()+save+" per year.";
                    this.view.setSecondAnalysisPart(info1,info2, dataSet);
                }
            }else if(selected.size()>1){
                ArrayList<Double> dataSet = new ArrayList<>();

                selected.sort(new Comparator<Subscription>() {
                    @Override
                    public int compare(Subscription o1, Subscription o2) {
                        return (int)o2.getSubscriptionValue() - (int)o1.getSubscriptionValue();
                    }
                });
                double totalVal = 0;
                for(int i=0; i< selected.size(); i++){
                    save+= Math.round(selected.get(i).getSubscriptionValue()*12);
                }
                dataSet.add(avg.get(selected.get(0).getCategory()));
                dataSet.add(selectedTotal);

                String info1 = selected.size()+" of your monthly subscriptions fall under the "+category+" category";
                String info2 = "You could save up to "+view.getSymbol()+save+" a year by cancelling your "+(selected.size()-1)+" most expensive subscriptions";
                this.view.setSecondAnalysisPart(info1,info2, dataSet);

            }
            this.view.setSaveValue(save);

        }catch (Exception e){
            System.out.println("SOMETHING WENT WRONG "+e.getMessage());
        }









        view.showPieChart(pieDataSet, subNames);
    }




    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }

    @Override
    public void handleMoveToNext(View view) {

        if(index<sub_list.size()-1){
            handleProcessAnalysis(sub_list.get(++index));
        }

        if(index==sub_list.size()-1){
            nextButton.setVisibility(View.INVISIBLE);
        }

        if(index!=0){
            backButton.setVisibility(View.VISIBLE);
        }else{
            backButton.setVisibility(View.INVISIBLE);
        }



    }

    @Override
    public void handleMoveBack(View view) {
        if(index>0){
            handleProcessAnalysis(sub_list.get(--index));
        }
        if(index==0){
            backButton.setVisibility(View.INVISIBLE);
        }

        if(index!=sub_list.size()-1){
            nextButton.setVisibility(View.VISIBLE);
        }else{
            nextButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void getAnalysis() {
        HashMap<String, String> map = new HashMap<>();
        map.put("userID",this.view.getUserID());
        services.getSubscriptions(map);
    }

    @Override
    public void handleBack(View view) {
        this.view.goBack();
    }
}
