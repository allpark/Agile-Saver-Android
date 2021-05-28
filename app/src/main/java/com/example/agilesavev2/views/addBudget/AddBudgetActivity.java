package com.example.agilesavev2.views.addBudget;

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
import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.databinding.ActivityAddBudgetBinding;
import com.example.agilesavev2.views.category.CategoryActivity;

public class AddBudgetActivity extends AppCompatActivity implements AddBudgetContract.AddBudgetView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ActivityAddBudgetBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_my_budget);
        AddBudgetPresenter presenter = new AddBudgetPresenter(getIntent(),this, this);
        binding.setPresenter(presenter);
    }

    @Override
    public void back() {

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            ((Button)findViewById(R.id.categorySelector)).setText(data.getStringExtra("SUBCATEGORY"));
        }
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
}