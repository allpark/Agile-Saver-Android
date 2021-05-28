package com.example.agilesavev2.fragments.budgetElement;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.agilesavev2.R;

import java.util.Currency;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetFragment extends Fragment {
    private String name,target,spent,left,id, symbol, category;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_budget, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView name, target,spent,left;
        name= view.findViewById(R.id.budgetName);
        target=view.findViewById(R.id.target);
        spent=view.findViewById(R.id.spent);
        left=view.findViewById(R.id.left);

        name.setText(this.name);
        target.setText(this.target);
        spent.setText(this.spent);
        left.setText(this.left);

        ProgressBar progressBar = view.findViewById(R.id.simpleProgressBar);
        symbol= Currency.getInstance(getActivity().getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();
        progressBar.setMax((int) Double.parseDouble(this.target.replace(symbol,"")));
        double spentVal = Double.parseDouble(this.spent.replace(symbol,""));
        double targetVale = Double.parseDouble(this.target.replace(symbol,""));

        double leftVal = Double.parseDouble(this.left.replace(symbol,""));

        if(leftVal<=0){
            left.setText(symbol+"0");
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
        else if(leftVal<(targetVale/3)){
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(	255, 165, 0)));
        }
        else if(leftVal<(targetVale/2)){
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        }
        progressBar.setProgress((int) spentVal);




        openDialog();

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTarget(String symbol,String target) {
        this.target = formatMoney(symbol,Double.parseDouble(target));
    }

    public void setSpent(String symbol, String spent) {
        this.spent = formatMoney(symbol,Double.parseDouble(spent));
    }

    public void setLeft(String symbol, String left) {
        this.left = formatMoney(symbol,Double.parseDouble(left));
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getTarget() {
        return target;
    }

    public String getSpent() {
        return spent;
    }

    public String getLeft() {
        return left;
    }

    public String getBudgetId() {
        return id;
    }

    public void openDialog(){
        getView().findViewById(R.id.budget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BudgetDialog budgetDialog = new BudgetDialog();
                budgetDialog.setTitle(getName());
                budgetDialog.setBudgetProgress(getSpent().replace(symbol,""));
                budgetDialog.setID(getBudgetId());
                budgetDialog.setTargetVal(getTarget());
                budgetDialog.setCategoryName(getCategory());
                budgetDialog.show(getChildFragmentManager(),"");
            }
        });
    }


    //helper functions
    private String formatMoney(String currencySymbol,double val){
        if(val == (int)val){
            return currencySymbol+(int)val;
        }else{
            return currencySymbol+String.format("%.2f",val);
        }
    }
}