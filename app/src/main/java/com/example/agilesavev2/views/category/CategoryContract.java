package com.example.agilesavev2.views.category;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.example.agilesavev2.models.transactions.Category;

import java.util.ArrayList;
import java.util.List;

public interface CategoryContract {
    interface CategoryView{
        public void goBack();
        public void showCategory(ArrayList<Category> categories);
        public void returnSelectedCategory(String cat);
        public Context getContextData();
        public void showAddCategory();
        public String getUserID();
    }
    interface CategoryPresenter{
        public void getCategory();
        public void handleBack(View view);
        public void handleAddCategory(View view);

    }
}
