package com.example.agilesavev2.views.settings.pay_info_setting;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.services.Services;

import java.util.HashMap;

public class PayInfoPresenter implements PayInfoContract.CompleteSignUpPresenter, Presenter {
    PayInfoContract.CompleteSignUpView view;
    Services services;
    public PayInfoPresenter(PayInfoContract.CompleteSignUpView view) {
        this.view = view;
        services = new Services(this, this.view.getContext());
        onHandleSelectPayDayType(this.view.getPayTypeSpinner());
    }

    @Override
    public void handleSubmit(View view) {
        if(this.view.formValidation()){
            Intent intent = this.view.getIntentData();
            HashMap<String,String> payInfo = new HashMap<>();
            payInfo.put("userID", intent.getStringExtra("ID"));
            payInfo.put("payDayType", this.view.getPayType());
            payInfo.put("nextPayDate", this.view.getNextPayDate());
            payInfo.put("pay", this.view.payValue());
            services.addPayInfo(payInfo);
        }

    }

    @Override
    public void onHandleSelectPayDayType(View view) {
        View parentView = (View) view.getParent();
        Spinner spinner = (Spinner) view;
        String[] payDayType = {"How often is payday?","weekly","biweekly","monthly","bimonthly"};
        Button payDate = parentView.findViewById(R.id.paydayDate);
        EditText pay = parentView.findViewById(R.id.pay);
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

//    @Override
//    public void handlePayDateSelected(View view) {
//        this.view.openDatePicker();
//    }


    @Override
    public <T> void handleOnSuccess(int code, T data) {
        if(code==7){
            this.view.showMessage("Pay info has been added!");
        }
    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }

    @Override
    public void handleBack(View view) {
        this.view.goBack();
    }


}
