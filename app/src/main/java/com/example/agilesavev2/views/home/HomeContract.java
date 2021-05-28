package com.example.agilesavev2.views.home;

import android.content.Intent;
import android.view.View;

import com.example.agilesavev2.fragments.pie_chart.PieChartFragment;
import com.example.agilesavev2.models.users.User;

import java.util.HashMap;

public interface HomeContract {
    interface iHomeView{
        void showMyBudget();
        void displayExpenses();
        void displayTopGoal();
        void displayActivity();
        void restartUI();
        String getBalance();
        void displayBalance(String balance);
        void displayAdjustBalance();
        void showAddNewRecord();
        void showPieChart(HashMap<String, Integer> data);
        String getPieChartSelectedVal();
        void setPieChartFragment(PieChartFragment fragment);
        void hideTopGoal();
        void hideTransactionList();
    }
    interface iHomePresenter{
        void getTopGoal();
        void getActivity();
        void getBalance();
        void handleAddRecord(View view);
        void updateAccountbalance(HashMap<String, String> map);
        void deleteAccount(HashMap<String, String> map);



    }
}
