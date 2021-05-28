package com.example.agilesavev2.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class DatePicker {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Intent intent;
    private Context context;
    public DatePicker(Button dateButton, Intent intent, Context context) {
        this.dateButton = dateButton;
        this.intent = intent;
        this.context=context;

        initDatePicker();
        if(dateButton.getHint()==null || dateButton.getHint().toString().trim().equals("")) {
            dateButton.setText(getTodayDate());
        }
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });
    }

    /**
     * DATE SECTION
     * ------------
     * The following functions are responsible for the logic of the date picker.
     * initDatePicker = it initialises a listener for the date picker, it returns the date, year, month in form of int selected by the user.
     * the dateButton text changes to the selected date.
     *
     * makeDateString = converts the date values of int month, int day, int year into a much more readable format e.g MAR 29 2021
     *
     * makeDateString = takes in month, and returns the relative month of that value. e.g 0 would be January, 3 would be April
     *
     * openDatePicker = this opens the dialogue for the user to select a date.
     *
     */

    private String getTodayDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month+1, year);
    }



    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int day) {
                month++;

                String date = makeDateString(day, month, year);
                    System.out.println("TEEEEEEES12");
                    dateButton.setText(date);



                // format date to iso format
                String dateAsStringISO = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);

                // save to intent
                if (intent != null){
                    intent.putExtra("DATE", dateAsStringISO);
                }
            }

        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(context, style, dateSetListener, year, month, day);


    }
    private String makeDateString(int day, int month, int year){
        return getMonthFormat(month)+" "+day+" "+year;
    }

    private String getMonthFormat(int month){
        if(month<0 || month>12) return null;
        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "NOV", "DEC"};
        return months[month-1];
    }

    public void openDatePicker(){
        datePickerDialog.show();
    }
    public static String parseDate(String date){
        return "";
    }
    public String getSelectedDate(){
        return dateButton.getText().toString();
    }

}
