package com.example.agilesavev2.views.addBudget;

import android.view.View;

public interface AddBudgetContract {
    interface AddBudgetView{
        void back();
        String getName();
        String getTarget();
        void returnResult();
        void showCategory();
    }

    interface AddBudgetPresenter{
        void handleAddBudget(View view);
        void handleBack(View view);
        void handleShowCategory(View view);
    }
}
