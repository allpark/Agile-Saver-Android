package com.example.agilesavev2.fragments.record;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.agilesavev2.R;

import java.util.Currency;

public class RecordFragment extends Fragment {
    private String name, category, currencyType, type, year, month, day, week;
    private double transactionValue;
    private int iconColour;
    public RecordFragment() {
        // Required empty public constructor
    }
    public RecordFragment(String name, String category,String currencyType,String type, int colour, double transactionValue) {
        // Required empty public constructor
        this.name=name;
        this.category=category;
        this.transactionValue=transactionValue;
        this.currencyType= currencyType;
        this.type=type;
        this.iconColour = colour;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView name = view.findViewById(R.id.transaction_name);
        TextView category = view.findViewById(R.id.transaction_category);
        TextView value = view.findViewById(R.id.transaction_value);
        TextView date1 = view.findViewById(R.id.date1);
        TextView date2 = view.findViewById(R.id.date2);
        LinearLayout icon = view.findViewById(R.id.transaction_icon);
        name.setText(this.name);
        category.setText(this.category);
        icon.setBackgroundColor(this.iconColour);
        String symbol = Currency.getInstance(getActivity().getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();

        if(this.type.equals("EXPENSE")){
            value.setTextColor(Color.RED);
            value.setText("-"+symbol+Double.toString(this.transactionValue));
        }else{
            value.setTextColor(Color.GREEN);
            value.setText(symbol+Double.toString(this.transactionValue));
        }



        if(month!=null){
            date1.setText(month+" "+year);
        }else{
            date1.setVisibility(View.INVISIBLE);
            date1.setHeight(0);
        }
        if(week!=null){
            date2.setText(week+" "+day);
        }else{
            date2.setVisibility(View.INVISIBLE);
            date2.setHeight(0);
        }

    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}