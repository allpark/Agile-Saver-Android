package com.example.agilesavev2.views.complete_signup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivityCompleteSignUpBinding;
import com.example.agilesavev2.models.users.User;
import com.example.agilesavev2.views.accounts.AccountTypeActivity;
import com.example.agilesavev2.views.settings.pay_info_setting.PayInfoActivity;
import com.example.agilesavev2.views.home.HomeActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class CompleteSignUpActivity extends AppCompatActivity implements CompleteSignUpContract.CompleteSignUpView {
    CompleteSignUpPresenter presenter;
    private String firstName, surName, currencyType, payDayType, pay, nextPayDayDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window g=getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        getSupportActionBar().hide();
        ActivityCompleteSignUpBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_complete_sign_up);
        presenter = new CompleteSignUpPresenter(this,getIntent());
        binding.setPresenter(presenter);


        presenter.onHandleSelectPayDayType(this);
    }

    @Override
    public void onSuccess(User userData) {
       // getIntent().putExtra("BALANCE",userData.getBalance());
        getIntent().setClass(this,HomeActivity.class);
        startActivity(getIntent());
    }

    @Override
    public void onReject() {

    }

    @Override
    public String getFirstname() {
        EditText editText = findViewById(R.id.firstName);
        return editText.getText().toString();
    }

    @Override
    public String getSurname() {
        EditText editText = findViewById(R.id.surName);
        return editText.getText().toString();
    }


    @Override
    public void next() {

        if(formValidation()){
            Intent intent = new Intent(this, AccountTypeActivity.class);
            intent.putExtra("ID", getIntent().getStringExtra("ID"));
            intent.putExtra("isMain", "true");
            startActivityForResult(intent,1);
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setContentView(R.layout.activity_complete_sign_up);
    }

    @Override
    public void proceed(User userData) {
        getIntent().setClass(this, HomeActivity.class);
        getIntent().putExtra("MAIN_CURRENCY", userData.getMain_currency());
        startActivity(getIntent());
    }

    @Override
    public void showAddPayInfo() {
        getIntent().setClass(this, PayInfoActivity.class);
        System.out.println("hello world");
        startActivityForResult(getIntent(),2);
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
    public String getPayType() {
        return ((Spinner)findViewById(R.id.payInfo1)).getSelectedItem().toString();
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
    public void showCustomeToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getMainCurrency() {
        return ((Spinner)findViewById(R.id.currency_type)).getSelectedItem().toString().substring(0,3);
    }

    //payDayType, pay, nextPayDayDate
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK) {
                presenter.handleSubmit(null);
            }
        }
        if(requestCode==2){
            if(requestCode==RESULT_OK){
                payDayType= data.getStringExtra("PAYDAYTYPE");
                pay= data.getStringExtra("PAY");
                nextPayDayDate= data.getStringExtra("PAYDAYDATE");
            }
        }
    }

    public boolean formValidation(){
        RadioButton payCheck = findViewById(R.id.radio_yes);

        if(getFirstname().trim().equals("")){
            showCustomeToast("Please enter first name");
            return false;
        }
        if(getSurname().trim().equals("")){
            showCustomeToast("Please enter sur name");
            return false;
        }

        if(getMainCurrency().equals("Select currency")){
            showCustomeToast("Please select a currency");
            return false;
        }

        if(payCheck.isChecked()){
            if(getPayType().equals("How often is payday?")){
                showCustomeToast("Please complete pay information");
                return false;
            }
            if(payValue().trim().equals("")){
                showCustomeToast("Please enter "+getPayType()+" pay");
                return false;
            }
        }
        if(getNextPayDate().trim().equals("")){
            showCustomeToast("Enter a next pay date");
            return false;
        }
        if(LocalDate.now().isAfter(LocalDate.parse(convertDateFormat()))){
            showCustomeToast("Pay day is already past");
            return false;
        }
        return true;
    }


    //private functions for other features

    private String convertDateFormat(){
        String[] months = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        String[] date = getNextPayDate().split(" ");
        String month = Integer.toString(Arrays.asList(months).indexOf(date[0])+1);
        return date[2]+"-"+(month.length()<2? "0"+month : month)+"-"+(date[1].length()<2?"0"+date[1]:date[1]);
    }

}