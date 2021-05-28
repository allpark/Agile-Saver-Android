package com.example.agilesavev2.views.current_balance.categories;

import android.view.View;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.transactions.Transaction;
import com.example.agilesavev2.services.Services;
import com.example.agilesavev2.utils.CurrencyConvertion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CurrentBalanceCategoriesPresenter implements CurrentBalanceCategoriesContract.categoriesPresenter, Presenter {
    private CurrentBalanceCategoriesContract.categoriesView view;
    private Services services;
    public CurrentBalanceCategoriesPresenter(CurrentBalanceCategoriesContract.categoriesView view) {
        this.view = view;
        services = new Services(this, this.view.getContext());
    }

    @Override
    public void getTransactionData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("userID", Integer.toString(view.getUserID()));
        map.put("accountID", Integer.toString(view.getSelectedAccountID()));
        services.getUserTransactionData(map, this);
    }

    @Override
    public void handleAddCategory(View view) {
        this.view.showAddCategories();
    }

    @Override
    public void handleGoBack(View view) {
        this.view.goBack();
    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {
        HashMap<String,Double> map = new HashMap<>();
        ArrayList<Transaction> transactionList = (ArrayList<Transaction>)data;

        for(Transaction transaction : transactionList){
            if(transaction.getType().equals(this.view.getType())) {
                transaction.setTransaction(CurrencyConvertion.getConvertedCurrency(transaction.getCurrency(),transaction.getTransaction()));
                map.put(transaction.getCategory(), map.get(transaction.getCategory())==null? transaction.getTransaction(): map.get(transaction.getCategory())+transaction.getTransaction());
            }
        }
        view.showCategories(map);
        view.showPieChart(map);
    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }
}
