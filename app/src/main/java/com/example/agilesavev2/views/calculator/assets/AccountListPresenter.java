package com.example.agilesavev2.views.calculator.assets;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountListPresenter implements AccountListContract.AccountListPresenter {
    private Intent intent;
    private AccountListContract.AccountListView view;
    private List<String> accounts;

    public AccountListPresenter(Intent intent, AccountListContract.AccountListView view) {
        this.intent = intent;
        this.view = view;
        accounts = new ArrayList<>();
    }

    @Override
    public void handleAccountList() {
        HashMap<String,Integer> accountMap = (HashMap<String,Integer>) intent.getSerializableExtra("ACCOUNTS");
        for(Map.Entry<String,Integer> account : accountMap.entrySet()) accounts.add(account.getKey());
        view.showAccountList(accounts);
    }

    @Override
    public void setListeners(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleSelectedAccount(accounts.get(position));
            }
        });
    }

    @Override
    public void handleSelectedAccount(String account) {
        view.sendSelectedAccountBack(account);
    }
}
