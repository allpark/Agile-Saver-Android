package com.example.agilesavev2.views.accounts;

import android.view.View;

public interface AccountTypeContract {
    interface AccountTypeView{
        void showSyncBankAccount();
        void showFileImport();
        void showManualInput();
        void back();
    }
    interface AccountTypePresenter{
        void handleShowSyncBankAccount(View view);
        void handleShowFileImport(View view);
        void handleManualInput(View view);
        void handleBack(View view);
    }
}
