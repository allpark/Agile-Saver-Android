package com.example.agilesavev2.views.calculator.assets;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.agilesavev2.R;

import java.util.List;

public class AccountListViewActivity extends AppCompatActivity implements AccountListContract.AccountListView{
    private ListView listView;
    private AccountListPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_account_list_view);
        presenter = new AccountListPresenter(getIntent(),this);
        listView = findViewById(R.id.accounts);
        presenter.handleAccountList();

        ImageView imageView = findViewById(R.id.imageView4);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void showAccountList(List<String> accounts) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,accounts);
        listView.setAdapter(arrayAdapter);
        presenter.setListeners(listView);
    }

    @Override
    public void sendSelectedAccountBack(String account) {
        getIntent().putExtra("SELECTED_ACCOUNT", account);
        setResult(RESULT_OK, getIntent());
        finish();
    }

}