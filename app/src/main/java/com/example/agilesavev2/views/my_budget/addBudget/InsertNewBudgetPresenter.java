package com.example.agilesavev2.views.my_budget.addBudget;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.Budgets.Budget;
import com.example.agilesavev2.services.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InsertNewBudgetPresenter implements InsertNewBudgetContract.InsertBudgetPresenter, Presenter {
    private InsertNewBudgetContract.InsertBudgetView view;
    private Intent intent;
    private Services services;
    private ArrayList<String> budgetList;

    public InsertNewBudgetPresenter(Intent intent, InsertNewBudgetContract.InsertBudgetView view, Context context) {
        this.view = view;
        this.intent=intent;
        services= new Services(this, context);
        services.getBudget(intent.getStringExtra("ID"),this);
    }

    @Override
    public void handleAddBudget(View view) {
        if(budgetList.contains(this.view.getName())){
            this.view.showToastMessage("Budget "+this.view.getName()+" already exists");
            return;
        }
        if(this.view.formValidate()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("userID", intent.getStringExtra("ID"));
            map.put("passed", "false");
            map.put("name", this.view.getName());
            map.put("target", this.view.getTarget());
            map.put("date", LocalDateTime.now().toString());
            map.put("category", this.view.getSelectedCategory());
            map.put("progress","0");
            services.addBudget(map, this);
            this.view.returnResult();
        }

    }

    @Override
    public void handleBack(View view) {
        this.view.back();
    }

    @Override
    public void handleShowCategory(View view) {
        this.view.showCategory();
    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {
        if(code==6){
            this.view.returnResult();
        }
        if(code==7){
            List<Budget> list = (List<Budget>)data;
            budgetList = new ArrayList<>();
            for(Budget budget : list){
                budgetList.add(budget.getName());
            }
        }


    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }
}
