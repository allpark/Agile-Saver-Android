package com.example.agilesavev2.views.accounts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivityAccountTypeBinding;
import com.example.agilesavev2.views.accounts.import_csv.ImportCSVActivity;
import com.example.agilesavev2.views.accounts.manually_input.ManualInputAccountActivity;
import com.example.agilesavev2.views.accounts.sync_bank_account.SyncBankAccountActivity;

public class AccountTypeActivity extends AppCompatActivity implements AccountTypeContract.AccountTypeView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().hide();
        ActivityAccountTypeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_account_type);
        AccountTypePresenter presenter = new AccountTypePresenter(getIntent(),this);
        binding.setPresenter(presenter);
    }


    @Override
    public void showSyncBankAccount() {
//        Intent intent = new Intent(this , SyncBankAccountActivity.class);
//        intent.putExtra("ID", getIntent().getStringExtra("ID"));
//        startActivityForResult(intent,0);
        Toast.makeText(this, "Feature not available :(", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFileImport() {
//        Intent intent = new Intent(this , ImportCSVActivity.class);
//        intent.putExtra("ID", getIntent().getStringExtra("ID"));
//        startActivityForResult(intent,1);
        Toast.makeText(this, "Feature not available :(", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showManualInput() {
        getIntent().setClass(this, ManualInputAccountActivity.class);
        startActivityForResult(getIntent(),3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            setResult(RESULT_OK, getIntent());
            finish();
        }
    }

    @Override
    public void back() {
        finish();
    }
}