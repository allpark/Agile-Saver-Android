package com.example.agilesavev2.views.complete_signup;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.users.User;
import com.example.agilesavev2.services.Services;
import com.example.agilesavev2.utils.DatePicker;

import java.util.HashMap;

public class CompleteSignUpPresenter implements CompleteSignUpContract.CompleteSignUpPresenter, Presenter {
    private CompleteSignUpContract.CompleteSignUpView view;
    private Services userService;
    private Intent intent;
    private RadioButton radioButton_yes;

    public CompleteSignUpPresenter(CompleteSignUpContract.CompleteSignUpView view,Intent intent) {
        this.view = view;
        this.intent=intent;
        userService = new Services(this, this.view.getContext());
    }

    @Override
    public void handleSubmit(View view) {
        if(radioButton_yes!=null && radioButton_yes.isChecked()){

            HashMap<String,String> payInfo = new HashMap<>();
            payInfo.put("userID", intent.getStringExtra("ID"));
            payInfo.put("payDayType", this.view.getPayType());
            payInfo.put("nextPayDate", this.view.getNextPayDate());
            payInfo.put("pay", this.view.payValue());
            userService.addPayInfo(payInfo);
        }


        HashMap<String,String> map = new HashMap<>();
        map.put("id",intent.getStringExtra("ID"));
        map.put("firstName",this.view.getFirstname());
        map.put("surName",this.view.getSurname());
        map.put("main_currency",this.view.getMainCurrency());
        userService.completeSignUp(map,this);



    }

    @Override
    public void handleNext(View view) {
        this.view.next();
    }

    @Override
    public void handleOnSuccess(User userData) {
        userService.cacheUserData(Integer.parseInt(userData.getId()));
        view.onSuccess(userData);

    }

    @Override
    public void handleOnAddAccount(View view) {

    }


    @Override
    public <T> void handleOnSuccess(int code, T data) {
       // userService.getCurrencyConversionRates();
        if(code==7){
            this.view.showCustomeToast("Pay info added!");
        }
        if(code==1){
            User userData = (User) data;
            this.view.proceed(userData);
        }

    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }

    @Override
    public void handleAddPayInfo(View view) {
        this.view.showAddPayInfo();
    }

    @Override
    public void handleRadioButton(View view) {
        RadioButton radioButton = (RadioButton) view;
        String radioText = radioButton.getText().toString();
        LinearLayout payInfo = ((View)view.getParent().getParent().getParent()).findViewById(R.id.payInfoSection);
        if(radioText.toLowerCase().equals("yes")){
            //show pay info section
            radioButton_yes = radioButton;
            payInfo.setVisibility(View.VISIBLE);
        }else{
            //hide pay info section
            payInfo.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public void onHandleSelectPayDayType(CompleteSignUpActivity view) {
        Spinner spinner = view.findViewById(R.id.payInfo1);
        String[] payDayType = {"How often is payday?","weekly","biweekly","monthly","bimonthly"};
        Button payDate = view.findViewById(R.id.paydayDate);
        EditText pay = view.findViewById(R.id.pay);
        Button payDatePicker = view.findViewById(R.id.paydayDate);
        new DatePicker(payDatePicker, view.getIntent(), view.getContext());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = payDayType[position];
                if(!type.equals("How often is payday?")){
                    payDate.setVisibility(View.VISIBLE);
                    pay.setVisibility(View.VISIBLE);
                    pay.setHint(type+" pay");
                }else{
                    payDate.setVisibility(View.INVISIBLE);
                    pay.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
