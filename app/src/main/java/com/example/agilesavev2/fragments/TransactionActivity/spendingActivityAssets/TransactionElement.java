package com.example.agilesavev2.fragments.TransactionActivity.spendingActivityAssets;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agilesavev2.R;
import com.example.agilesavev2.utils.CurrencyConvertion;

import java.util.Currency;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionElement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionElement extends Fragment {
    private String  accountName, date, money,name, category, transactionValue, currenySymbol, type;
    private ImageView icon;
    public TransactionElement() {
        // Required empty public constructor
    }

    public TransactionElement(String name, String category,String accountName,String date, String transactionValue, String type, String currency) {
        // Required empty public constructor
        this.name = name;
        this.category=category;
        this.accountName = accountName;
        this.date=date.substring(0,10).replaceAll("-","/");
        this.type=type;
        this.transactionValue =  Double.toString(CurrencyConvertion.getConvertedCurrency(currency,Double.parseDouble(transactionValue)));

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spending_element, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView name = view.findViewById(R.id.name);
        TextView category = view.findViewById(R.id.category);
        TextView accountName = view.findViewById(R.id.account);
        TextView date = view.findViewById(R.id.date);
        TextView transaction = view.findViewById(R.id.transaction_value);
        icon = view.findViewById(R.id.icon);
        name.setText(this.name);
        category.setText(this.category);
        accountName.setText(this.accountName);
        date.setText(this.date);


        String symbol = Currency.getInstance(getActivity().getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();
        if(type.equals("EXPENSE")){
            transaction.setTextColor(getResources().getColor(R.color.lightRed));
            transaction.setText("-"+symbol+transactionValue);
            icon.setImageResource(R.drawable.devaluation);
        }else{
            transaction.setTextColor(getResources().getColor(R.color.green2));
            transaction.setText(symbol+transactionValue);
            icon.setImageResource(R.drawable.profit);
        }


    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(String transactionValue) {
        this.transactionValue = transactionValue;
    }

    public String getCurrenySymbol() {
        return currenySymbol;
    }

    public void setCurrenySymbol(String currenySymbol) {
        this.currenySymbol = currenySymbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCurrencySymbol(String currencyCode){
        if(currencyCode.equals("undefined")) return;
        Currency currency = Currency.getInstance(currencyCode);
        currenySymbol = currency.getSymbol();
    }

    public void setTransactionType(String type){
        this.type = type;
    }


}