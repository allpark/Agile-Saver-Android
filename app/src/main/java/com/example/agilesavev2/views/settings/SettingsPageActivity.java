package com.example.agilesavev2.views.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivitySettingsPageBinding;
import com.example.agilesavev2.views.settings.add_category.AddCategoryActivity;
import com.example.agilesavev2.views.settings.manage_accounts.ManageAccountsActivity;
import com.example.agilesavev2.views.settings.manage_user_account.ManageUserAccountActivity;
import com.example.agilesavev2.views.settings.pay_info_setting.PayInfoActivity;

public class SettingsPageActivity extends AppCompatActivity implements SettingsPageContract.SettingsPageView{
    private SettingsPagePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ActivitySettingsPageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_settings_page);
        presenter = new SettingsPagePresenter(this);
        binding.setPresenter(presenter);
        getSupportActionBar().hide();
    }


    @Override
    public void goBack() {
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Intent getIntentData() {
        return getIntent();
    }

    @Override
    public String getUserID() {
        return getIntent().getStringExtra("ID");
    }

    @Override
    public void showManageAccounts() {
//        getIntent().setClass(this, ManageAccountsActivity.class);
//        startActivity(getIntent());
        Toast.makeText(getContext(), "Feature not available :(", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddCategory() {
        getIntent().setClass(this, AddCategoryActivity.class);
        startActivity(getIntent());
    }

    @Override
    public void showManagePayInfo() {
        getIntent().setClass(this, PayInfoActivity.class);
        startActivity(getIntent());
    }

    @Override
    public void showManageUserAccount() {
        getIntent().setClass(this, ManageUserAccountActivity.class);
        startActivity(getIntent());
    }
}