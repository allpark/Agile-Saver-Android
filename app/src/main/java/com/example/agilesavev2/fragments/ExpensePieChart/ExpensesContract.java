package com.example.agilesavev2.fragments.ExpensePieChart;

import android.view.View;

import com.example.agilesavev2.models.transactions.Spending;
import com.example.agilesavev2.models.transactions.Transaction;

import java.util.HashMap;
import java.util.List;

public interface ExpensesContract {
    interface ExpensesView{
        void showPieChart(HashMap<String, Double> data);
        String getPieChartSelectedVal();
        String getID();
        String getSelectedAccountID();
    }
    interface ExpensesPresenter{
        void inspectPieChart(View view);
        void handleBack(View view);
        void getExpenses();
        void getExpenses(String label);
        void processExpenses(List<Transaction> spending);
    }
}
