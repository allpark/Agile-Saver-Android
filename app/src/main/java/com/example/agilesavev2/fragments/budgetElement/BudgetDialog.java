package com.example.agilesavev2.fragments.budgetElement;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.agilesavev2.R;
import com.example.agilesavev2.views.category.CategoryActivity;

import java.util.Currency;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class BudgetDialog extends AppCompatDialogFragment {
    private TextView textViewTitle, edit, delete;
    private EditText budgetProgress, target;
    private String title, budgetValue,id,categoryName, progress, targetVal;
    private DialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.budget_dialog,null);
        builder.setView(view).setTitle("");
        budgetProgress = view.findViewById(R.id.budget_progress);
        textViewTitle = view.findViewById(R.id.budget_name);
        target = view.findViewById(R.id.budget_target);
        edit = view.findViewById(R.id.edit);
        delete = view.findViewById(R.id.delete);

        textViewTitle.setText(this.title);

        String symbol = Currency.getInstance(getActivity().getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();
        this.budgetProgress.setText(progress);
        this.target.setText(targetVal.replace(symbol, ""));

        //event listeners

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rounded to 2 decimal places
                if(target.getText().toString().trim().equals("")){
                    target.setText("0");
                }

                if(budgetProgress.getText().toString().trim().equals("")){
                    budgetProgress.setText("0");
                }
                Double progressVal = Math.floor(Math.round(Double.parseDouble( budgetProgress.getText().toString()) *100)/100);
                Double targetVal = Math.floor(Math.round(Double.parseDouble( target.getText().toString()) *100)/100);
                HashMap<String, String> map =new HashMap<>();
                map.put("name", title);
                map.put("progress", Double.toString(progressVal));
                map.put("target", Double.toString(targetVal));
                listener.applyChanges(map);
                dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.delete(title);
                dismiss();
            }
        });

        return builder.create();
    }


    public void setTitle(String title){
        this.title=title;
    }
    public void setBudgetProgress(String budgetValue){
        this.progress = budgetValue;
    }
    public void setID(String id){
        this.id=id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setTargetVal(String targetVal) {
        this.targetVal = targetVal;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement DialogListener");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public interface DialogListener{
        void applyChanges(HashMap<String, String> map);
        void delete(String budgetName);
    }
}
