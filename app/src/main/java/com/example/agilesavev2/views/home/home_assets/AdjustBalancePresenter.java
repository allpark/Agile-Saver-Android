package com.example.agilesavev2.views.home.home_assets;

import android.content.Intent;
import android.view.View;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.users.User;
import com.example.agilesavev2.services.Services;
import com.example.agilesavev2.views.home.HomeActivity;

public class AdjustBalancePresenter implements Presenter {
    AdjustBalanceActivity view;
    Intent intent;
    Services services = new Services(this);

    public AdjustBalancePresenter(Intent intent,AdjustBalanceActivity view) {
        this.view = view;
        this.intent = intent;
    }

    public void handleOnSubmit(View view){
        //services.updateBalance(intent.getStringExtra("ID"),this.view.getInputBalance(), this);
    }


    public void handleOnSuccess(User userData) {

    }

    @Override
    public <T> void handleOnSuccess(int code,T data) {
        view.onSuccess();
    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }
}
