package com.example.agilesavev2.views.saving_goals.addSavingGoal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.databinding.ActivityAddBudgetBinding;
import com.example.agilesavev2.databinding.ActivityInsertNewbudgetBinding;
import com.example.agilesavev2.databinding.ActivitySavingGoalsBinding;
import com.example.agilesavev2.databinding.AddSavingGoalBinding;
import com.example.agilesavev2.utils.DatePicker;
import com.example.agilesavev2.views.addBudget.AddBudgetPresenter;
import com.example.agilesavev2.views.my_budget.addBudget.InsertNewBudgetPresenter;

public class AddSavingActivity extends AppCompatActivity implements AddSavingContract.AddSavingView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_saving_goal);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        AddSavingGoalBinding binding = DataBindingUtil.setContentView(this, R.layout.add_saving_goal);
        AddSavingPresenter presenter = new AddSavingPresenter(getIntent(), this, this);
        binding.setPresenter(presenter);

        DatePicker datePicker = new DatePicker(findViewById(R.id.target_date), getIntent(), this);

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
    public String getCurrentAmount() {
        EditText target = findViewById(R.id.current_amount);
        return target.getText().toString();
    }
    @Override
    public String getTargetAmount() {
        EditText target = findViewById(R.id.target_amount);
        return target.getText().toString();
    }
    @Override
    public String getTargetDate() {
        Button target = findViewById(R.id.target_date);
        return target.getText().toString();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean formValidate() {

        if(getName().trim().equals("")){
            showMessage("Enter a name");
            return false;
        }else if(getCurrentAmount().equals("")){
            showMessage("Enter a current amount");
            return false;
        }else if(getTargetAmount().trim().equals("")){
            showMessage("Enter a target amount");
            return false;
        }else if(getTargetDate().trim().equals("")){
            showMessage("Enter a target date");
            return false;
        }
        return true;
    }


    @Override
    public void returnResult() {
        setResult(RESULT_OK);
        Toast.makeText(this, "Budget added!", Toast.LENGTH_SHORT).show();
        finish();
    }
}