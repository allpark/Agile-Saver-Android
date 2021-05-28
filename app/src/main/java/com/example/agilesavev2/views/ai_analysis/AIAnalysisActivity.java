package com.example.agilesavev2.views.ai_analysis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.Fragment;
import com.example.agilesavev2.assets.NavBarController.NavBarController;
import com.example.agilesavev2.databinding.ActivityAIAnalysisBinding;

import java.util.ArrayList;

//import com.example.agilesavev2.fragments.pie_chart.PieChartFragment;


public class AIAnalysisActivity extends AppCompatActivity implements AIAnalysisContract.iAIView , com.example.agilesavev2.assets.View {

    private DrawerLayout drawer;
    private AIAnalysisPresenter presenter;
    private String selectedValue;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setTitle("");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        ActivityAIAnalysisBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_a_i_analysis);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.draw_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_close, R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavBarController navBarController = new NavBarController(this,drawer,getIntent());
        presenter = new AIAnalysisPresenter(getIntent(),this);
        binding.setPresenter(presenter);

    }


    @Override
    public void displayActivity() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void restartUI() {
        finish();
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public void refreshFragments() {
        for(Fragment fragment : fragments){
            fragment.refreshFragment();
        }
    }

    @Override
    public void setAdapter(Fragment fragment) {
        if(fragments==null){
            fragments = new ArrayList<>();
        }
        fragments.add(fragment);
    }

    @Override
    public int getUserID() {
        return Integer.parseInt(getIntent().getStringExtra("ID"));
    }

    @Override
    public Context getContext() {
        return this;
    }
}
