package com.example.agilesavev2.views.settings.add_category;

import android.view.View;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.services.Services;

import java.util.HashMap;

public class AddCategoryPresenter implements AddCategoryContract.AddCategoryPresenter , Presenter {
    private AddCategoryContract.AddCategoryView view;
    private Services services;
    public AddCategoryPresenter(AddCategoryContract.AddCategoryView view) {
        this.view = view;
        services = new Services(this, view.getContextData());
    }

    @Override
    public void handleGoBack(View view) {
        this.view.goBack();
    }

    @Override
    public void handleSubmit(View view) {
        if(this.view.formValidation()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("userID", this.view.getUserID());
            map.put("categoryName", this.view.getCategoryName());
            this.services.addCategory(map);
        }
    }

    @Override
    public <T> void handleOnSuccess(int code, T data) {
        if(code==16){
            this.view.showMessage("Category has been added!");
        }
    }

    @Override
    public void handleOnReject() {

    }

    @Override
    public void handleOnReject(int code) {

    }
}
