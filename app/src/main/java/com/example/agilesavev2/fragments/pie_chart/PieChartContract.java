package com.example.agilesavev2.fragments.pie_chart;

import android.graphics.Color;

import java.util.HashMap;

public interface PieChartContract {
    interface PieChartView{
        void parseData(HashMap<String,Double> map);
        void drawPieChart();
        void setCentreValue(String string);
        void setCentreValueTop(String string);
        void setCentreValueBottom(String string);
        void setTagsVisible(Boolean bool);
        void setHoleRadius(float val);
        void setFontColor(int color);
        void setTextSize(float size);

    }
}
