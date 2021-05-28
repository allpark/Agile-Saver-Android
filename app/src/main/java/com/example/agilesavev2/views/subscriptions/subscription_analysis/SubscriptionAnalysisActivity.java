package com.example.agilesavev2.views.subscriptions.subscription_analysis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivitySubscriptionAnalysisBinding;
import com.example.agilesavev2.fragments.pie_chart.PieChartFragment;
import com.example.agilesavev2.fragments.subscription.SubscriptionInfoOneFragment;
import com.example.agilesavev2.fragments.subscription.SubscriptionInfoTwoFragment;
import com.example.agilesavev2.models.subscriptions.Subscription;
import com.example.agilesavev2.views.subscriptions.SubscriptionsPresenter;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionAnalysisActivity extends AppCompatActivity implements SubscriptionAnalysisContract.SubscriptionAnalysisView {
    private SubscriptionsAnalysisPresenter presenter;
    private SubscriptionInfoOneFragment subscriptionInfoOneFragment;
    private SubscriptionInfoTwoFragment subscriptionInfoTwoFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().hide();
        ActivitySubscriptionAnalysisBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_subscription_analysis);
        presenter = new SubscriptionsAnalysisPresenter(this);
        binding.setPresenter(presenter);
        this.presenter.getAnalysis();
    }

    @Override
    public void showNext() {

    }

    @Override
    public void showSelectedCategory() {

    }

    @Override
    public void showPieChart(HashMap<String, Double> map, ArrayList<String> list) {
        LinearLayout piechart_container = findViewById(R.id.piechart_container);
        piechart_container.removeAllViews();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        PieChartFragment pieChartFragment = new PieChartFragment();
        pieChartFragment.parseData(map);
        pieChartFragment.setTagsVisible(false);
        pieChartFragment.setHoleRadius(70);


        pieChartFragment.setCustomColour(highlightSegment(map,list));
        fragmentTransaction.add(piechart_container.getId(),pieChartFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void showAnalysis() {
        LinearLayout linearLayout = findViewById(R.id.subscription_container);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        subscriptionInfoOneFragment = new SubscriptionInfoOneFragment();
        transaction.add(linearLayout.getId(),subscriptionInfoOneFragment);
        transaction.commitNow();
    }

    @Override
    public void setTopAnalysisPart(String category, Double percentage, ArrayList<String> selected) {
        subscriptionInfoOneFragment.setCategory(category);
        subscriptionInfoOneFragment.setPercentage(percentage);
        subscriptionInfoOneFragment.setList(selected);
    }

    @Override
    public void setSecondAnalysisPart(String info1, String info2, ArrayList<Double> data) {
        LinearLayout linearLayout = findViewById(R.id.subscription_container2);
        linearLayout.removeAllViews();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        subscriptionInfoTwoFragment = new SubscriptionInfoTwoFragment(info1,info2, data);
        transaction.add(linearLayout.getId(),subscriptionInfoTwoFragment);
        transaction.commitNow();
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void goBack() {
        finish();
    }

    @Override
    public String getUserID() {
        return getIntent().getStringExtra("ID");
    }

    @Override
    public void setSaveValue(double saveValue) {
        TextView total_monthly_subscription = findViewById(R.id.subscriptions_total_value);
        String symbol = Currency.getInstance(getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();
        total_monthly_subscription.setText(formatMoney(symbol,saveValue));
    }

    //helper functions
    private String formatMoney(String currencySymbol,double val){
        if(val == (int)val){
            return currencySymbol+(int)val;
        }else{
            return currencySymbol+String.format("%.2f",val);
        }
    }

    @Override
    public ArrayList<Integer> highlightSegment(HashMap<String, Double> map, ArrayList<String> list){
        ArrayList<Integer> colours = new ArrayList<>();
        if(map!=null){
            for(Map.Entry<String, Double> entry : map.entrySet()){
                if(list.contains(entry.getKey())){
                    colours.add(Color.RED);
                }else{
                    colours.add(Color.BLACK);
                }
            }
        }
        return colours;
    }

    @Override
    public String getSymbol() {
        return Currency.getInstance(getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();
    }

    @Override
    public ImageView getBackButton() {
        return findViewById(R.id.goBack);
    }

    @Override
    public ImageView getNextButton() {
        return findViewById(R.id.next);
    }

}