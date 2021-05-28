package com.example.agilesavev2.views.home;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.Fragment;
import com.example.agilesavev2.assets.NavBarController.NavBarController;
import com.example.agilesavev2.databinding.ActivityHomeBinding;
import com.example.agilesavev2.fragments.accounts.BalanceListFragment;
import com.example.agilesavev2.fragments.accounts.UpdateAccountBalanceDialog;
import com.example.agilesavev2.fragments.pie_chart.PieChartFragment;
import com.example.agilesavev2.utils.PlusButtonAnimation;
import com.example.agilesavev2.views.addBudget.AddBudgetActivity;
import com.example.agilesavev2.views.calculator.CalculatorMainActivity;
import com.example.agilesavev2.views.home.home_assets.AdjustBalanceActivity;
import com.example.agilesavev2.views.my_budget.MyBudgetActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//Muneeb
public class HomeActivity extends AppCompatActivity implements HomeContract.iHomeView , com.example.agilesavev2.assets.View, UpdateAccountBalanceDialog.DialogListener {
    private DrawerLayout drawer;
    private HomePresenter presenter;
    private String selectedValue;
    private PieChartFragment pieChartFragment;
    private ArrayList<Fragment> fragments;
    private boolean hideTopGoals = false, hideTransactionList=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setTitle("");
        ActivityHomeBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.draw_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_close,R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavBarController navBarController = new NavBarController(this,drawer,getIntent());
        presenter = new HomePresenter(getIntent(),this);
        binding.setPresenter(presenter);
        presenter.getBalance();

        if(hideTopGoals){
            LinearLayout linearLayout = findViewById(R.id.top_goal_container);
            linearLayout.removeAllViews();
        }
        if(hideTransactionList){
            LinearLayout linearLayout = findViewById(R.id.transactions_container);
            linearLayout.removeAllViews();
        }

        new PlusButtonAnimation((LinearLayout)findViewById(R.id.hint));






    }




    public void showPieChart(HashMap<String, Integer>  data){
        //get pie chart
        PieChart pieChart = findViewById(R.id.any_chart_view);
        pieChart.getDescription().setEnabled(false);
        pieChart.setHoleColor(140070);
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.getLegend().setTextColor(Color.WHITE);


        pieChart .getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        pieChart .getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        pieChart .getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);

        //entries
        ArrayList<PieEntry> entries = new ArrayList<>();

        if(data==null || data.isEmpty()){//show an empty pie chart is data given is empty
            entries.add(new PieEntry(0,""));

            return;
        }else {

            int totalExpense = 0;
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                entries.add(new PieEntry(entry.getValue(), entry.getKey()));
                totalExpense += entry.getValue();
            }
            pieChart.setCenterText("£"+totalExpense);
        }



        //get colours
        ArrayList<Integer> colours = new ArrayList<>();
        for(int color: ColorTemplate.MATERIAL_COLORS) colours.add(color);
        for(int color: ColorTemplate.VORDIPLOM_COLORS)colours.add(color);

        //dataset
        PieDataSet dataSet = new PieDataSet(entries,"");
        dataSet.setColors(colours);
        PieData pieData= new PieData(dataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);



        pieChart.setData(pieData);
        pieChart.invalidate();

        pieChart.setDrawEntryLabels(false);
        pieChart.getData().setDrawValues(false);

        Button button = findViewById(R.id.goDeeper);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry pe = (PieEntry) e;
                button.setVisibility(View.VISIBLE);
                selectedValue=(pe.getLabel());
            }

            @Override
            public void onNothingSelected() {
                button.setVisibility(View.INVISIBLE);
                selectedValue="";
            }
        });



    }

    @Override
    public String getPieChartSelectedVal() {
        return selectedValue;
    }

    @Override
    public void setPieChartFragment(PieChartFragment fragment) {

    }

    @Override
    public void hideTopGoal() {
        hideTopGoals=true;
        LinearLayout linearLayout = findViewById(R.id.top_goal_container);
        if(linearLayout!=null){
            linearLayout.removeAllViews();
        }
    }

    @Override
    public void hideTransactionList() {
        hideTransactionList = true;
        LinearLayout linearLayout = findViewById(R.id.transactions_container);
        if(linearLayout!=null){
            linearLayout.removeAllViews();
        }

    }


    @Override
    public void showMyBudget() {
        Intent intent = new Intent(this, MyBudgetActivity.class);
        intent.putExtra("ID",getIntent().getStringExtra("ID"));
        intent.putExtra("NAME",getIntent().getStringExtra("NAME"));
        intent.putExtra("EMAIL",getIntent().getStringExtra("EMAIL"));
        startActivity(intent);
    }

    @Override
    public void displayExpenses() {

    }

    @Override
    public void displayTopGoal() {

    }

    @Override
    public void displayActivity() {

    }

    @Override
    public String getBalance() {
        TextView balance = findViewById(R.id.Balance);
        return balance.getText().toString();
    }


    @Override
    public void displayBalance(String balance) {
        TextView textView = findViewById(R.id.Balance);
        textView.setText("£"+balance);
    }


    @Override
    public void displayAdjustBalance() {
        Intent adjustBalanceIntent= new Intent(this, AdjustBalanceActivity.class);
        adjustBalanceIntent.putExtra("ID",getIntent().getStringExtra("ID"));
        //startActivity(adjustBalanceIntent);
        startActivityForResult(adjustBalanceIntent,1);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                presenter.getBalance();
            }
        }
        if(requestCode==2){
            if(resultCode == RESULT_OK) {
                restartUI();

            }
        }
    }

    @Override
    public void showAddNewRecord() {
        getIntent().setClass(this, CalculatorMainActivity.class);
        startActivityForResult(getIntent(),2);

    }

    @Override
    public void restartUI() {
        overridePendingTransition(0, 0);
        finish();
        getIntent().setClass(this, HomeActivity.class);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public void refreshFragments() {
        for(Fragment fragment : fragments){
            fragment.refreshFragment();
        }

    }

    @Override
    public void setAdapter(Fragment fragment) {
        if(fragments==null){
            fragments = new ArrayList<>();
        }
        fragments.add(fragment);
    }

    @Override
    public void applyChanges(int id, String accountName, double newBalance) {
        HashMap<String,String> map = new HashMap<>();
        map.put("userID", getIntent().getStringExtra("ID"));
        map.put("name", accountName);
        map.put("balance", Double.toString(newBalance));
        map.put("id", Integer.toString(id));
        presenter.updateAccountbalance(map);

    }

    @Override
    public void delete(int id, String accountName) {
        HashMap<String,String> map = new HashMap<>();
        map.put("userID", getIntent().getStringExtra("ID"));
        map.put("name", accountName);
        map.put("id", Integer.toString(id));
        presenter.deleteAccount(map);
    }





}


