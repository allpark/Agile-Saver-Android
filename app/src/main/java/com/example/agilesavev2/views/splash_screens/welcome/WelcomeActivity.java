package com.example.agilesavev2.views.splash_screens.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agilesavev2.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window g=getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
        ImageView logo = findViewById(R.id.welcome_logo);
        TextView welcomeMessage = findViewById(R.id.welcomeMessage);
        welcomeMessage.setAlpha(0);
        logo.setY(logo.getY()-300);
        logo.animate().translationY(logo.getY()+500).setDuration(1000);
        logo.animate().rotation(720).alpha(1).setDuration(1500);
        welcomeMessage.animate().alpha(1).setDuration(1000);
        new Handler().postDelayed(new Runnable() {
            public void run() {

                finish();

            }
        }, 4000);


    }
}