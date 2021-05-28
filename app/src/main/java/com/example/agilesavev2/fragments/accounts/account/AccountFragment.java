package com.example.agilesavev2.fragments.accounts.account;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.agilesavev2.R;
import com.example.agilesavev2.fragments.accounts.BalanceListFragment;

import java.util.Currency;
import java.util.HashMap;

public class AccountFragment extends Fragment  {
    String accountName, balance, currencyType;
    View layout;
    BalanceListFragment parentView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View fragment = this.getView();
        TextView accountName = getView().findViewById(R.id.accountName);
        TextView accountBalance = getView().findViewById(R.id.balance);
        System.out.println(getCurrencyType());
        Currency currency = Currency.getInstance(getCurrencyType());

        accountName.setText(getAccountName());

        accountBalance.setText(formatMoney(currency.getSymbol(),Double.parseDouble(getBalance())));
        onclickEvent();


    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public void setParentView(BalanceListFragment view){
        parentView = view;
    }

    public BalanceListFragment getParentView(){
        return parentView;
    }

    public void setSelected(){
        if(getActivity().getIntent().getStringExtra("SELECTED")!=null && getActivity().getIntent().getStringExtra("SELECTED").equals(getAccountName())) {
            getView().findViewById(R.id.account).setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.agileSaverCyan2));
        }else{
            getView().findViewById(R.id.account).setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.agileSaverGray));
        }
    }


    public void onclickEvent(){
        this.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Integer> accountIDs =(HashMap<String,Integer>) getActivity().getIntent().getSerializableExtra("ACCOUNTS");
                getActivity().getIntent().putExtra("SELECTED",getAccountName());
                getActivity().getIntent().putExtra("SELECTED_ACCOUNT_ID",Integer.toString(accountIDs.get(getAccountName())));
                getParentView().refreshFragment();
//                ((com.example.agilesavev2.assets.View)getActivity()).refreshFragments();
            }
        });

    }

    //helper functions
    private String formatMoney(String currencySymbol,double val){
        if(val == (int)val){
            return currencySymbol+(int)val;
        }else{
            return currencySymbol+String.format("%.2f",val);
        }
    }

}