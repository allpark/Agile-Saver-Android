package com.example.agilesavev2.views.subscriptions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.NavBarController.NavBarController;
import com.example.agilesavev2.databinding.ActivitySubscriptionsBinding;
import com.example.agilesavev2.fragments.pie_chart.PieChartFragment;
import com.example.agilesavev2.fragments.record.RecordCategoryFragment;
import com.example.agilesavev2.fragments.subscription.SubscriptionDialog;
import com.example.agilesavev2.fragments.subscription.SubscriptionFragment;
import com.example.agilesavev2.models.subscriptions.Subscription;
import com.example.agilesavev2.models.transactions.Transaction;
import com.example.agilesavev2.utils.PlusButtonAnimation;
import com.example.agilesavev2.views.subscriptions.add_subscription.AddSubscriptionActivity;
import com.example.agilesavev2.views.subscriptions.subscription_analysis.SubscriptionAnalysisActivity;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionsActivity extends AppCompatActivity implements SubscriptionsContract.IsubscriptionsView, SubscriptionDialog.DialogListener {
    private DrawerLayout drawer;
    private SubscriptionsPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ActivitySubscriptionsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_subscriptions);
        setTitle("");
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.draw_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_close,R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavBarController navBarController = new NavBarController(this,drawer,getIntent());

        //setting up presenter handle
        presenter=new SubscriptionsPresenter(this);
        binding.setPresenter(presenter);//binding the presenter to the XML layout
        presenter.getData();

        new PlusButtonAnimation((LinearLayout)findViewById(R.id.hint));

    }

    @Override
    public void showSubscription(List<Subscription> data) {
        for(Subscription subscription : data){
            RecordCategoryFragment recordCategoryFragment = new RecordCategoryFragment();
        }
    }

    @Override
    public void showPieChart(HashMap<String, Double> map) {
        TextView total_monthly_subscription = findViewById(R.id.subscriptions_total_value);
        String symbol = Currency.getInstance(getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();
        if(map.isEmpty()){
            total_monthly_subscription.setText(symbol+"0");
        }else{
            double total=0;
            for(Map.Entry<String, Double> entry : map.entrySet()){
                total+=entry.getValue();
            }
            total_monthly_subscription.setText(formatMoney(symbol,total));
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
    public void showBalance() {

    }

    @Override
    public void showAddSubscription() {
        getIntent().setClass(this, AddSubscriptionActivity.class);
        startActivityForResult(getIntent(),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            presenter.getData();
        }
    }

    @Override
    public void showAnalysis() {
        getIntent().setClass(this, SubscriptionAnalysisActivity.class);
        startActivity(getIntent());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public int getUserID() {
        return Integer.parseInt(getIntent().getStringExtra("ID"));
    }

    @Override
    public Intent getIntentData() {
        return getIntent();
    }

    @Override
    public void listSubscriptions(HashMap<String, Double> map, ArrayList<Subscription> list) {
        if(list.isEmpty()){
            ((Button) findViewById(R.id.analyse)).setVisibility(View.INVISIBLE);
            return;
        }

        LinearLayout linearLayout = findViewById(R.id.subscription_container);
        linearLayout.removeAllViews();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        HashMap<String, Integer> colorHashMap = getColourMap(map);

        for(Map.Entry<String,Double> entry : map.entrySet()){
            Subscription sub = getSubscription(list,entry.getKey());
            SubscriptionFragment subscription = new SubscriptionFragment(sub,colorHashMap.get(entry.getKey()));
            fragmentTransaction.add(linearLayout.getId(),subscription);
        }
        fragmentTransaction.commit();


    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //helper functions
    private String formatMoney(String currencySymbol,double val){
        if(val == (int)val){
            return currencySymbol+(int)val;
        }else{
            return currencySymbol+String.format("%.2f",val);
        }
    }

    Subscription getSubscription(ArrayList<Subscription> sublist,String sub){
        for(Subscription subscription : sublist){
            if(subscription.getName().equals(sub))return subscription;
        }
        return null;
    }


    private HashMap<String, Integer> getColourMap(HashMap<String, Double> map){
        ArrayList<Integer> colours = new ArrayList<>();
        for(int color: ColorTemplate.MATERIAL_COLORS) colours.add(color);
        for(int color: ColorTemplate.VORDIPLOM_COLORS)colours.add(color);
        HashMap<String, Integer> colourMap = new HashMap<>();

        for(Map.Entry<String, Double> entry : map.entrySet()){
            if(colourMap.get(entry.getKey())==null){
                colourMap.put(entry.getKey(),colours.get(0));
                colours.remove(0);
            }

        }
        return colourMap;
    }


    @Override
    public void applyChanges(int id, String name, double monthlyCost, String category) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", Integer.toString(id));
        map.put("userID", getIntent().getStringExtra("ID"));
        map.put("name", name);
        map.put("subscriptionValue", Double.toString(monthlyCost));
        map.put("category", category);
        presenter.handleUpdate(map);
    }

    @Override
    public void delete(int id, String subscriptionName) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", Integer.toString(id));
        map.put("userID", getIntent().getStringExtra("ID"));
        map.put("name", subscriptionName);
        presenter.handleDelete(map);
    }
}