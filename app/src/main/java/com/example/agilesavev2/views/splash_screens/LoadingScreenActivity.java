package com.example.agilesavev2.views.splash_screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.agilesavev2.R;

public class LoadingScreenActivity extends AppCompatActivity {
    private static LoadingScreenActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.activity_loading_screen);
        getSupportActionBar().hide();
        activity = this;
    }
    public static LoadingScreenActivity getInstance(){
        return activity;
    }

}