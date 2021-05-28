package com.example.agilesavev2.fragments.savingElement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.agilesavev2.R;

import java.util.Currency;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavingFragment extends Fragment {

    private String title, id, target,target_date, saved, remaining, progress_percentage, symbol;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_saving, container, false);
        symbol = Currency.getInstance(getActivity().getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();

        setupOnClickListener(v);
        return v;
    }

    public void setupOnClickListener(View v){

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SavingDialog savingDialog = new SavingDialog();
                savingDialog.setTitle(getTitle());
                savingDialog.setID(getSavingsID());
                savingDialog.setTargetGoal( getTarget().replace(symbol,""));
                savingDialog.setCurrentSavings(getSaved().replace(symbol,""));
                savingDialog.setTargetDate(getTargetDate());
                savingDialog.show(getChildFragmentManager(),"");


            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title, target,saved,remaining,target_date,progress_percentage;
        String symbol = Currency.getInstance(getActivity().getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();

        title= view.findViewById(R.id.saving_goal_title);
        target=view.findViewById(R.id.saving_goal_target);
        saved=view.findViewById(R.id.saving_goal_saved);
        remaining=view.findViewById(R.id.saving_goal_remaining);
        target_date=view.findViewById(R.id.saving_goal_target_date);
        progress_percentage=view.findViewById(R.id.saving_goal_progress_percentage);

        title.setText(this.title);
        target.setText(symbol+this.target);
        saved.setText(symbol+this.saved);
        remaining.setText(symbol+this.remaining);
        target_date.setText(this.target_date);
        progress_percentage.setText(this.progress_percentage);

        ProgressBar progressBar = view.findViewById(R.id.saving_goal_progress_bar);
        //progressBar.setMax((int) Double.parseDouble(this.target.replace("£","")));
        progressBar.setProgress((int)Double.parseDouble(this.progress_percentage.replace("%","")));

    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setTarget(String target) {
        this.target = target;
    }
    public void setTargetDate(String target_date) {
        this.target_date = target_date;
    }
    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }
    public void setSaved(String saved) {
        this.saved = saved;
    }

    public void setProgressPercentage(String progress_percentage) { this.progress_percentage = progress_percentage + "%"; }
    public void setSavingsID(String id) {
        this.id = id;
    }
    public String getSavingsID() {
        return id;
    }
    public String getTitle() { return title; }
    public String getTarget() { return target; }
    public String getTargetDate() { return target_date; }
    public String getRemaining() { return remaining; }
    public String getSaved() { return saved; }

}