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


public class RecordCategoryFragment extends Fragment {
    private String category, type;
    private double transactionValue;
    private int iconColour;

    public RecordCategoryFragment() {
        // Required empty public constructor
    }

    public RecordCategoryFragment(String category, double transactionValue, int iconColour,String type){
        this.category=category;
        this.transactionValue=transactionValue;
        this.iconColour = iconColour;
        this.type = type;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView category = view.findViewById(R.id.transaction_category);
        TextView transactionValue = view.findViewById(R.id.transaction_value);
        LinearLayout icon = view.findViewById(R.id.transaction_icon);

        icon.setBackgroundColor(this.iconColour);



        category.setText(this.category);
        String symbol = Currency.getInstance(getActivity().getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();
        transactionValue.setText("-£"+Double.toString(this.transactionValue));
        if(this.type.equals("EXPENSE")){
            transactionValue.setText("-"+symbol+Double.toString(this.transactionValue));
        }else{
            transactionValue.setText(symbol+Double.toString(this.transactionValue));
        }
    }
}