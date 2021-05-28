package com.example.agilesavev2.views.settings.manage_user_account;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.agilesavev2.models.users.User;

public interface ManageUserAccountContract {
    interface UserAccountPageView{
        void goBack();
        Context getContext();
        Intent getIntentData();
        String getEmail();
        String getCurrencyType();
        String getUserID();
        void updateFields(User userData);
        Intent getIntentDate();
        void showMessage(String message);
        void sendToMainPage();
        void changeCurrency();
        void showMenuPage();
    }
    interface UserAccountPagePresenter{
        void handleUserData(User userData);
        void handleGoBack(View view);
        void handleSubmit(View view);
        void handleDelete(View view);
    }
}
