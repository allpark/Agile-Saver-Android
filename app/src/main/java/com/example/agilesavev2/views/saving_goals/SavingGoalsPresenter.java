package com.example.agilesavev2.views.saving_goals;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.saving_goal.SavingsGoal;
import com.example.agilesavev2.services.Services;

import java.util.HashMap;
import java.util.List;

public class SavingGoalsPresenter implements SavingGoalsContract.SavingGoalPresenter, Presenter {
    private SavingGoalsContract.SavingGoalView view;
    Intent intent;
    private Services services;

    public SavingGoalsPresenter(Intent intent, SavingGoalsContract.SavingGoalView view, Context context) {
        this.view = view;
        this.intent=intent;
        services=new Services(this, context);
    }

    @Override
    public void handleAddSavingsGoal(View view) {
        this.view.addSavingGoal();
    }

    @Override
    public void loadSavingsGoal() {

    }

    @Override
    public void getCategories() {

    }

    @Override
    public void getSavings() {
        String userID= intent.getStringExtra("ID");
        this.services.getSavings(userID,this);
    }

    @Override
    public void handleActive() {

    }

    @Override
    public void handleReached() {

    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {
        if(code==13 || code==14){
            getSavings();
        }
        if(code==2){
            this.view.listSavings((List<SavingsGoal>)data);
        }

    }
    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }

    public void updateSavings(String savingID, String savingTargetGoal, String savingCurrent, String savingTargetDate){
        HashMap<String,String> map = new HashMap<>();
        map.put("userID", this.view.getUserID());
        map.put("id",savingID);
        map.put("targetgoal", savingTargetGoal);
        map.put("progress", savingCurrent);
        map.put("targetdate", savingTargetDate);
        this.services.updateSavingGoal(map);
        this.services.updateSavingGoalOnBackEnd(map);
        System.out.println("UPDATING " + savingID +" " +  savingTargetGoal + " " + savingCurrent +" "  +savingTargetDate);
    }

    public void deleteSavings(String savingID){
        HashMap<String,String> map = new HashMap<>();
        map.put("userID", this.view.getUserID());
        map.put("id",savingID);
        this.services.deleteSavingGoal(map);
        this.services.deleteSavingOnBackEnd(map);
        System.out.println("REMOVING " + savingID);
    }
}
