package com.example.agilesavev2.views.category;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivityCategoryBinding;
import com.example.agilesavev2.fragments.category.CategoryFragment;
import com.example.agilesavev2.models.transactions.Category;
import com.example.agilesavev2.utils.PlusButtonAnimation;
import com.example.agilesavev2.views.settings.add_category.AddCategoryActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements CategoryContract.CategoryView {
    private ListView listView;
    private CategoryPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ActivityCategoryBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        getSupportActionBar().hide();
        listView= findViewById(R.id.category);
        presenter = new CategoryPresenter(this);
        binding.setPresenter(presenter);
        presenter.getCategory();

        new PlusButtonAnimation((LinearLayout)findViewById(R.id.hint));
    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public void showCategory(ArrayList<Category> categories) {
        LinearLayout linearLayout = findViewById(R.id.categoryContainer);
        linearLayout.removeAllViews();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for(Category category : categories){
            CategoryFragment categoryFragment = new CategoryFragment(category.getCategoryName());
            transaction.add(linearLayout.getId(),categoryFragment);
        }
        transaction.commit();
    }

    @Override
    public void returnSelectedCategory(String cat) {
        Intent intent = new Intent();
        intent.putExtra("CATEGORY",cat);
        setResult(RESULT_OK,intent);
        finish();
    }



    @Override
    public Context getContextData() {
        return this;
    }

    @Override
    public void showAddCategory() {
        getIntent().setClass(this, AddCategoryActivity.class);
        startActivityForResult(getIntent(),1);
    }

    @Override
    public String getUserID() {
        return getIntent().getStringExtra("ID");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            presenter.getCategory();
        }
    }
}