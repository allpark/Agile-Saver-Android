package com.example.agilesavev2.views.saving_goals.addSavingGoal;

import android.view.View;

public interface AddSavingContract {
    interface AddSavingView{
        void back();
        String getName();
        String getCurrentAmount();
        String getTargetAmount();
        String getTargetDate();
        void showMessage(String message);
        boolean formValidate();
        void returnResult();
    }

    interface AddSavingPresenter{
        void handleAddSaving(View view);
        void handleBack(View view);
    }
}
