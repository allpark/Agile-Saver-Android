package com.example.agilesavev2.views.accounts.manually_input;

import android.content.Context;
import android.view.View;

public interface ManualInputContract {
    interface ManualInputView{
        void submitted();
        String getAccountName();
        String getAccountType();
        double getBalance();
        String getCurrencyType();
        Context getContext();
        void goBack();
        boolean formValidate();
        void showMessage(String message);
        boolean isMain();
    }

    interface ManualInputPresenter{
        void handleSubmit(View view);
        void handleBack(View view);
    }
}
