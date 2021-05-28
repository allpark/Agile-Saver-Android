package com.example.agilesavev2.views.saving_goals;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.agilesavev2.models.saving_goal.SavingsGoal;

import java.util.List;

public interface SavingGoalsContract {
    interface SavingGoalView {
        void listSavings(List<SavingsGoal> data);
        void addSavingGoal();
        void showSavingGoals();
        void showCategories();
        void showSavings();
        void showActive();
        void showReached();
        void clearFragments();
        Context getContext();
        Intent getIntentData();
        String getUserID();
    }
    interface SavingGoalPresenter {
        void handleAddSavingsGoal(View view);
        void loadSavingsGoal();
        void getCategories();
        void getSavings();
        void handleActive();
        void handleReached();
        void updateSavings(String savingID, String savingTargetGoal, String savingCurrent, String savingTargetDate);
        void deleteSavings(String savingID);
    }
}
