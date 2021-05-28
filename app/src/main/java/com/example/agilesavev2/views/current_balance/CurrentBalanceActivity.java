package com.example.agilesavev2.views.current_balance;

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
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.NavBarController.NavBarController;
import com.example.agilesavev2.databinding.ActivityCurrentBalanceBinding;
import com.example.agilesavev2.fragments.pie_chart.PieChartFragment;
import com.example.agilesavev2.fragments.record.RecordFragment;
import com.example.agilesavev2.models.transactions.Transaction;
import com.example.agilesavev2.views.current_balance.categories.CurrentBalanceCategoriesActivity;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

//Syedur
public class CurrentBalanceActivity extends AppCompatActivity implements CurrentBalanceContract.CurrentBalanceView{
    private DrawerLayout drawer;
    private CurrentBalancePresenter presenter;
    private Double balance;
    private Spinner typeSelection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_current_balance);
        ActivityCurrentBalanceBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_current_balance);
        presenter = new CurrentBalancePresenter(this);
        binding.setPresenter(presenter);

        setTitle("");
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.draw_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_close,R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavBarController navBarController = new NavBarController(this,drawer,getIntent());

        presenter.getCurrentBalance();//here we use the presenter handle to retrieve the current balance

        TextView selectedAccount = findViewById(R.id.selectedAccount);
        selectedAccount.setText(getIntent().getStringExtra("SELECTED"));

        presenter.getTransactionData();

        typeSelection = findViewById(R.id.selectedType);
        ArrayList<String> types = new ArrayList<>();
        types.add("EXPENSE");
        types.add("INCOME");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>  (this,R.layout.spinner_item, types);
        typeSelection.setAdapter(dataAdapter);
        presenter.handleSelectType(typeSelection);
        this.presenter.getPayDate();





    }

    @Override
    public void showPieChart(HashMap<String, Double> data) {
        double spending =0;
        for(Map.Entry<String, Double> entry : data.entrySet()) spending+=entry.getValue();
        LinearLayout linearLayout = findViewById(R.id.piechart_container);
        linearLayout.removeAllViews();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        PieChartFragment pieChartFragment = new PieChartFragment();
        pieChartFragment.parseData(data);
        pieChartFragment.setTagsVisible(false);
        TextView centretext = findViewById(R.id.transaction_value);

        TextView transactionType = findViewById(R.id.transaction_type);
        if(getSelectedType().equals("EXPENSE")){
            transactionType.setText("Spent:");
        }else{
            transactionType.setText("Received:");
        }

        String symbol = Currency.getInstance(getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();

        centretext.setText(symbol+spending);
        pieChartFragment.setFontColor(Color.WHITE);
        pieChartFragment.setHoleRadius(70);

        transaction.add(linearLayout.getId(),pieChartFragment);
        transaction.commitNow();

    }

    @Override
    public void showRemainingBalance(String currencySymbol, Double balance) {
        this.balance = balance;
        //retrieves the textview that mentions the balance
        TextView balanceTv = findViewById(R.id.balance);
        balanceTv.setText(currencySymbol+Double.toString(balance));
    }

    @Override
    public void showCategories() {
        getIntent().setClass(this,CurrentBalanceCategoriesActivity.class);
        getIntent().putExtra("TRANSACTION_TYPE", getSelectedType());
        startActivity(getIntent());
    }

    @Override
    public void showAccountMenu(ArrayList<String> accounts) {

    }

    @Override
    public void showTransactions(ArrayList<Transaction> data) {
        //get colours

        HashMap<String, Integer> colourMap = getColourMap(data);
        LinearLayout linearLayout = findViewById(R.id.transactions_container);
        linearLayout.removeAllViews();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        try {
            String month = "";
            String week = "";
            for(Transaction transaction : data){
                if(transaction.getType().equals(getSelectedType())) {

                    String date =transaction.getDate().replace("+0000","").replace("Z","").replaceFirst(" ","T").trim();
                    LocalDateTime dateTime = LocalDateTime.parse(date);
                    RecordFragment recordFragment = new RecordFragment(transaction.getName(), transaction.getCategory(), transaction.getCurrency(), transaction.getType(), colourMap.get(transaction.getCategory()), transaction.getTransaction());
                    if(!month.equals(dateTime.getMonth().toString())){
                        month = dateTime.getMonth().toString().substring(0, 1).toUpperCase() + dateTime.getMonth().toString().substring(1);
                        recordFragment.setMonth(month);
                        recordFragment.setYear(Integer.toString(dateTime.getYear()));
                    }
                    if(!week.equals(dateTime.getDayOfWeek().toString())){
                        week = dateTime.getDayOfWeek().toString().substring(0, 1).toUpperCase() + dateTime.getDayOfWeek().toString().substring(1);
                        recordFragment.setWeek(week);
                        recordFragment.setDay(dateFormatter(dateTime.getDayOfMonth()));
                    }

                    fragmentTransaction.add(linearLayout.getId(),recordFragment);
                }

            }
            fragmentTransaction.commitNow();
        }catch (DateTimeException e){
            System.out.println(e.getMessage());
        }

    }

    private String dateFormatter(int date){
        if(date==1 || date==21 || date==31){
            return Integer.toString(date)+"st"; // 1 -> 1st
        }
        if(date==2 || date==22){
            return Integer.toString(date)+"nd"; //2 -> 2nd
        }
        if(date==3 || date==23){
            return Integer.toString(date)+"rd"; //3 -> 3nd
        }
        else{
            return Integer.toString(date)+"th"; //4 -> 4th
        }
    }

    private HashMap<String, Integer> getColourMap(ArrayList<Transaction> data){
        ArrayList<Integer> colours = new ArrayList<>();
        for(int color: ColorTemplate.MATERIAL_COLORS) colours.add(color);
        for(int color: ColorTemplate.VORDIPLOM_COLORS)colours.add(color);
        HashMap<String, Integer> colourMap = new HashMap<>();
        HashMap<String,Double> map = new HashMap<>();
        for(Transaction transaction : data){
            if(transaction.getType().equals(getSelectedType())) {
                map.put(transaction.getCategory(), map.get(transaction.getCategory())==null? transaction.getTransaction(): map.get(transaction.getCategory())+transaction.getTransaction());
            }
        }

        for(Map.Entry<String, Double> entry : map.entrySet()){
            if(colourMap.get(entry.getKey())==null){
                colourMap.put(entry.getKey(),colours.get(0));
                colours.remove(0);
            }

        }
        return colourMap;
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
    public Context getContext() {
        return this;
    }

    @Override
    public Intent getIntentData(){
        return getIntent();
    }

    @Override
    public String getSelectedType() {
        return typeSelection.getSelectedItem().toString();
    }

    @Override
    public void showPayDay(String date, String type) {
        try {
            String[] dataArr = date.split(" ");
            String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG","SEP", "OCT", "NOV", "DEC"};
            LocalDateTime date1,date2;
            if(Arrays.asList(months).contains(dataArr[0])) {
                String month = (Arrays.asList(months).indexOf(dataArr[0])+1)<10? "0"+(Arrays.asList(months).indexOf(dataArr[0])+1) : Integer.toString(Arrays.asList(months).indexOf(dataArr[0])+1);
                String day = dataArr[1].length() < 2 ? "0" + dataArr[1] : dataArr[1];
                String year = dataArr[2];
                date1 = LocalDateTime.parse(year + "-" + month + "-" + day + "T00:00");
                date2 = LocalDateTime.now();
            }else{
                date1 = LocalDateTime.parse(date.replace("Z",""));
                date2 = LocalDateTime.now();
            }
            long daysBetween = Duration.between(date2, date1 ).toDays();
            if(daysBetween==0){
                ((TextView)findViewById(R.id.days)).setText("TODAY");
                return;
            }
            else if(daysBetween<0){
                switch (type){
                    case "weekly":

                        while(daysBetween<0){
                            date1=date1.plusDays(7);
                            daysBetween = Duration.between(date2, date1 ).toDays();
                        }
                        presenter.updatePayDayDate(date1.toString());
                        break;
                    case "biweekly":

                        while(daysBetween<0){
                            date1=date1.plusDays(14);
                            daysBetween = Duration.between(date2, date1 ).toDays();
                        }
                        presenter.updatePayDayDate(date1.toString());
                        break;
                    case "monthly" :
                        while(daysBetween<0){
                            date1=date1.plusDays(31);
                            daysBetween = Duration.between(date2, date1 ).toDays();
                        }
                        presenter.updatePayDayDate(date1.toString());
                        break;
                    case "bimonthly" :
                        while(daysBetween<0){
                            date1=date1.plusDays(61);
                            daysBetween = Duration.between(date2, date1 ).toDays();
                        }
                        presenter.updatePayDayDate(date1.toString());

                        break;

                }

            }
            ((TextView)findViewById(R.id.days)).setText(Long.toString( daysBetween));


        }catch (DateTimeException e){
            System.out.println("Something went wrong "+e.getMessage());
        }


    }

    @Override
    public void hidePayDayCounter() {
        ((LinearLayout) (findViewById(R.id.daysTillPay))).removeAllViews();
        ((LinearLayout) (findViewById(R.id.daysTillPay))).setVisibility(View.INVISIBLE);
    }


}