package com.example.agilesavev2.views.my_budget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.agilesavev2.assets.NavBarController.NavBarController;
import com.example.agilesavev2.databinding.ActivityMyBudgetBinding;
import com.example.agilesavev2.fragments.budgetElement.BudgetDialog;
import com.example.agilesavev2.fragments.budgetElement.BudgetFragment;
import com.example.agilesavev2.models.Budgets.Budget;
import com.example.agilesavev2.utils.PlusButtonAnimation;
import com.example.agilesavev2.views.my_budget.addBudget.InsertNewbudgetActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.agilesavev2.R;

import java.text.ParseException;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

//Muneeb
public class MyBudgetActivity extends AppCompatActivity implements MyBudgetContract.MyBudgetView, BudgetDialog.DialogListener  {
    private DrawerLayout drawer;
    private MyBudgetPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ActivityMyBudgetBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_my_budget);
        presenter = new MyBudgetPresenter(getIntent(),this, this);
        binding.setPresenter(presenter);
        presenter.getBudgets();
        setTitle("");
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.draw_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_close,R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavBarController navBarController = new NavBarController(this,drawer,getIntent());
        this.presenter.getPayDate();

        new PlusButtonAnimation((LinearLayout)findViewById(R.id.hint));
    }



    @Override
    public void listBudgets(List<Budget> budgets) {
        LinearLayout linearLayout = findViewById(R.id.budgetList);
        linearLayout.removeAllViews();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        for(Budget budget : budgets){
            fragmentTransaction.addToBackStack(null);
            BudgetFragment budgetFragment = new BudgetFragment();
            budgetFragment.setName(budget.getName());
            budgetFragment.setCategory(budget.getCategory());
            budgetFragment.setTarget(getSymbol(),Double.toString(budget.getTarget()));
            budgetFragment.setSpent(getSymbol(),Double.toString(budget.getProgress()));
            budgetFragment.setLeft(getSymbol(),Double.toString(budget.getTarget() - budget.getProgress() ));
            budgetFragment.setId(Integer.toString(budget.getId()));
            fragmentTransaction.add(linearLayout.getId(), budgetFragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void addBudget() {
        Intent intent = new Intent(this, InsertNewbudgetActivity.class);
        intent.putExtra("ID",getIntent().getStringExtra("ID"));
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            finish();
            startActivity(getIntent());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void showPayDay(String date, String type) {

        try {
            String[] dataArr = date.split(" ");
            String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG","SEP", "OCT", "NOV", "DEC"};
            LocalDateTime date1,date2;
            if(Arrays.asList(months).contains(dataArr[0])) {
                String month = (Arrays.asList(months).indexOf(dataArr[0])+1)<10? "0"+(Arrays.asList(months).indexOf(dataArr[0])+1) : Integer.toString(Arrays.asList(months).indexOf(dataArr[0])+1);
                String day = dataArr[1].length() < 2 ? "0" + dataArr[1] : dataArr[1];
                String year = dataArr[2];
                date1 = LocalDateTime.parse(year + "-" + month + "-" + day + "T00:00");
                date2 = LocalDateTime.now();
            }else{
                date1 = LocalDateTime.parse(date.replace("Z",""));
                date2 = LocalDateTime.now();
            }
            long daysBetween = Duration.between(date2, date1 ).toDays();
            if(daysBetween==0){
                ((TextView)findViewById(R.id.days)).setText("TODAY");
                return;
            }
            else if(daysBetween<0){
                switch (type){
                    case "weekly":

                        while(daysBetween<0){
                            date1=date1.plusDays(7);
                            daysBetween = Duration.between(date2, date1 ).toDays();
                        }
                        presenter.updatePayDayDate(date1.toString());
                        break;
                    case "biweekly":

                        while(daysBetween<0){
                            date1=date1.plusDays(14);
                            daysBetween = Duration.between(date2, date1 ).toDays();
                        }
                        presenter.updatePayDayDate(date1.toString());
                        break;
                    case "monthly" :
                        while(daysBetween<0){
                            date1=date1.plusDays(31);
                            daysBetween = Duration.between(date2, date1 ).toDays();
                        }
                        presenter.updatePayDayDate(date1.toString());
                        break;
                    case "bimonthly" :
                        while(daysBetween<0){
                            date1=date1.plusDays(61);
                            daysBetween = Duration.between(date2, date1 ).toDays();
                        }
                        presenter.updatePayDayDate(date1.toString());

                        break;

                }

            }
            ((TextView)findViewById(R.id.days)).setText(Long.toString( daysBetween));


        }catch (DateTimeException e){
            System.out.println("Something went wrong "+e.getMessage());
        }


    }

    @Override
    public void hidePayDayCounter() {
        ((LinearLayout) (findViewById(R.id.daysTillPay))).removeAllViews();
        ((LinearLayout) (findViewById(R.id.daysTillPay))).setVisibility(View.INVISIBLE);
    }



    @Override
    public String getSymbol(){
        return Currency.getInstance(getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();
    }

    @Override
    public String getUserID() {
        return getIntent().getStringExtra("ID");
    }

    @Override
    public void applyChanges(HashMap<String, String> map) {
        presenter.updateBudget(map);
    }

    @Override
    public void delete(String budgetName) {
        presenter.deleteBudget(budgetName);
    }
}