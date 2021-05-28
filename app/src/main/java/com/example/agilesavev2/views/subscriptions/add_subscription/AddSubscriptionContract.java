package com.example.agilesavev2.views.subscriptions.add_subscription;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.util.HashMap;

public interface AddSubscriptionContract {
    interface IaddSubscriptionView{
        void goBack();
        void onSuccess();
        void onReject();
        void showPieChart(HashMap<String,Double> data);
        void showInvalidNameInputMessage();
        void showInvalidPriceInputMessage();
        void showInvalidCategoryInputMessage();
        String getEnteredSubscriptionName();
        String getEnteredMonthlyPrice();
        String getSelectedCategory();
        Context getContext();
        Intent getIntentData();
        int getUserID();
    }
    interface IaddSubscriptionPresenter{
        void handleOnSubmit(View view);
        void getPieChartData();
        void handleBack(View view);
    }

}
