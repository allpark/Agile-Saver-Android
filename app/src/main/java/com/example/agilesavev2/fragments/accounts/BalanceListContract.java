package com.example.agilesavev2.fragments.accounts;

import android.view.View;

import com.example.agilesavev2.models.users.Account;

import java.util.List;

public interface BalanceListContract {
    interface BalanceListView{
        void showAdjustBalance();
        void showSettings();
        void displayAccounts(List<Account> accounts);
        void addAccount();
        void refreshFragment();
    }

    interface BalanceListPresenter{
        void handleAdjustBalance(View view);
        void handleSettings(View view);
        void getAccounts();
        void handleAddAccount(View view);
    }
}
