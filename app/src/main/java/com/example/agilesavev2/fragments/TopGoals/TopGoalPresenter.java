package com.example.agilesavev2.fragments.TopGoals;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.fragments.saving_goals.SavingGoal;
import com.example.agilesavev2.models.saving_goal.SavingsGoal;
import com.example.agilesavev2.services.Services;

import java.util.ArrayList;
import java.util.Comparator;

public class TopGoalPresenter implements TopGoalContract.TopGoalPresenter , Presenter {
    private TopGoalContract.TopGoalView view;
    private Services services;

    public TopGoalPresenter(TopGoalContract.TopGoalView view) {
        this.view = view;
        services = new Services(this, view.getContextData());
    }

    @Override
    public void getTopGoal() {
        services.getSavings(this.view.getUserID(), this);
    }

    @Override
    public void handleShowMore(TextView tv) {
        TopGoalContract.TopGoalView view=this.view;
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.showMore();
            }
        });
    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {
        ArrayList<SavingsGoal> savingGoals = (ArrayList<SavingsGoal>) data;
        savingGoals.sort(new Comparator<SavingsGoal>() {
            @Override
            public int compare(SavingsGoal o1, SavingsGoal o2) {
                return (int)(o2.getProgress() - (100 / o2.getTargetGoal())) - (int)(o1.getProgress() - (100 / o1.getTargetGoal()));
            }
        });
        if(savingGoals!=null && !savingGoals.isEmpty()) {
            this.view.parseTopGoal(savingGoals.get(0));
        }else{
            this.view.hideTopGoal();
        }
    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }


}
