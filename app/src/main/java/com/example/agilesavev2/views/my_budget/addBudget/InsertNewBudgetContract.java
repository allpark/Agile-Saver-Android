package com.example.agilesavev2.views.my_budget.addBudget;

import android.view.View;

public interface InsertNewBudgetContract {
    interface InsertBudgetView{
        void back();
        String getName();
        String getTarget();
        String getSelectedCategory();
        void returnResult();
        void showCategory();
        boolean formValidate();
        void showToastMessage(String message);
    }

    interface InsertBudgetPresenter{
        void handleAddBudget(View view);
        void handleBack(View view);
        void handleShowCategory(View view);
    }
}
