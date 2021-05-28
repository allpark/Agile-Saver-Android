package com.example.agilesavev2.views.splash_screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agilesavev2.R;
import com.example.agilesavev2.views.main.MainActivity;

public class OpenAppActivityMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window g=getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.activity_open_app_main);
        System.out.println("test test");
        new Handler().postDelayed(new Runnable() {
            public void run() {

                Intent loginPage = new Intent(OpenAppActivityMainActivity.this, MainActivity.class);
                startActivity(loginPage);
                finish();
            }
        }, 3000);
        splashScreen();



    }

    public void splashScreen(){
        getSupportActionBar().hide();
        ImageView logo = findViewById(R.id.open_logo);
        TextView agile = findViewById(R.id.open_agile);
        TextView saver = findViewById(R.id.open_saver);

        logo.setY(logo.getY()-400);
        logo.setScaleX(0f);
        logo.setScaleY(0f);
        agile.setX(agile.getX()-1000);
        saver.setX(saver.getX()+1000);

        logo.animate().rotation(1080).translationYBy(400).scaleX(1).scaleY(1).setDuration(2000);
        agile.animate().translationXBy(1000).setDuration(2500);
        saver.animate().translationXBy(-1000).setDuration(2500);

    }
}