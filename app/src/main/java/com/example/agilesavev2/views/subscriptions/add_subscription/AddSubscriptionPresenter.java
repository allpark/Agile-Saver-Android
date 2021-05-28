package com.example.agilesavev2.views.subscriptions.add_subscription;

import android.view.View;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.subscriptions.Subscription;
import com.example.agilesavev2.models.transactions.Transaction;
import com.example.agilesavev2.services.Services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class AddSubscriptionPresenter implements AddSubscriptionContract.IaddSubscriptionPresenter, Presenter {
    private AddSubscriptionContract.IaddSubscriptionView view;
    private Services services;
    public AddSubscriptionPresenter(AddSubscriptionContract.IaddSubscriptionView view) {
        this.view = view;
        services = new Services(this,this.view.getContext());
    }

    @Override
    public void handleOnSubmit(View view) {
        if(this.view.getEnteredSubscriptionName().trim().equals("")){
            this.view.showInvalidNameInputMessage();
            return;
        }
        if(this.view.getEnteredMonthlyPrice().trim().equals("")){
            this.view.showInvalidPriceInputMessage();
            return;
        }
        if(this.view.getSelectedCategory().equals("Please select a category")){
            this.view.showInvalidCategoryInputMessage();
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("userID",Integer.toString(this.view.getUserID()));
        map.put("name", this.view.getEnteredSubscriptionName());
        map.put("category",this.view.getSelectedCategory());
        map.put("started", LocalDateTime.now().toString());
        map.put("subscriptionValue",this.view.getEnteredMonthlyPrice());
        if(services.isSubscriptionExist(map)){
            this.view.onReject();
            return;
        }
        services.addSubscription(map);
        services.sendSubscription(map);
        this.view.onSuccess();
    }

    @Override
    public void getPieChartData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("userID",Integer.toString(this.view.getUserID()));
        services.getSubscriptions(map);
    }

    @Override
    public void handleBack(View view) {
        this.view.goBack();
    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {
        if(code==17){
            getPieChartData();
            return;
        }
        List<Subscription> list = (List<Subscription>) data;
        HashMap<String, Double> pieDataSet = new HashMap<>();
        for(Subscription subscription : list) pieDataSet.put(subscription.getName(),subscription.getSubscriptionValue());
        view.showPieChart(pieDataSet);
    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }

}
