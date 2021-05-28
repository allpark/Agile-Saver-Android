package com.example.agilesavev2.views.settings.user_feedback;

import android.view.View;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.services.Services;

public class UserFeedBackPresenter implements UserFeedBackContract.UserFeedBackPresenter , Presenter {
    private UserFeedBackContract.UserFeedBackView view;
    private Services services;

    public UserFeedBackPresenter(UserFeedBackContract.UserFeedBackView view) {
        this.view = view;
        services = new Services(this,view.getContext());
    }

    @Override
    public void handleGoBack(View view) {

    }

    @Override
    public void handleSubmit(View view) {

    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {

    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }
}
