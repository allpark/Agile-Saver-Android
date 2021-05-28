package com.example.agilesavev2.views.current_balance;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.transactions.Transaction;
import com.example.agilesavev2.models.users.Account;
import com.example.agilesavev2.models.users.UserPayInfo;
import com.example.agilesavev2.services.Services;
import com.example.agilesavev2.utils.CurrencyConvertion;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;

public class CurrentBalancePresenter implements CurrentBalanceContract.CurrentBalancePresenter, Presenter {
    private CurrentBalanceContract.CurrentBalanceView view;
    private Services services;
    public CurrentBalancePresenter(CurrentBalanceContract.CurrentBalanceView view) {
        this.view = view;
        services=new Services(this, view.getContext());
    }

    @Override
    public void getCurrentBalance() {
        //creating hashmap to store userID and accountID needed for retrieving the balance from the account
        HashMap<String, String> map = new HashMap<>();
        map.put("userID", Integer.toString(view.getUserID()));
        map.put("accountID", Integer.toString(view.getSelectedAccountID()));
        Account account = services.getAccount(map,this);
        String accountSymbol = Currency.getInstance(account.getCurrency()).getSymbol();
        view.showRemainingBalance(accountSymbol,account.getBalance());


    }

    @Override
    public void handleCategories(View view) {
        this.view.showCategories();
    }

    @Override
    public void getTransactionData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("userID", Integer.toString(this.view.getUserID()));
        map.put("accountID", Integer.toString(this.view.getSelectedAccountID()));
        services.getUserTransactionData(map,this);
    }

    @Override
    public void handleAccountSelection(View view) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userID", Integer.toString(this.view.getUserID()));
        services.getAccounts(map, this);
    }

    @Override
    public void handleSelectType(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getTransactionData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void getPayDate() {
        services.getPayInfo(view.getIntentData().getStringExtra("ID"));
    }

    @Override
    public void updatePayDayDate(String date) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userID", view.getIntentData().getStringExtra("ID"));
        map.put("nextPayDate", date);
        services.updatePayDate(map);
    }



    @Override
    public <T> void handleOnSuccess(int code, T data) {
        if(code==4){
            HashMap<String,Double> map = new HashMap<>();
            ArrayList<Transaction> transactionList = (ArrayList<Transaction>)data;
            for(Transaction transaction : transactionList){
                if(transaction.getType().equals(view.getSelectedType())) {
                    transaction.setTransaction(CurrencyConvertion.getConvertedCurrency(transaction.getCurrency(),transaction.getTransaction()));
                    map.put(transaction.getCategory(), map.get(transaction.getCategory())==null? transaction.getTransaction(): map.get(transaction.getCategory())+transaction.getTransaction());
                }
            }
            this.view.showTransactions(transactionList);
            view.showPieChart(map);
        }
        if(code==8){
            UserPayInfo info = (UserPayInfo) data;
            System.out.println(info);
            this.view.showPayDay(info.getNextPayDate(), info.getPayDayType());
            return;
        }
        if(code==9){
            view.showAccountMenu((ArrayList<String>)data);
        }
    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }


}
