package com.example.agilesavev2.views.my_budget;

import android.view.View;

import com.example.agilesavev2.models.Budgets.Budget;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface MyBudgetContract {
    interface MyBudgetView{
        void listBudgets(List<Budget> budgets);
        void addBudget();
        void showPayDay(String dateTime, String type);
        void hidePayDayCounter();
        String getSymbol();
        String getUserID();
    }

    interface MyBudgetPresenter{
        void handleAddBudget(View view);
        void handleDeleteBudget(String budgetID);
        void deleteBudget(String budgetName);
        void updateBudget(HashMap<String, String> map);
        void getBudgets();
        void getPayDate();
        void updatePayDayDate(String date);
    }
}
