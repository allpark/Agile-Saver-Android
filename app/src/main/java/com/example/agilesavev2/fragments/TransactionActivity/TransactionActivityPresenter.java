package com.example.agilesavev2.fragments.TransactionActivity;

import android.content.Context;
import android.content.Intent;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.transactions.Transaction;
import com.example.agilesavev2.services.Services;

import java.util.HashMap;
import java.util.List;

public class TransactionActivityPresenter implements TransactionActivityActivityContract.spendingActivityPresenter, Presenter {
    private Intent intent;
    private TransactionActivityActivityContract.spendingActivityView view;
    private Services services;

    public TransactionActivityPresenter(Intent intent, TransactionActivityActivityContract.spendingActivityView view, Context context) {
        this.intent = intent;
        this.view = view;
        services = new Services(this, context);
    }

    @Override
    public void getTransactions() {
        HashMap<String, String> map = new HashMap<>();
        map.put("userID", intent.getStringExtra("ID"));
        map.put("accountID",intent.getStringExtra("SELECTED_ACCOUNT_ID"));
        services.getUserTransactionData(map, this);
    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {
        List<Transaction> list = (List<Transaction>) data;
        if(list==null || list.isEmpty()){
            this.view.hideTransaction();
        }else{
            this.view.displayTransactions(list);
        }

    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }
}
