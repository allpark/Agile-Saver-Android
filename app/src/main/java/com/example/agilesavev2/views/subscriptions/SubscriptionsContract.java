package com.example.agilesavev2.views.subscriptions;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.agilesavev2.models.subscriptions.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface SubscriptionsContract {
    interface IsubscriptionsView{
        void showSubscription(List<Subscription> data);
        void showPieChart(HashMap<String, Double> map);
        void showBalance();
        void showAddSubscription();
        void showAnalysis();
        Context getContext();
        int getUserID();
        Intent getIntentData();
        void listSubscriptions(HashMap<String, Double> map, ArrayList<Subscription> list);
        void showMessage(String message);


    }

    interface IsubscriptionsPresenter{
        void getData();
        void handleAnalysis(View view);
        void handleAddSubscription(View view);
        void getBalance(View view);
        void handleUpdate(HashMap<String, String> map);
        void handleDelete(HashMap<String, String> map);
    }

}
