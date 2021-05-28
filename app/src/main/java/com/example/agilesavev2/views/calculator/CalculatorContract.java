package com.example.agilesavev2.views.calculator;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public interface CalculatorContract {
    interface CalculatorView{
        void updateScreen(String num);
        void incomeMode();
        void expenseMode();
        void showCategory();
        void onSuccess();
        void onReject();
        void save();
        void cancel();
        void getMode();
        void addDecimal();
        Intent getTransactionData();
        String transactionType();
        String getSelectedAccountName();
        Integer getSelectedAccountID();
        void showAccountList();
        void showNotification();
        Context getContext();
        void showTimePicker();
        String getDateTime();
        String getName();
        String getUserId();
        String getInputValue();
        boolean formValidate();
    }
    interface CalculatorPresenter{
        void handleIncomeMode(View view);
        void handleExpenseMode(View view);
        void handleCategory(View view);
        void handleNumbers(View view);
        void handleOperations(View view);
        void handleSubmit(View view);
        void handleSave(View view);
        void handleCancel(View view);
        void handleDelete(View view);
        void handleDecimal(View view);
        void handleAccounts(View view);
        void handleTimePicker(View view);
        String getCurrencyType();

    }
}
