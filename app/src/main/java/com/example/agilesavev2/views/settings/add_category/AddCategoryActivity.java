package com.example.agilesavev2.views.settings.add_category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivityAddCategoryBinding;

public class AddCategoryActivity extends AppCompatActivity implements AddCategoryContract.AddCategoryView{
    private AddCategoryPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        ActivityAddCategoryBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_category);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().hide();
        presenter = new AddCategoryPresenter(this);
        binding.setPresenter(presenter);
    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public boolean formValidation() {
        if(getCategoryName().trim().equals("")){
            showMessage("Enter a category name");
            return false;
        }
        return true;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getUserID() {
        return getIntent().getStringExtra("ID");
    }

    @Override
    public Context getContextData() {
        return this;
    }

    @Override
    public Intent getIntentData() {
        return getIntent();
    }

    @Override
    public String getCategoryName() {
        return ((EditText)findViewById(R.id.category_name)).getText().toString();
    }
}