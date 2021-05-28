package com.example.agilesavev2.views.current_balance;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Spinner;

import com.example.agilesavev2.models.transactions.Transaction;

import java.util.ArrayList;
import java.util.HashMap;

public interface CurrentBalanceContract {
    interface CurrentBalanceView{
        void showPieChart(HashMap<String, Double> data);
        void showRemainingBalance(String currencySymbol, Double balance);
        void showCategories();
        void showAccountMenu(ArrayList<String> accounts);
        void showTransactions(ArrayList<Transaction> data);
        int getSelectedAccountID();
        int getUserID();
        Context getContext();
        Intent getIntentData();
        String getSelectedType();
        void showPayDay(String dateTime, String type);
        void hidePayDayCounter();

    }

    interface CurrentBalancePresenter{
        void getCurrentBalance();
        void handleCategories(View view);
        void getTransactionData();
        void handleAccountSelection(View view);
        void handleSelectType(Spinner spinner);
        void getPayDate();
        void updatePayDayDate(String date);

    }
}
