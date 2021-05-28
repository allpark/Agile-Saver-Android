package com.example.agilesavev2.models.transactions;

import android.graphics.Color;

public class Category {
    private int id, userid;
    private String categoryname;

    public Category(int id, int userID, String categoryName) {
        this.id = id;
        this.userid = userID;
        this.categoryname = categoryName;
    }

    public Category(String categoryName) {
        this.categoryname = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userid;
    }

    public void setUserID(int userID) {
        this.userid = userID;
    }

    public String getCategoryName() {
        return categoryname;
    }

    public void setCategoryName(String categoryName) {
        this.categoryname = categoryName;
    }


    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", userID=" + userid +
                ", categoryName='" + categoryname + '\'' +
                '}';
    }
}
