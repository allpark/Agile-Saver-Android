package com.example.agilesavev2.views.calculator;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.users.Account;
import com.example.agilesavev2.services.Services;

import java.util.ArrayList;
import java.util.HashMap;

public class CalculatorPresenter implements CalculatorContract.CalculatorPresenter, Presenter {
    private CalculatorContract.CalculatorView view;
    private Services services;
    private ArrayList<Account> accounts;

    private boolean result=false;
    private Double operand1=0.0;
    private Double operand2;
    private String operator;
    private String operationValue="";
    private boolean clearTextView=false;
    private boolean operationInProgress=false;
    private boolean error=false;
    private boolean decimal=false;
    private double decimalPoint;



    public CalculatorPresenter(CalculatorContract.CalculatorView view) {
        this.view = view;
        services = new Services(this, this.view.getContext());
        HashMap<String, String> map = new HashMap<>();
        map.put("userID", this.view.getUserId());
        services.getAccounts(map, this);
    }



    @Override
    public <T> void handleOnSuccess(int code,T data) {
        if(code==9){
            accounts = (ArrayList<Account>) data;
            return;
        }
        if(code==4){
            this.view.showNotification();
            this.view.save();
            return;
        }else{
            this.view.save();
            return;
        }



    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }


    @Override
    public void handleIncomeMode(View view) {
        this.view.incomeMode();
    }

    @Override
    public void handleExpenseMode(View view) {
        this.view.expenseMode();
    }

    @Override
    public void handleCategory(View view) {
        this.view.showCategory();
    }

    @Override
    public void handleNumbers(View view) {
        double value=  Double.parseDouble(((TextView) view).getText().toString());
        if(operationInProgress){
            System.out.println("if");

            if (operand2 == 0) {
                operand2 = value;
                decimal=false;
                decimalPoint=1;
            } else {
                operand2 = operand2 * 10.0 + value;
                decimalPoint+=1;

            }

            value = operand2;
        }else {
            System.out.println("else");
            if (operand1 == 0) {
                operand1 = value * (1.0/(Math.pow(10.0, decimalPoint)));
                decimalPoint+=1;
                System.out.println("93");
            } else {

                operand1 = decimal? operand1 + value*(1.0/(Math.pow(10.0, decimalPoint))) : operand1 * 10 + value;

                //Double.parseDouble(Double.toString(value) + Double.toString(operand1))
                System.out.println(value + " " + operand1);
                if (decimal){
                    decimalPoint+=1;
                }

            }

            value = operand1;
        }

        this.view.updateScreen(Double.toString(value));
    }

    @Override
    public void handleOperations(View view) {
        operationInProgress=true;
        if(view!=null){
            operator=((TextView)view).getText().toString();
        }

        switch (operator){
            case "+":
                if(operand2==null){
                    operand2=0.0;
                    operationInProgress=true;
                }else{
                    operand1+=operand2;
                    operand2=0.0;
                    this.view.updateScreen(Double.toString(operand1));
                }
                break;
            case "-":
                if(operand2==null){
                    operand2=0.0;
                    operationInProgress=true;
                }else{
                    operand1-=operand2;
                    operand2=0.0;
                    this.view.updateScreen(Double.toString(operand1));
                }
                break;
            case "/":
                if(operand2==null){
                    operand2=0.0;
                    operationInProgress=true;
                }else{
                    try{
                        operand1/=operand2;
                        operand2=0.0;
                        this.view.updateScreen(Double.toString(operand1));
                    }catch (Exception e){
                        this.view.updateScreen("ERROR");
                        error=true;
                    }


                }
                break;
            case "X":
                if(operand2==null){
                    operand2=0.0;
                    operationInProgress=true;
                }else{
                    operand1*=operand2;
                    operand2=0.0;
                    this.view.updateScreen(Double.toString(operand1));
                }
                break;
        }
    }

    @Override
    public void handleSubmit(View view) {
        System.out.println(operand1);
        System.out.println(operand2);
        handleOperations(null);
        operand2=null;
        operationInProgress=false;
        result=true;


    }

    @Override
    public void handleSave(View view) {


        Intent data = this.view.getTransactionData();
        if(this.view.getInputValue().equals("0") || this.view.getName().trim().equals("") || this.view.getSelectedAccountID()==null || data.getStringExtra("CATEGORY")==null){
            this.view.onReject();
            return;
        }
        else{
            Account account = getSelectedAccount(this.view.getSelectedAccountID());
            double transaction  = Double.parseDouble(data.getStringExtra("TRANSACTION"));
            HashMap<String,String> map = new HashMap<>();
            map.put("userID",data.getStringExtra("ID"));
            map.put("accountID",Integer.toString(account.getID()));
            map.put("accountName",account.getName());
            map.put("transaction",Double.toString(transaction));
            map.put("type",this.view.transactionType());
            map.put("category",data.getStringExtra("CATEGORY"));
            map.put("name",this.view.getName());
            map.put("date", this.view.getDateTime());

            System.out.println("TESTEST "+map);

            services.addTransaction(map,this);
            map.put("name",this.view.getName());
            services.sendTransactionData(map);

            if(account!=null){
                String newBalance ="";
                if(this.view.transactionType().equals("EXPENSE")){
                    newBalance = Double.toString(account.getBalance()-transaction);
                }else if(this.view.transactionType().equals("INCOME")){
                    newBalance = Double.toString(account.getBalance()+transaction);
                }

                HashMap<String, String> accountMap = new HashMap<>();
                accountMap.put("id",Integer.toString(account.getID()));
                accountMap.put("name",account.getName());
                accountMap.put("balance",newBalance);
                accountMap.put("userID",data.getStringExtra("ID"));
                services.updateAccountBalance(accountMap,this);

            }



        }


    }


    @Override
    public void handleCancel(View view) {
        this.view.cancel();
    }


    @Override
    public void handleDelete(View view) {

        if(result || error){
            this.view.updateScreen("0");
            operand1=0.0;
            result=false;
            operand2=null;
            operationInProgress=false;
            decimal=false;
            decimalPoint=0;
        }else{
            decimalPoint=0;
            decimal=false;

            double value;
            if(operationInProgress){
                value=operand2=Math.floor(operand2/10);
            }else{
                value = Math.floor(operand1=Math.floor(operand1/=10));
            }

            this.view.updateScreen(Double.toString(value));
        }

    }


    @Override
    public void handleDecimal(View view) {
        this.view.addDecimal();
        decimal=true;
        decimalPoint=1;
    }
    // this should work now
    @Override
    public void handleAccounts(View view) {
        this.view.showAccountList();
    }

    @Override
    public void handleTimePicker(View view) {
        this.view.showTimePicker();
    }

    @Override
    public String getCurrencyType() {
        for(Account account : accounts){
            if(account.getName().equals(view.getSelectedAccountName())){
                return account.getCurrency();
            }
        }
        return "";
    }

    //helper function
    private Account getSelectedAccount(String name){
        for(Account account :accounts){
            if(account.getName().equals(name)){
                return account;
            }
        }
        return null;
    }

    private Account getSelectedAccount(int id){
        for(Account account :accounts){
            if(account.getID()==(id)){
                return account;
            }
        }
        return null;
    }


}
