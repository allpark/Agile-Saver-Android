package com.example.agilesavev2.fragments.BudgetLineGraph;

import com.example.agilesavev2.models.ai.LineGraphData;
import com.example.agilesavev2.models.ai.Point;

import java.util.HashMap;
import java.util.List;

public interface BudgetContract {
    interface BudgetView{
        void setupLineChart();
        void getData();
        void drawLineGraphData(List<Point>dataPoints, List<Point>lineOfBestFit, float daysUntilBudgetRunsOut,  float gradient, float yintercept, boolean budgetRanOut, float domainXMax, float domainYMax);
        void updateLabels(float daysUntilBudgetRunsOut, float predictedBudgetBeforePayday);

        String getID();
    }
    interface BudgetPresenter{
        void getData();
        void parseData();
        void handleOnReject();
        void parseLineGraphData(LineGraphData data);
        HashMap<String, String> getBudgetPeriod();
        void handleDrawLineGraph(List<Point>dataPoints, List<Point>lineOfBestFit, float daysUntilBudgetRunsOut, float predictedBudgetBeforePayday,  float gradient, float yintercept, float domainXMax, float domainYMax);

    }
}
