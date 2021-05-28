package com.example.agilesavev2.views.settings;

import android.view.View;
import android.widget.TextView;

import com.example.agilesavev2.R;

public class SettingsPagePresenter implements SettingsPageContract.SettingsPagePresenter {
    private SettingsPageContract.SettingsPageView view;

    public SettingsPagePresenter(SettingsPageContract.SettingsPageView view) {
        this.view = view;
    }


    @Override
    public void handleGoBack(View view) {
        this.view.goBack();
    }

    @Override
    public void handleManageAccounts(View view) {
        this.view.showManageAccounts();
    }

    @Override
    public void handleAddCategory(View views) {
        this.view.showAddCategory();
    }

    @Override
    public void handleManagePayInfo(View view) {
        this.view.showManagePayInfo();
    }

    @Override
    public void manageUserAccount(View view) {
        this.view.showManageUserAccount();
    }
}
