package com.example.agilesavev2.views.complete_signup;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.agilesavev2.models.users.User;

public interface CompleteSignUpContract {
    interface CompleteSignUpView{
        void onSuccess(User userData);
        void onReject();
        String getFirstname();
        String getSurname();
        void next();
        void proceed(User userData);
        void showAddPayInfo();
        Context getContext();
        Intent getIntentData();
        String getPayType();
        String getNextPayDate();
        String payValue();
        void showCustomeToast(String string);
        String getMainCurrency();
        boolean formValidation();

    }
    interface CompleteSignUpPresenter{
        void handleSubmit(View view);
        void handleNext(View view);
        void handleOnSuccess(User userData);
        void handleOnAddAccount(View view);
        void handleOnReject();
        void handleAddPayInfo(View view);
        void handleRadioButton(View view);
        void onHandleSelectPayDayType(CompleteSignUpActivity view);
    }
}
