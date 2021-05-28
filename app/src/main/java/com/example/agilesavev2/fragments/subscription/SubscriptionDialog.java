package com.example.agilesavev2.fragments.subscription;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.agilesavev2.R;
import com.example.agilesavev2.models.subscriptions.Subscription;

import java.util.Arrays;
import java.util.Currency;

public class SubscriptionDialog extends AppCompatDialogFragment {
    private TextView edit, delete, title;
    private EditText name, subscription_cost;
    private Spinner category;
    private DialogListener listener;
    private Subscription subscription;

    public SubscriptionDialog(Subscription subscription) {
        this.subscription = subscription;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.subscription_dialog,null);
        builder.setView(view).setTitle("");
        String[] categores =  {"Please select a category", "TV/Streaming",  "Transport",  "Gaming", "Software", "Pharmacy", "Eating out", "Sports", "Social media", "Essentials", "Digital", "Charity", "Entertainment", "Bills", "Personal care", "Shopping", "Holidays", "Finances"};
        int catIndex = Arrays.asList(categores).indexOf(subscription.getCategory());

        title = view.findViewById(R.id.title);
        edit = view.findViewById(R.id.edit);
        delete = view.findViewById(R.id.delete);
        name = view.findViewById(R.id.subscription_name);
        subscription_cost = view.findViewById(R.id.subscription_cost);
        category = view.findViewById(R.id.subscription_category);
        category.setSelection(catIndex);
        name.setText(subscription.getName());
        title.setText(subscription.getName());
        subscription_cost.setText(Double.toString(subscription.getSubscriptionValue()));



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formValidate()) {
                    listener.applyChanges(subscription.getId(), name.getText().toString(), Double.parseDouble(subscription_cost.getText().toString()), category.getSelectedItem().toString());
                    dismiss();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.delete(subscription.getId(),subscription.getName());
                dismiss();
            }
        });

        return builder.create();
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
        void applyChanges(int id, String name, double monthlyCost, String category);
        void delete(int id, String subscriptionName);
    }

    public boolean formValidate(){
        if(name.getText().toString().trim().equals("")){
            Toast.makeText(getContext(), "Give your subscription a name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(category.getSelectedItem().equals("Please select a category")){
            Toast.makeText(getContext(), "Give your subscription a category", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
