package com.example.agilesavev2.assets.NavBarController;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agilesavev2.R;
import com.example.agilesavev2.views.saving_goals.SavingGoalsActivity;
import com.example.agilesavev2.views.ai_analysis.AIAnalysisActivity;
import com.example.agilesavev2.views.current_balance.CurrentBalanceActivity;
import com.example.agilesavev2.views.main.MainActivity;
import com.example.agilesavev2.views.my_budget.MyBudgetActivity;
import com.example.agilesavev2.views.home.HomeActivity;
import com.example.agilesavev2.views.settings.SettingsPageActivity;
import com.example.agilesavev2.views.subscriptions.SubscriptionsActivity;

public class NavBarController extends AppCompatActivity implements NavBarInterface{
    private Intent intent;
    private View view;
    private Context packageContext;
    public NavBarController(Context context, View view, Intent intent) {
        this.intent = intent;
        this.view=view;
        this.packageContext=context;
        setUpNavBar();
    }



    public void setUpNavBar(){
        String name = intent.getStringExtra("NAME");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Runnable runnable = new Runnable() {
                    public void run() {
                        TextView tvUsername = view.findViewById(R.id.nav_username);
                        if (tvUsername != null) {
                            tvUsername.setText(name);
                            navBarOptionsListener();
                            return;
                        }

                    }
                };
                Handler handler = new android.os.Handler();
                handler.postDelayed(runnable, 1000);
            }
        });




    }

    public void navBarOptionsListener(){
        (view.findViewById(R.id.nav_budget)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyBudget();
            }
        });
        (view.findViewById(R.id.nav_home)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHome();
            }
        });
        (view.findViewById(R.id.nav_subscription)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSubscriptions();
            }
        });
        (view.findViewById(R.id.nav_savingGoals)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSavingGoals();
            }
        });
        (view.findViewById(R.id.nav_currentBalance)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCurrentBalance();
            }
        });
        (view.findViewById(R.id.nav_artificialIntelligence)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showArtificialIntelligence();
            }
        });
        (view.findViewById(R.id.nav_settings)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettings();
            }
        });
        (view.findViewById(R.id.nav_logOut)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }



    @Override
    public void showHome() {
        intent.setClass(packageContext, HomeActivity.class);
        packageContext.startActivity(intent);
    }

    @Override
    public void showMyBudget(){
//        (view.findViewById(R.id.nav_budget)).setBackgroundColor(Color.GRAY);
//        (view.findViewById(R.id.nav_budget)).setClickable(false);
        intent.setClass(packageContext,MyBudgetActivity.class);
        packageContext.startActivity(intent);
        ((Activity)packageContext).overridePendingTransition(0, 0);
    }


    public void showSubscriptions(){
        intent.setClass(packageContext, SubscriptionsActivity.class);
        packageContext.startActivity(intent);
        ((Activity)packageContext).overridePendingTransition(0, 0);
    }

    public void showSavingGoals(){
        intent.setClass(packageContext, SavingGoalsActivity.class);
        packageContext.startActivity(intent);
        ((Activity)packageContext).overridePendingTransition(0, 0);
    }

    public void showCurrentBalance(){
        intent.setClass(packageContext, CurrentBalanceActivity.class);
        packageContext.startActivity(intent);
        ((Activity)packageContext).overridePendingTransition(0, 0);
    }

    public void showArtificialIntelligence(){
        intent.setClass(packageContext, AIAnalysisActivity.class);
        packageContext.startActivity(intent);
        ((Activity)packageContext).overridePendingTransition(0, 0);
    }

    public void showSettings(){
        intent.setClass(packageContext, SettingsPageActivity.class);
        packageContext.startActivity(intent);
        ((Activity)packageContext).overridePendingTransition(0, 0);
    }

    @Override
    public void signOut() {
        Intent intent = new Intent(packageContext,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        packageContext.startActivity(intent);
        ((Activity)packageContext).finish();
        ((Activity)packageContext).overridePendingTransition(0, 0);
    }



}
