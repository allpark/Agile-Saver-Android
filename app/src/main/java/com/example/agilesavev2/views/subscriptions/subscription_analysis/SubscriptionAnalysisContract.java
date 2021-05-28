package com.example.agilesavev2.views.subscriptions.subscription_analysis;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

public interface SubscriptionAnalysisContract {
    interface SubscriptionAnalysisView{
        void showNext();
        void showSelectedCategory();
        void showPieChart(HashMap<String, Double> map, ArrayList<String> list);
        void showAnalysis();
        void setTopAnalysisPart(String category, Double percentage, ArrayList<String> selected);
        void setSecondAnalysisPart(String info1, String info2, ArrayList<Double> data);
        Context getContext();
        void goBack();
        String getUserID();
        void setSaveValue(double saveValue);
        ArrayList<Integer> highlightSegment(HashMap<String, Double> map, ArrayList<String> list);
        String getSymbol();
        ImageView getBackButton();
        ImageView getNextButton();
    }
    interface SubscriptionAnalysisPresenter{
        void handleMoveToNext(View view);
        void handleMoveBack(View view);
        void getAnalysis();
        void handleBack(View view);
    }
}
