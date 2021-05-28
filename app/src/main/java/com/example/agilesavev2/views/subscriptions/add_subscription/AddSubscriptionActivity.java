package com.example.agilesavev2.views.subscriptions.add_subscription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivityAddSubscriptionBinding;
import com.example.agilesavev2.fragments.pie_chart.PieChartFragment;

import java.util.HashMap;
import java.util.Map;

public class AddSubscriptionActivity extends AppCompatActivity implements AddSubscriptionContract.IaddSubscriptionView {
    private AddSubscriptionPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().hide();
        ActivityAddSubscriptionBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_subscription);
        presenter = new AddSubscriptionPresenter(this);
        binding.setPresenter(presenter);
        presenter.getPieChartData();
    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "Subscription added!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onReject() {
        Toast.makeText(this, "This subscription was already added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPieChart(HashMap<String,Double> map) {
        TextView total_monthly_subscription = findViewById(R.id.subscriptions_total_value);
        if(map.isEmpty()){
            total_monthly_subscription.setText("£0");
        }else{
            double total=0;
            for(Map.Entry<String, Double> entry : map.entrySet()){
                total+=entry.getValue();
            }
            total_monthly_subscription.setText(formatMoney("£",total));
        }



        LinearLayout piechart_container = findViewById(R.id.piechart_container);
        piechart_container.removeAllViews();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        PieChartFragment pieChartFragment = new PieChartFragment();
        pieChartFragment.parseData(map);
        pieChartFragment.setTagsVisible(false);
        pieChartFragment.setHoleRadius(70);
        fragmentTransaction.add(piechart_container.getId(),pieChartFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void showInvalidNameInputMessage() {
        Toast.makeText(this, "Please enter a name for your subscription", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidPriceInputMessage() {
        Toast.makeText(this, "Please input monthly price for your subscription", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidCategoryInputMessage() {
        Toast.makeText(this, "Please select a category for your subscription", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getEnteredSubscriptionName() {
        EditText name = findViewById(R.id.name);
        return name.getText().toString();
    }

    @Override
    public String getEnteredMonthlyPrice() {
        EditText monthlyPrice = findViewById(R.id.monthly_price);
        return monthlyPrice.getText().toString();
    }

    @Override
    public String getSelectedCategory() {
        Spinner category = findViewById(R.id.subscription_category);
        return category.getSelectedItem().toString();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Intent getIntentData() {
        return getIntent();
    }

    @Override
    public int getUserID() {
        return Integer.parseInt(getIntent().getStringExtra("ID"));
    }


    //helper functions
    private String formatMoney(String currencySymbol,double val){
        if(val == (int)val){
            return currencySymbol+(int)val;
        }else{
            return currencySymbol+String.format("%.2f",val);
        }
    }
}