package com.example.agilesavev2.views.settings.manage_user_account;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.users.Account;
import com.example.agilesavev2.models.users.User;
import com.example.agilesavev2.services.Services;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageUserAccountPresenter implements ManageUserAccountContract.UserAccountPagePresenter, Presenter {
    private ManageUserAccountContract.UserAccountPageView view;
    private Services services;
    private Intent intent;
    private User user;
    private Context context;
    private ArrayList<Account> accounts;

    public ManageUserAccountPresenter(Intent intent, ManageUserAccountContract.UserAccountPageView view, Context context ) {
        this.view = view;
        this.intent = intent;
        this.context = context;

        services = new Services(this, this.view.getContext());
        services.getUserData(intent.getStringExtra("ID"), this);

    }
    @Override
    public void handleGoBack(View view) {
        this.view.goBack();
    }
    @Override
    public void handleSubmit(View view) {

        String email = this.view.getEmail();
        String currencyType = this.view.getCurrencyType();
        String id = intent.getStringExtra("ID");

        if (user!=null) {
            HashMap<String, String> map = new HashMap<>();

            map.put("id", id);
            map.put("firstName", user.getFirstname());
            map.put("surName", user.getSurname());
            map.put("DOB", "0000-00-00");
            map.put("email", email);
            map.put("balance", user.getBalance());
            map.put("main_currency",currencyType.split(" ")[0]);

            System.out.println();//wait one moment
            services.updateUser(map, this);
        }

    }

    public void handleUserData(User userData){
        this.view.updateFields(userData);
    }
    @Override
    public void handleDelete(View view) {

        String id = intent.getStringExtra("ID");
        HashMap<String, String> map = new HashMap<>();
        map.put("userID", id);
        services.deleteUser(map, this);


    }

    @Override
    public <T> void handleOnSuccess(int code,T data) {
        if (code==1){
            User userData = (User)data;
            if (userData !=null){
                user = userData;
                handleUserData(userData);
                System.out.println(userData.toString());
                System.out.println("Received user data");
            }
        }
        if(code==2){
            User userData = (User)data;
            this.view.changeCurrency();
            this.view.showMessage("Your user profile has been updated");
            this.view.showMessage("Main currency updated (may need to re login to see changes)");
        }
        if(code==3){
            this.view.showMessage("Your user profile has been deleted");
            this.view.sendToMainPage();
        }
        if(code==9){
            accounts = (ArrayList<Account>) data;
        }
        if(code==15){
            this.view.showMessage("Account has been deleted");
            this.view.showMenuPage();
        }
    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }


}