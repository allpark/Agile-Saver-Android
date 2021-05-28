package com.example.agilesavev2.models.transactions;

public class Transaction {
    private int id, userid, accountid;
    private double transaction;
    private double balance;
    private String type ,currency , category, subcategory ,name , date;

    public Transaction(int id, int userid, int accountid, double transaction, String type,String currency,String subcategory, String date, String name, String category,   double balance) {
        this.id = id;
        this.userid = userid;
        this.accountid = accountid;
        this.transaction = transaction;
        this.balance = balance;
        this.type = type;
        this.currency = currency;
        this.category = category;
        this.subcategory = subcategory;
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getUserID() {
        return userid ;
    }

    public int getAccountID() {
        return accountid;
    }

    public double getTransaction() {
        return transaction ;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getSubCategory() {
        return subcategory;
    }

    public String getType() {
        return type;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserID(int userID) {
        this.userid = userID;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public void setTransaction(double transaction) {
        this.transaction = transaction;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", userid=" + userid +
                ", accountid=" + accountid +
                ", transaction=" + transaction +
                ", balance=" + balance +
                ", type='" + type + '\'' +
                ", currency='" + currency + '\'' +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
