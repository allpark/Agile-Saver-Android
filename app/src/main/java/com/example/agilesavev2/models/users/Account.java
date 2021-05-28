package com.example.agilesavev2.models.users;

public class Account {
    private int id, userid;
    private String currency,name, type;
    private double balance;
    private boolean main;

    public Account(int id, int userid, String currency,double balance, String name, String type, boolean main) {
        this.id = id;
        this.userid = userid;
        this.currency = currency;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.main=main;
    }

    public int getID() {
        return id;
    }

    public int getUserID() {
        return userid;
    }

    public String getCurrency() {
        return currency;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isMain(){
        return main;
    }


    public void setMain(boolean main) {
        this.main = main;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserID(int userID) {
        this.userid = userID;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }




    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userid=" + userid +
                ", currency='" + currency + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                '}';
    }
}
