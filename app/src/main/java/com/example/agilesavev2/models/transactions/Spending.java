package com.example.agilesavev2.models.transactions;

import java.time.LocalDate;

public class Spending {
    private int id, usrID;
    private double expense;
    private String category,subcategory,name, date;

    public int getId() {
        return id;
    }

    public int getUsrID() {
        return usrID;
    }

    public double getExpense() {
        return expense;
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
}
