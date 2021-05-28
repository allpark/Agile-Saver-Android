package com.example.agilesavev2.views.accounts.manually_input;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivityManualInputAccountBinding;

public class ManualInputAccountActivity extends AppCompatActivity implements ManualInputContract.ManualInputView {
    ManualInputPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().hide();
        ActivityManualInputAccountBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_manual_input_account);
        presenter = new ManualInputPresenter(getIntent(),this);
        binding.setPresenter(presenter);

    }

    @Override
    public void submitted() {
        setResult(RESULT_OK);
        Toast.makeText(this, "Account added!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public String getAccountName() {
        EditText editText = findViewById(R.id.account_name);
        return editText.getText().toString();
    }

    @Override
    public String getAccountType() {
        Spinner spinner = findViewById(R.id.account_type);
        return spinner.getSelectedItem().toString();
    }

    @Override
    public double getBalance() {
        EditText editText = findViewById(R.id.balance);
        return Double.parseDouble(editText.getText().toString());
    }

    @Override
    public String getCurrencyType() {
        Spinner spinner = findViewById(R.id.currency_type);
        return spinner.getSelectedItem().toString();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public boolean formValidate() {
        if(getAccountName().trim().equals("")){
            showMessage("Please enter account name");
            return false;
        }
        if(getCurrencyType().equals("Select currency")){
            showMessage("Please select currency type");
            return false;
        }
        return true;

    }

    @Override
    public void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isMain() {
        Boolean isMain = Boolean.parseBoolean(getIntent().getStringExtra("isMain"));
        return isMain==true;
    }
}