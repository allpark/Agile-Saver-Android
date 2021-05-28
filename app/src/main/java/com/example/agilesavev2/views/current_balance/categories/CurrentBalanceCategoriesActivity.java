package com.example.agilesavev2.views.current_balance.categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.databinding.ActivityCurrentBalanceCategoriesBinding;
import com.example.agilesavev2.fragments.pie_chart.PieChartFragment;
import com.example.agilesavev2.fragments.record.RecordCategoryFragment;
import com.example.agilesavev2.models.transactions.Transaction;
import com.example.agilesavev2.utils.PlusButtonAnimation;
import com.example.agilesavev2.views.settings.add_category.AddCategoryActivity;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class CurrentBalanceCategoriesActivity extends AppCompatActivity implements CurrentBalanceCategoriesContract.categoriesView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCurrentBalanceCategoriesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_current_balance_categories);
        CurrentBalanceCategoriesPresenter presenter = new CurrentBalanceCategoriesPresenter(this);
        binding.setPresenter(presenter);
        getSupportActionBar().hide();

        presenter.getTransactionData();
        new PlusButtonAnimation((LinearLayout)findViewById(R.id.hint));

    }

    @Override
    public void showPieChart(HashMap<String, Double> data) {
        double spending =0;
        for(Map.Entry<String, Double> entry : data.entrySet()) spending+=entry.getValue();
        LinearLayout linearLayout = findViewById(R.id.piechart_container);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        PieChartFragment pieChartFragment = new PieChartFragment();
        pieChartFragment.parseData(data);
        pieChartFragment.setTagsVisible(false);
        TextView centretext = findViewById(R.id.transaction_value);

        String symbol = Currency.getInstance(getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();
        centretext.setText(symbol+spending);
        pieChartFragment.setFontColor(Color.WHITE);
        pieChartFragment.setHoleRadius(70);

        transaction.add(linearLayout.getId(),pieChartFragment);
        transaction.commitNow();
    }


    @Override
    public void showCategories(HashMap<String, Double> data) {



        LinearLayout linearLayout = findViewById(R.id.transactions_container);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ArrayList<Integer> colours = new ArrayList<>();
        for(int color: ColorTemplate.MATERIAL_COLORS) colours.add(color);
        for(int color: ColorTemplate.VORDIPLOM_COLORS)colours.add(color);
        HashMap<String, Integer> colourMap = new HashMap<>();
        for(Map.Entry<String, Double> entry : data.entrySet()){
            colourMap.put(entry.getKey(),colours.get(0));
            colours.remove(0);

            RecordCategoryFragment recordCategoryFragment = new RecordCategoryFragment(entry.getKey(),entry.getValue(), colourMap.get(entry.getKey()), getType());
            fragmentTransaction.add(linearLayout.getId(),recordCategoryFragment);

        }
        fragmentTransaction.commitNow();


    }

    @Override
    public void showAddCategories() {
        getIntent().setClass(this, AddCategoryActivity.class);
        startActivity(getIntent());
    }

    @Override
    public int getSelectedAccountID() {
        //retrieves hashmap of accounts
        HashMap<String, Integer> map = (HashMap<String, Integer>) getIntent().getSerializableExtra("ACCOUNTS");
        //retrieves the selected account name
        String selectedAccount = getIntent().getStringExtra("SELECTED");

        //Retrieves the accountID from the specified selected account name
        return map.get(selectedAccount);
    }

    @Override
    public int getUserID() {
        return Integer.parseInt(getIntent().getStringExtra("ID"));
    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public String getType() {
        return getIntent().getStringExtra("TRANSACTION_TYPE");
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Intent getIntentData(){
        return getIntent();
    }
}