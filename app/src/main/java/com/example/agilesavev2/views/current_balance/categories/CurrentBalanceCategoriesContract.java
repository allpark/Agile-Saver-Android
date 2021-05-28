package com.example.agilesavev2.views.current_balance.categories;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.agilesavev2.models.transactions.Transaction;

import java.util.ArrayList;
import java.util.HashMap;

public interface CurrentBalanceCategoriesContract {
    interface categoriesView{
        void showPieChart(HashMap<String, Double> data);
        Context getContext();
        Intent getIntentData();
        void showCategories(HashMap<String, Double> data);
        void showAddCategories();
        int getSelectedAccountID();
        int getUserID();
        void goBack();
        String getType();


    }
    interface categoriesPresenter{
        void getTransactionData();
        void handleAddCategory(View view);
        void handleGoBack(View view);
    }
}
