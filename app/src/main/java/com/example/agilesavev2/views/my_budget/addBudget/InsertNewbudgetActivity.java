package com.example.agilesavev2.views.my_budget.addBudget;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivityInsertNewbudgetBinding;
import com.example.agilesavev2.views.category.CategoryActivity;

public class InsertNewbudgetActivity extends AppCompatActivity implements InsertNewBudgetContract.InsertBudgetView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_insert_newbudget);
        ActivityInsertNewbudgetBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_insert_newbudget);
        InsertNewBudgetPresenter presenter = new InsertNewBudgetPresenter(getIntent(), this, this);
        binding.setPresenter(presenter);
    }


    @Override
    public void back() {
        finish();
    }

    @Override
    public String getName() {
        EditText name = findViewById(R.id.name);
        return name.getText().toString();
    }

    @Override
    public String getTarget() {
        EditText target = findViewById(R.id.target);
        return target.getText().toString();
    }

    @Override
    public String getSelectedCategory() {
        return ((Button)findViewById(R.id.categorySelector)).getText().toString();
    }

    @Override
    public void returnResult() {
        setResult(RESULT_OK);
        Toast.makeText(this, "Budget added!", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void showCategory() {
        getIntent().setClass(this, CategoryActivity.class);
        startActivityForResult(getIntent(),1);
    }

    @Override
    public boolean formValidate() {
        EditText name = findViewById(R.id.name);
        EditText target = findViewById(R.id.target);
        Button category = findViewById(R.id.categorySelector);
        if(name.getText().toString().trim().equals("")){
            Toast.makeText(this, "Please give budget a name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(target.getText().toString().trim().equals("")){
            Toast.makeText(this, "Please give budget a target", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(category.getText().toString().equals("CATEGORY")){
            Toast.makeText(this, "Please give budget a category ", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            ((Button)findViewById(R.id.categorySelector)).setText(data.getStringExtra("CATEGORY"));
        }

    }




}