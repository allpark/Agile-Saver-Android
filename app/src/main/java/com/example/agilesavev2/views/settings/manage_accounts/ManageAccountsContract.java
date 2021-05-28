package com.example.agilesavev2.views.settings.manage_accounts;

import android.view.View;
import android.widget.ListView;

import com.example.agilesavev2.models.users.Account;

import java.util.List;

public interface ManageAccountsContract {
    interface ManageAccountsView{
        void listAccounts(List<String> accountList);
        void showAddAccount();
        void showSelectedAccount(Account selectedAccount);
        void goBack();
    }
    interface ManageAccountsPresenter{
        void retrieveAccounts();
        void handleBack(View view);
        void handleAddAccount(View view);
        void setListeners(ListView listView);
        void handleSelectedAccount(Account account);
    }
}
