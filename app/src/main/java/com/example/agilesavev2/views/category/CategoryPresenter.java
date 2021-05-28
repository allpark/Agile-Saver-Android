package com.example.agilesavev2.views.category;

import android.content.ClipData;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.models.transactions.Category;
import com.example.agilesavev2.services.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryPresenter implements CategoryContract.CategoryPresenter, Presenter {
    private CategoryContract.CategoryView view;
    private Services services;
   // private ArrayList<String> categories;
    private ArrayList<Category> categories;


    public CategoryPresenter(CategoryContract.CategoryView view) {
        this.view = view;
        services = new Services(this, view.getContextData());
        categories = new ArrayList<>();



        categories.add(new Category("Food & Drinks"));
        categories.add(new Category("Shopping"));
        categories.add(new Category("Housing"));
        categories.add(new Category("Transportation"));
        categories.add(new Category("Vehicle"));
        categories.add(new Category("Life & Entertainment"));
        categories.add(new Category("Communication, PC"));
        categories.add(new Category("Financial expense"));
        categories.add(new Category("Investment"));
        categories.add(new Category("Income"));








    }

    @Override
    public void getCategory() {
        this.services.getCategories(view.getUserID());
      //  this.view.showCategory(categories);
    }

    @Override
    public void handleBack(View view) {
        this.view.goBack();
    }

    @Override
    public void handleAddCategory(View view) {
        this.view.showAddCategory();
    }


    @Override
    public <T> void handleOnSuccess(int code, T data) {
        ArrayList<Category> categories = (ArrayList<Category>) data;
        this.categories.addAll(categories);
        System.out.println("TEST "+this.categories);
        this.view.showCategory(this.categories);

    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }
}
