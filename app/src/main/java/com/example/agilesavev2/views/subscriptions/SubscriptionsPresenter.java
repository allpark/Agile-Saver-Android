package com.example.agilesavev2.views.subscriptions;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.subscriptions.Subscription;
import com.example.agilesavev2.services.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubscriptionsPresenter implements SubscriptionsContract.IsubscriptionsPresenter, Presenter {
    SubscriptionsContract.IsubscriptionsView view;
    Services services;
    public SubscriptionsPresenter(SubscriptionsContract.IsubscriptionsView view) {
        this.view = view;
        services = new Services(this, this.view.getContext());
    }

    @Override
    public void getData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("userID",Integer.toString(this.view.getUserID()));
        services.getSubscriptions(map);
    }

    @Override
    public void handleAnalysis(View view) {
        this.view.showAnalysis();
    }

    @Override
    public void handleAddSubscription(View view) {
        this.view.showAddSubscription();
    }

    @Override
    public void getBalance(View view) {
        this.view.showBalance();
    }

    @Override
    public void handleUpdate(HashMap<String, String> map) {
        services.updateSubscription(map);
    }

    @Override
    public void handleDelete(HashMap<String, String> map) {
        services.deleteSubscription(map);
    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {
        if(code==5){
            ArrayList<Subscription> list = (ArrayList<Subscription>) data;
            HashMap<String, Double> pieDataSet = new HashMap<>();
            for(Subscription subscription : list) pieDataSet.put(subscription.getName(),subscription.getSubscriptionValue());
            view.showPieChart(pieDataSet);
            view.listSubscriptions(pieDataSet,list);
        }
        if(code==11){
            this.view.showMessage("Subscription has been updated");
            getData();
        }
        if(code==12){
            this.view.showMessage("Subscription has been deleted");
            getData();
        }


    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }


}
