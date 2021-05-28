package com.example.agilesavev2.fragments.savingElement;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import com.example.agilesavev2.utils.DatePicker;

import java.util.Arrays;
import java.util.Currency;

public class SavingDialog extends AppCompatDialogFragment {

    private TextView textViewTitle, textViewGoal, textViewSaved, edit, delete;
    private EditText editGoal, editSaved;
    private Button editDate;

    private String title, id, goalDate, goal, saved;

    private DialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.saving_dialog,null);
        builder.setView(view).setTitle("");


        editGoal = view.findViewById(R.id.saving_dialog_goal);
        editSaved = view.findViewById(R.id.saving_dialog_saved);

        textViewTitle = view.findViewById(R.id.saving_dialog_title);
        textViewGoal = view.findViewById(R.id.saving_dialog_goal_text);
        textViewSaved = view.findViewById(R.id.saving_dialog_saved_text);

        edit = view.findViewById(R.id.saving_dialog_update);
        delete = view.findViewById(R.id.saving_dialog_delete);

        editDate = view.findViewById(R.id.saving_dialog_goal_date);

        textViewTitle.setText(this.title);
        String symbol = Currency.getInstance(getActivity().getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();
        textViewGoal.setText("New Target Goal: "+symbol +this.goal);
        textViewSaved.setText("Saved So Far: "+symbol +this.saved);

        editGoal.setText(goal);
        editSaved.setText(saved);
        editDate.setText("01/01/1999");

        DatePicker datePicker = new DatePicker(view.findViewById(R.id.saving_dialog_goal_date), null, getContext());


        ///

        editGoal.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String symbol = Currency.getInstance(getActivity().getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();
                    textViewGoal.setText("New Target Goal: "+symbol + editGoal.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });


        editSaved.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String symbol = Currency.getInstance(getActivity().getIntent().getStringExtra("MAIN_CURRENCY")).getSymbol();
                    textViewSaved.setText("New Saved Amount: "+symbol + editSaved.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.applyChanges(id,
                        editGoal.getText().toString(),
                        editSaved.getText().toString(),
                        formatDate(editDate.getText().toString())
                );
                dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.delete(id);
                dismiss();
            }
        });

        return builder.create();
    }

    public void setTitle(String title){
        this.title=title;
    }
    public void setTargetDate(String date){
        this.goalDate=date;
    }
    public void setTargetGoal(String goal){
        this.goal = goal;
    }
    public void setCurrentSavings(String savings){
        this.saved = savings;
    }
    public void setID(String id){
        this.id=id;
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
        void applyChanges(String savingID, String savingTargetGoal, String savingCurrent, String savingTargetDate);
        void delete(String savingID);
    }

    public String formatDate(String date){
        String[] dateAr = editDate.getText().toString().split(" ");
        String[] months = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        String m = Integer.toString(Arrays.asList(months).indexOf(dateAr[0])+1);;
        String day = dateAr[1].length()<2 ? "0"+dateAr[1] : dateAr[1];
        String month = m.length()<2 ? "0"+m : m;

        String year = dateAr[2];
        return year+"-"+month+"-"+day;
    }

}
