package com.example.agilesavev2.views.settings.user_feedback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.agilesavev2.R;

public class UserFeedBackActivity extends AppCompatActivity implements UserFeedBackContract.UserFeedBackView {
    private UserFeedBackPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_user_feed_back);
        presenter = new UserFeedBackPresenter(this);
    }

    @Override
    public void goBack() {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public Intent getIntentData() {
        return null;
    }

    @Override
    public String getUserID() {
        return null;
    }

    @Override
    public void showMessage() {

    }

    @Override
    public boolean formValidation() {
        return false;
    }
}