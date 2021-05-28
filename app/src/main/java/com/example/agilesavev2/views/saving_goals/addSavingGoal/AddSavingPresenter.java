package com.example.agilesavev2.views.saving_goals.addSavingGoal;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.services.Services;
import com.example.agilesavev2.utils.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.HashMap;

import static java.security.AccessController.getContext;

public class AddSavingPresenter implements AddSavingContract.AddSavingPresenter, Presenter {
    AddSavingContract.AddSavingView view;
    Intent intent;
    Services services;
    public AddSavingPresenter(Intent intent , AddSavingContract.AddSavingView view, Context context) {
        this.view=view;
        services = new Services(this, context);
        this.intent=intent;

    }

    @Override
    public void handleAddSaving(View view) {

        if(this.view.formValidate()) {

            // test
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
            SimpleDateFormat fromDate = new SimpleDateFormat("yyyy-MM-dd");

            try{
                date = format.parse(this.view.getTargetDate() );
            }
            catch (Exception e){
                System.out.println("bad date format");
            }
        // uhhhh
            String stringDate= fromDate.format(date);

            // create hash map
            HashMap<String, String> map = new HashMap<>();

            map.put("userID", intent.getStringExtra("ID"));
            map.put("goalName", this.view.getName());
            map.put("targetDate", stringDate );
            map.put("startDate", stringDate );// stupid inequality
            map.put("reached", Boolean.toString(Double.parseDouble(this.view.getTargetAmount())<=Double.parseDouble(this.view.getCurrentAmount())) );
            map.put("achievedDate", stringDate);
            map.put("targetGoal", this.view.getTargetAmount());
            map.put("progress", this.view.getCurrentAmount());
            // technically the percentage label should get clamped, but ok
            services.addSavingGoal(map);
            this.view.returnResult();


        }

    }

    @Override
    public void handleBack(View view) {
        this.view.back();
    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {
        this.view.returnResult();
    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }
}
