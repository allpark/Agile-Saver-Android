package com.example.agilesavev2.views.home;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.Observable;

import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.users.User;
import com.example.agilesavev2.services.Services;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HomePresenter implements HomeContract.iHomePresenter , Presenter{
    private HashMap<String, Map<String,Integer>> test;
    private HomeContract.iHomeView view;
    private Services services = new Services(this);
    private Intent intent;
    public HomePresenter(Intent intent,HomeContract.iHomeView view) {
        this.intent=intent;
        this.view = view;


        //test
        test = new HashMap<>();
        //category1

        Map<String,Integer> food= new HashMap<>();
        food.put("Groceries",10);
        food.put("Restaurant",20);
        food.put("Cafe",50);
        test.put("Food & Drinks",food);

        //category2
        Map<String,Integer> shopping= new HashMap<>();
        shopping.put("Clothes & shoes",10);
        shopping.put("Gifts",5);
        shopping.put("Tools",20);
        test.put("shopping",shopping);

        //category3
        Map<String,Integer> investments= new HashMap<>();
        investments.put("Collections",30);
        investments.put("Savings",40);
        investments.put("Financial investment",10);
        test.put("Investments",investments);
    }

    @Override
    public void getTopGoal() {

    }

    @Override
    public void getActivity() {

    }

    @Override
    public void getBalance() {

        //services.getUserData(intent.getStringExtra("ID"),this);
    }




    @Override
    public void handleAddRecord(View view) {
        this.view.showAddNewRecord();
    }

    @Override
    public void updateAccountbalance(HashMap<String, String> map) {
        services.updateAccountBalance(map,this);
    }

    @Override
    public void deleteAccount(HashMap<String, String> map) {
        services.deleteAccount(map);
    }

    @Override
    public <T> void handleOnSuccess(int code,T data) {

        this.view.restartUI();
    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }

}
