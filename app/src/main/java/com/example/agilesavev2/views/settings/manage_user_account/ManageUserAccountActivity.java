package com.example.agilesavev2.views.settings.manage_user_account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivityManageUserAccountBinding;
import com.example.agilesavev2.models.users.User;
import com.example.agilesavev2.views.main.MainActivity;

public class ManageUserAccountActivity extends AppCompatActivity implements ManageUserAccountContract.UserAccountPageView{
    private ManageUserAccountPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ActivityManageUserAccountBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_user_account);
        presenter = new ManageUserAccountPresenter(getIntent(), this, getContext());
        binding.setPresenter(presenter);

        getSupportActionBar().hide();
    }


    @Override
    public void updateFields(User userData){

        EditText email = findViewById(R.id.email);
        Spinner currencyType = findViewById(R.id.currency_type);

        email.setText(userData.getEmail());
        Adapter adapter = currencyType.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            String currency = (String)adapter.getItem(i);
            if (currency.startsWith(userData.getMain_currency())){
                currencyType.setSelection(i);
                break;
            }
        }

    }

    @Override
    public Intent getIntentDate() {
        return getIntent();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendToMainPage() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void changeCurrency() {
        String newCurrency = getCurrencyType().split("-")[0].trim();
        getIntent().putExtra("MAIN_CURRENCY",newCurrency);
    }

    @Override
    public void showMenuPage() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    @Override
    public String getEmail(){
        EditText email = findViewById(R.id.email);
        return email.getText().toString();
    }


    @Override
    public String getCurrencyType(){
        Spinner currencyType = findViewById(R.id.currency_type);
        return currencyType.getSelectedItem().toString();
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


}