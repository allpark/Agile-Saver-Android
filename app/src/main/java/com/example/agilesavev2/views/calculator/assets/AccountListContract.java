package com.example.agilesavev2.views.calculator.assets;

import android.widget.ListView;

import java.util.List;

public interface AccountListContract {
    interface AccountListView{
        void showAccountList(List<String> accounts);
        void sendSelectedAccountBack(String account);
    }

    interface AccountListPresenter{
        void handleAccountList();
        void setListeners(ListView listView);
        void handleSelectedAccount(String account);

    }
}
