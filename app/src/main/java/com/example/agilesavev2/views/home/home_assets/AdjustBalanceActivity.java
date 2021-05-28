package com.example.agilesavev2.views.home.home_assets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivityAdjustBalanceBinding;

public class AdjustBalanceActivity extends AppCompatActivity {
    private EditText balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ActivityAdjustBalanceBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_adjust_balance);
        AdjustBalancePresenter presenter = new AdjustBalancePresenter(getIntent(),this);
        binding.setPresenter(presenter);
        getSupportActionBar().hide();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(height/2));

        balance = findViewById(R.id.adjustBalance_input);
    }

    public void onSuccess(){
        Toast.makeText(this, "Balance adjusted", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("newBalance",getInputBalance());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onReject(){

    }

    public String getInputBalance(){

        return balance.getText().toString();
    }
}