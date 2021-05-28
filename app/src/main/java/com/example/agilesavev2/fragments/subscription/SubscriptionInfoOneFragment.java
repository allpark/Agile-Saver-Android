package com.example.agilesavev2.fragments.subscription;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.agilesavev2.R;

import java.util.ArrayList;

public class SubscriptionInfoOneFragment extends Fragment {
    private Double percentage;
    private ArrayList<String> list;
    private String category;
    private TextView listSelected,categoryTv,percentageTv;
    public SubscriptionInfoOneFragment() {
        // Required empty public constructor
        percentage=0.0;
        list=new ArrayList<>();
        category="";

    }

    public SubscriptionInfoOneFragment(String category,Double percentage, ArrayList<String> list) {
        this.percentage=percentage;
        this.list=list;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscription_info_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listSelected = view.findViewById(R.id.list);
        categoryTv = view.findViewById(R.id.subscription_category);
        percentageTv = view.findViewById(R.id.subscription_percentage);
        String formatList ="";
        for(String item : list){
            formatList+="- "+item+"\n";
        }
        listSelected.setText(formatList);
        categoryTv.setText(this.category);
        percentageTv.setText(Math.round(this.percentage)+"%");
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
        if(percentageTv!=null) {
            percentageTv.setText(Math.round(this.percentage) + "%");
        }
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
        if(listSelected!=null) {
            String formatList = "";
            for (String item : list) {
                formatList += "- " + item + "\n";
            }
            listSelected.setText(formatList);
        }
    }

    public void setCategory(String category) {
        this.category = category;

        if(categoryTv!=null) {
            categoryTv.setText(this.category);
        }
    }
}