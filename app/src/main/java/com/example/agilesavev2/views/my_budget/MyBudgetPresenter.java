package com.example.agilesavev2.views.my_budget;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.Budgets.Budget;
import com.example.agilesavev2.models.users.User;
import com.example.agilesavev2.models.users.UserPayInfo;
import com.example.agilesavev2.services.Services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class MyBudgetPresenter implements MyBudgetContract.MyBudgetPresenter, Presenter {
    private MyBudgetContract.MyBudgetView view;
    private Intent intent;
    private Services services;
    public MyBudgetPresenter(Intent intent, MyBudgetContract.MyBudgetView view, Context context) {
        this.view = view;
        this.intent=intent;
        services=new Services(this, context);
    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {
        if(code==8){
            UserPayInfo info = (UserPayInfo) data;
            System.out.println(info);
            this.view.showPayDay(info.getNextPayDate(), info.getPayDayType());
            return;
        }

        this.view.listBudgets((List<Budget>)data);
    }

    @Override
    public void handleOnReject() {
        this.view.hidePayDayCounter();
    }

    @Override
    public void handleOnReject(int code) {

    }

    @Override
    public void handleAddBudget(View view) {
        this.view.addBudget();
    }

    @Override
    public void handleDeleteBudget(String budgetID) {

    }

    @Override
    public void deleteBudget(String budgetName) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", budgetName);
        map.put("userID", intent.getStringExtra("ID"));
        services.deleteBudget(map);
        getBudgets();
    }

    @Override
    public void updateBudget(HashMap<String, String> map) {
        map.put("userID", intent.getStringExtra("ID"));
        services.updateBudget( map);
        getBudgets();
    }

    @Override
    public void getBudgets() {
        String userID= intent.getStringExtra("ID");
        this.services.getBudget(userID,this);
    }

    @Override
    public void getPayDate() {
        services.getPayInfo(intent.getStringExtra("ID"));
    }

    @Override
    public void updatePayDayDate(String date) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userID", view.getUserID());
        map.put("nextPayDate", date);
        services.updatePayDate(map);
    }
}
