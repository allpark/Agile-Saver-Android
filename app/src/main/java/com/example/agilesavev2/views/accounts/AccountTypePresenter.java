package com.example.agilesavev2.views.accounts;

import android.content.Intent;
import android.view.View;

public class AccountTypePresenter implements AccountTypeContract.AccountTypePresenter {
    private AccountTypeContract.AccountTypeView view;
    private Intent intent;

    public AccountTypePresenter( Intent intent, AccountTypeContract.AccountTypeView view) {
        this.view = view;
        this.intent = intent;
    }

    @Override
    public void handleShowSyncBankAccount(View view) {
        this.view.showSyncBankAccount();
    }

    @Override
    public void handleShowFileImport(View view) {
        this.view.showFileImport();
    }

    @Override
    public void handleManualInput(View view) {
        this.view.showManualInput();
    }

    @Override
    public void handleBack(View view) {
        this.view.back();
    }
}
