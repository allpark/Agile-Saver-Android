package com.example.agilesavev2.views.settings;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public interface SettingsPageContract {
    interface SettingsPageView{
        void goBack();
        Context getContext();
        Intent getIntentData();
        String getUserID();
        void showManageAccounts();
        void showAddCategory();
        void showManagePayInfo();
        void showManageUserAccount();
    }
    interface SettingsPagePresenter{
        void handleGoBack(View view);
        void handleManageAccounts(View view);
        void handleAddCategory(View views);
        void handleManagePayInfo(View view);
        void manageUserAccount(View view);
    }
}
