package com.example.agilesavev2.views.settings.pay_info_setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivityPayInfoSettingBinding;
import com.example.agilesavev2.models.users.User;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class PayInfoActivity extends AppCompatActivity implements PayInfoContract.CompleteSignUpView{
    private PayInfoPresenter presenter;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ActivityPayInfoSettingBinding binding = DataBindingUtil.setContentView(this ,R.layout.activity_pay_info_setting);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        presenter = new PayInfoPresenter(this);
        binding.setPresenter(presenter);

        dateButton= findViewById(R.id.paydayDate);
        com.example.agilesavev2.utils.DatePicker datePicker = new com.example.agilesavev2.utils.DatePicker(dateButton, getIntent(), this);

    }

    @Override
    public void onSuccess(User userData) {

    }

    @Override
    public void onReject() {

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
    public int getUserID() {
        return Integer.parseInt(getIntent().getStringExtra("ID"));
    }

    @Override
    public View getPayTypeSpinner() {
        return findViewById(R.id.paydayType);
    }

    @Override
    public String getPayType() {
        return ((Spinner)findViewById(R.id.paydayType)).getSelectedItem().toString();
    }

    @Override
    public String getNextPayDate() {
        return ((Button)findViewById(R.id.paydayDate)).getText().toString();
    }

    @Override
    public String payValue() {
        return ((EditText)findViewById(R.id.pay)).getText().toString();
    }

    @Override
    public Intent getIntentData() {
        return getIntent();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public boolean formValidation(){

        if(getPayType().equals("How often is payday?")){
            showMessage("Please complete pay information");
            return false;
        }
        if(payValue().trim().equals("")){
            showMessage("Please enter "+getPayType()+" pay");
            return false;
        }
        if(getNextPayDate().trim().equals("")){
            showMessage("Enter a next pay date");
            return false;
        }
        return true;
    }






}