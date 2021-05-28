package com.example.agilesavev2.views.saving_goals;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.agilesavev2.R;
import com.example.agilesavev2.assets.NavBarController.NavBarController;
import com.example.agilesavev2.databinding.ActivitySavingGoalsBinding;
import com.example.agilesavev2.fragments.budgetElement.BudgetDialog;
import com.example.agilesavev2.fragments.budgetElement.BudgetFragment;
import com.example.agilesavev2.fragments.savingElement.SavingDialog;
import com.example.agilesavev2.fragments.savingElement.SavingFragment;
import com.example.agilesavev2.models.saving_goal.SavingsGoal;
import com.example.agilesavev2.utils.PlusButtonAnimation;
import com.example.agilesavev2.views.saving_goals.addSavingGoal.AddSavingActivity;

import java.util.List;


//Syedur
public class SavingGoalsActivity extends AppCompatActivity implements SavingGoalsContract.SavingGoalView, SavingDialog.DialogListener{
    private DrawerLayout drawer;
    private SavingGoalsPresenter presenter;
    private boolean showReached;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySavingGoalsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_saving_goals);
        setTitle("");
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.draw_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_close,R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavBarController navBarController = new NavBarController(this,drawer,getIntent());
        //presenter handle
        presenter = new SavingGoalsPresenter(getIntent(),this, this);
        binding.setPresenter(presenter);//setting presenter handle to binding

        // animation cosmetics
        LinearLayout reachedUnderline = findViewById(R.id.saving_goal_reached_underline);
        reachedUnderline.animate().scaleX(0).setDuration(0);

        // on initialise, get savings
        presenter.getSavings();

        // logic
        showReached = false;
        LinearLayout setActive = findViewById(R.id.saving_goals_reached_active_clickable);
        setActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // cosmetics
                LinearLayout underline_active = findViewById(R.id.saving_goal_active_underline);
                underline_active.animate().scaleX(1).setDuration(500);

                LinearLayout underline_reached = findViewById(R.id.saving_goal_reached_underline);
                underline_reached.animate().scaleX(0).setDuration(500);

                // logic
                showReached=false;
                presenter.getSavings();

                // you see this part?
                // that's not what I'm trying to.
                // so far, whenever you save a savings record to the backend, the activity does not immediately call these two functions
                // yes
                // someone keeps messaging me on whatsapp, if it's cider then im going to kill him. good. that fine.
                // let me show you something else.
            }
        });


        LinearLayout setReached = findViewById(R.id.saving_goals_reached_reached_clickable);
        setReached.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // cosmetics
                LinearLayout underline_active = findViewById(R.id.saving_goal_active_underline);
                underline_active.animate().scaleX(0).setDuration(500);

                LinearLayout underline_reached = findViewById(R.id.saving_goal_reached_underline);
                underline_reached.animate().scaleX(1).setDuration(500);


                showReached=true;
                presenter.getSavings();

            }
        });

        new PlusButtonAnimation((LinearLayout)findViewById(R.id.hint));





    };


    @Override
    public void addSavingGoal(){
        Intent intent = new Intent(this, AddSavingActivity.class);
        intent.putExtra("ID",getIntent().getStringExtra("ID"));
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            presenter.getSavings();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void listSavings(List<SavingsGoal> data){

        // here, check this out

        // on receiving data from the backend, fragments get first removed from main view
        System.out.println("starting clearing fragments!");
        clearFragments();
        System.out.println("ending clearing fragments!!");

        LinearLayout linearLayout = findViewById(R.id.fragment_container);
        linearLayout.removeAllViews();
        // let me show you what part of my code is responsible for getting rid ofold fragmentwnfo
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // and afterwards, this code gets executed, which populates fragment container
        // so why is this not working correctly?
        for(SavingsGoal savings : data){
            boolean reachedGoal = savings.isReached();
            if (showReached && reachedGoal || (!showReached && !reachedGoal)){
                System.out.println("RENDERING FRAGMENTS!");
                fragmentTransaction.addToBackStack(null);
                SavingFragment savingFragment = new SavingFragment();
                // removes unnecessary time digits from received date
                String targetDateFormatted = savings.getTargetdate().substring(0, 10);
                savingFragment.setTitle(savings.getGoalname());
                savingFragment.setTarget(Double.toString( Math.rint(savings.getTargetGoal())));
                savingFragment.setTargetDate(targetDateFormatted);
                savingFragment.setSavingsID(Integer.toString( savings.getId()));
                savingFragment.setSaved(Double.toString( Math.rint(savings.getProgress())));
                savingFragment.setRemaining(Double.toString( Math.rint(savings.getTargetGoal()) - Math.rint(savings.getProgress())));
                // this should fix it
                savingFragment.setProgressPercentage(Double.toString( Math.min( Math.rint(( (savings.getProgress()/savings.getTargetGoal()) * 100)), 100) ));
                fragmentTransaction.add(linearLayout.getId(), savingFragment, "HELLO");
            }
        }
        fragmentTransaction.commit();
    }

    @Override
    public void showSavingGoals() {
    }

    @Override
    public void showCategories() {
    }

    @Override
    public void showSavings() {
    }

    @Override
    public void showActive() {
    }

    @Override
    public void showReached() {
    }

    public void clearFragments(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.fragment_container);
        linearLayout.removeAllViews();
        linearLayout.destroyDrawingCache();

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public Intent getIntentData() {
        return getIntent();
    }

    @Override
    public String getUserID() {
        return getIntent().getStringExtra("ID");
    }


    @Override
    public void applyChanges(String savingID, String savingTargetGoal, String savingCurrent, String savingTargetDate) {
        presenter.updateSavings(savingID, savingTargetGoal, savingCurrent, savingTargetDate);
    }

    @Override
    public void delete(String savingID) {
        presenter.deleteSavings(savingID);
    }
}