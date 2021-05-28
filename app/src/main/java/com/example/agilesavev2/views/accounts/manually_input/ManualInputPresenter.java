package com.example.agilesavev2.views.accounts.manually_input;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.users.Account;
import com.example.agilesavev2.services.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManualInputPresenter implements ManualInputContract.ManualInputPresenter, Presenter {
    private Intent intent;
    private ManualInputContract.ManualInputView view;
    private Services services;
    private ArrayList<Account> accounts;

    public ManualInputPresenter(Intent intent, ManualInputContract.ManualInputView view) {
        this.intent = intent;
        this.view = view;
        services=new Services(this, this.view.getContext());

        HashMap<String, String > map = new HashMap<>();
        map.put("userID", intent.getStringExtra("ID"));
        services.getAccounts(map, this);
    }

    @Override
    public void handleSubmit(View view) {
        if(numOfAccounts()==4 && numOfAccounts()!=-1){
            this.view.showMessage("Max accounts already reached!");
        }

        if(accountExists(this.view.getAccountName())){
            this.view.showMessage("Account already exists");
            return;
        }
        if(this.view.formValidate()) {
            ((Button) view).setClickable(false);
            HashMap<String, String> map = new HashMap<>();
            map.put("name", this.view.getAccountName());
            map.put("main", Boolean.toString(this.view.isMain()));
            map.put("userID", intent.getStringExtra("ID"));
            map.put("currency", this.view.getCurrencyType().substring(0, 3));
            map.put("balance", Double.toString(this.view.getBalance()));
            map.put("type", this.view.getAccountType());
            services.addAccount(map, this);
        }
    }

    @Override
    public void handleBack(View view) {
        this.view.goBack();
    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {
        if(code==9){
            accounts = (ArrayList<Account>) data;
            System.out.println(accounts);
        }else{
            this.view.submitted();
        }

    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }

    //helper function()
    public boolean accountExists(String accountName){
        for(Account account : accounts){
            if(account.getName().equals(accountName)) return  true;
        }
        return false;
    }

    public int numOfAccounts(){
        try {
            return accounts.size();
        }catch (Exception e){

        }
        return -1;
    }
}
