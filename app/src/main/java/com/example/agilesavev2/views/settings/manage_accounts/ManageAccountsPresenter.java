package com.example.agilesavev2.views.settings.manage_accounts;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.users.Account;
import com.example.agilesavev2.services.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageAccountsPresenter implements ManageAccountsContract.ManageAccountsPresenter, Presenter {
    //Declaring private variables
    private ManageAccountsContract.ManageAccountsView view;//View handle
    private Intent intent;//data from the view to be accessed here
    private Context context;//context of the view needed for the database
    private Services services;//the services handle

    private List<Account> accountList;

    public ManageAccountsPresenter(ManageAccountsContract.ManageAccountsView view, Intent intent, Context context) {
        this.view = view;
        this.intent = intent;
        this.context = context;
        services= new Services(this, context);
    }

    @Override
    public void retrieveAccounts() {
        HashMap<String, String> map = new HashMap<>();
        map.put("userID",intent.getStringExtra("ID"));
        services.getAccounts(map,this);
    }

    @Override
    public void handleBack(View view) {

    }

    @Override
    public void handleAddAccount(View view) {

    }

    @Override
    public void setListeners(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleSelectedAccount(accountList.get(position));
            }
        });
    }

    @Override
    public void handleSelectedAccount(Account account) {
        view.showSelectedAccount(account);
    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {
        accountList = (List<Account>)data;
        List<String> list = new ArrayList<>();
        for(Account account : accountList)list.add(account.getName());
        this.view.listAccounts(list);
    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }
}
