package com.example.agilesavev2.views.settings.manage_accounts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.databinding.ActivityManageAccountsBinding;
import com.example.agilesavev2.models.users.Account;

import java.util.List;

public class ManageAccountsActivity extends AppCompatActivity implements ManageAccountsContract.ManageAccountsView {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_manage_accounts);
        getSupportActionBar().hide();
        ActivityManageAccountsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_accounts);
        ManageAccountsPresenter presenter = new ManageAccountsPresenter(this,getIntent(),this);
        binding.setPresenter(presenter);
        listView = findViewById(R.id.accounts_list);
        presenter.retrieveAccounts();

    }

    @Override
    public void listAccounts(List<String> accountList) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,accountList);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void showAddAccount() {

    }

    @Override
    public void showSelectedAccount(Account account) {

    }

    @Override
    public void goBack() {

    }
}