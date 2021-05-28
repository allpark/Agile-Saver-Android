package com.example.agilesavev2.views.addBudget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.services.Services;

import java.util.HashMap;

public class AddBudgetPresenter implements AddBudgetContract.AddBudgetPresenter, Presenter {
    AddBudgetContract.AddBudgetView view;
    Intent intent;
    Services services;
    public AddBudgetPresenter(Intent intent , AddBudgetContract.AddBudgetView view, Context context) {
        this.view=view;
        services = new Services(this, context);
    }

    @Override
    public void handleAddBudget(View view) {
        if(!this.view.getName().equals("") || !this.view.getTarget().equals("")) {
            HashMap<String, String> map = new HashMap<>();
            map.put("userID", intent.getStringExtra("ID"));
            map.put("isPassed", "false");
            map.put("name", this.view.getName());
            map.put("target", this.view.getTarget());

            services.addBudget(map, this);
        }

    }

    @Override
    public void handleBack(View view) {
        this.view.back();
    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {
        this.view.returnResult();
    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }

    @Override
    public void handleShowCategory(View view) {
        this.view.showCategory();
    }
}
