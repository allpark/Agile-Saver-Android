package com.example.agilesavev2.models.transactions;

// lightweight transaction data model
public class TransactionSimple {
    private double transaction;
    private String type ,currency, date;

    public String getType() {
        return type;
    }
    public String getCurrency() {
        return currency;
    }
    public String getDate() {
        return date;
    }

    public double getTransaction() {
        return transaction;
    }

}
