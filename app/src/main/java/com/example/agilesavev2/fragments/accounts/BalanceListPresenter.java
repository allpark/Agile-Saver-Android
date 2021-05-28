package com.example.agilesavev2.fragments.accounts;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.fragments.accounts.account.AccountFragment;
import com.example.agilesavev2.fragments.budgetElement.BudgetDialog;
import com.example.agilesavev2.models.users.Account;
import com.example.agilesavev2.services.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BalanceListPresenter implements BalanceListContract.BalanceListPresenter, Presenter {
    private Intent intent;
    private BalanceListContract.BalanceListView view;
    private Services services;
    private ArrayList<Account> accounts;

    public BalanceListPresenter(Intent intent, BalanceListContract.BalanceListView view, Context context) {
        this.intent = intent;
        this.view = view;
        services = new Services(this, context);
    }


    @Override
    public void handleAdjustBalance(View view) {
        BalanceListContract.BalanceListView balanceListView=this.view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balanceListView.showAdjustBalance();
            }



        });
    }

    @Override
    public void handleSettings(View view) {
        BalanceListContract.BalanceListView balanceListView=this.view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balanceListView.showSettings();
            }
        });

    }

    @Override
    public void getAccounts() {
        HashMap<String,String> map = new HashMap<>();
        map.put("userID",intent.getStringExtra("ID"));
        services.getAccounts(map,this);
    }

    @Override
    public void handleAddAccount(View view) {
        BalanceListContract.BalanceListView balanceListView=this.view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balanceListView.addAccount();
            }
        });

    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {
        accounts = (ArrayList<Account>) data;
        view.displayAccounts((List<Account>)data);
    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }

    //helper function
    Account getSelectedAccount(){
        String selectedAccount = intent.getStringExtra("SELECTED");
        for(Account account : accounts){
            if(account.getName().equals(selectedAccount)){
                return account;
            }
        }
        return null;
    }
}
