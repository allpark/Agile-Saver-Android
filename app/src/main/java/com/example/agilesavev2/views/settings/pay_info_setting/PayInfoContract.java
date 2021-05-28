package com.example.agilesavev2.views.settings.pay_info_setting;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.agilesavev2.models.users.User;

public interface PayInfoContract {
    interface CompleteSignUpView{
        void onSuccess(User userData);
        void onReject();
        Context getContext();
        void goBack();
        int getUserID();
        View getPayTypeSpinner();
        String getPayType();
        String getNextPayDate();
        String payValue();
        Intent getIntentData();
        void showMessage(String message);
        boolean formValidation();

    }
    interface CompleteSignUpPresenter{
        void handleSubmit(View view);
        void handleOnReject();
        void handleBack(View view);
        void onHandleSelectPayDayType(View view);

    }
}
