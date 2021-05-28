package com.example.agilesavev2.fragments.TopGoals;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.agilesavev2.R;
import com.example.agilesavev2.fragments.saving_goals.SavingGoal;
import com.example.agilesavev2.models.saving_goal.SavingsGoal;
import com.example.agilesavev2.views.current_balance.CurrentBalanceActivity;
import com.example.agilesavev2.views.home.HomeActivity;
import com.example.agilesavev2.views.saving_goals.SavingGoalsActivity;

import java.util.Currency;


public class TopGoalFragment extends Fragment implements TopGoalContract.TopGoalView {
    private TopGoalPresenter presenter;
    private ProgressBar progressBar;
    private TextView goalName, saved,target,targetDate,percentage;
    private String symbol;
    public TopGoalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_goal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new TopGoalPresenter(this);
        TextView showMoreButton = view.findViewById(R.id.showMore);
        goalName = view.findViewById(R.id.goalName);
        saved = view.findViewById(R.id.saved);
        target = view.findViewById(R.id.target);
        targetDate = view.findViewById(R.id.target_date);
        percentage = view.findViewById(R.id.percentage);
        progressBar = view.findViewById(R.id.progress_circular);
        presenter.handleShowMore(showMoreButton);
        symbol = Currency.getInstance(getActivity().getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();
        presenter.getTopGoal();



    }

    @Override
    public void parseTopGoal(SavingsGoal topGoal) {
        String percentage = Math.round(topGoal.getProgress() * (100/topGoal.getTargetGoal()))+"%";
        this.goalName.setText(topGoal.getGoalname());
        this.percentage.setText(percentage);
        this.saved.setText(symbol+topGoal.getProgress());
        this.target.setText(symbol+topGoal.getTargetGoal());
        this.targetDate.setText(topGoal.getTargetdate().substring(0,10));
        this.progressBar.setMax((int)topGoal.getTargetGoal());
        this.progressBar.setProgress((int)topGoal.getProgress());
    }

    @Override
    public void showMore() {
        getActivity().getIntent().setClass(getContext(), SavingGoalsActivity.class);
        startActivity(getActivity().getIntent());
    }

    @Override
    public Context getContextData() {
        return getContext();
    }

    @Override
    public String getUserID() {
        return getActivity().getIntent().getStringExtra("ID");
    }

    @Override
    public void hideTopGoal() {
        ((HomeActivity)getActivity()).hideTopGoal();
    }
}