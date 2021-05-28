package com.example.agilesavev2.fragments.TopGoals;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.agilesavev2.fragments.saving_goals.SavingGoal;
import com.example.agilesavev2.models.saving_goal.SavingsGoal;

public interface TopGoalContract {
    interface  TopGoalView{
        void parseTopGoal(SavingsGoal topGoal);
        void showMore();
        Context getContextData();
        String getUserID();
        void hideTopGoal();
    }
    interface TopGoalPresenter{
        void getTopGoal();
        void handleShowMore(TextView view);
    }
}
