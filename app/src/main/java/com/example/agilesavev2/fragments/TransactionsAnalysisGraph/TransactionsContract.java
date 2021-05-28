package com.example.agilesavev2.fragments.TransactionsAnalysisGraph;

import com.example.agilesavev2.models.transactions.TransactionSimple;

import java.util.ArrayList;
import java.util.HashMap;

public interface TransactionsContract {
    interface TransactionsView{
        void setupBarCharts();
        void getData();
        void drawBarCharts(HashMap<String, double[]> transactionMap);
        void drawBarChartLeft(HashMap<String, double[]> transactionMap);
        void drawBarChartRight(HashMap<String, double[]> transactionMap);
        void updateLabels( HashMap<String, double[]> transactionMap);
        String getID();
    }
    interface TransactionsPresenter{
        void getData();
        void handleOnReject();
        void parseBarChartData(ArrayList<TransactionSimple> data);
        HashMap<String, String> getBudgetPeriod();
        void handleDrawBarCharts(HashMap<String, double[]> transactionMap);

    }
}
