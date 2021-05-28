package com.example.agilesavev2.fragments.accounts;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.agilesavev2.R;
import com.example.agilesavev2.models.subscriptions.Subscription;
import com.example.agilesavev2.models.users.Account;

import java.util.Arrays;

public class UpdateAccountBalanceDialog extends AppCompatDialogFragment {
    private TextView update, delete, title;
    private EditText balance;
    private Account account;
    private DialogListener listener;

    public UpdateAccountBalanceDialog(Account account) {
        this.account = account;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.adjust_balance_dialog,null);
        builder.setView(view).setTitle("");


        title = view.findViewById(R.id.title);
        update = view.findViewById(R.id.update);
        delete = view.findViewById(R.id.delete);
        balance = view.findViewById(R.id.account_balance);

        title.setText(account.getName());
        balance.setText(Double.toString(account.getBalance()));




        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.applyChanges(account.getID(), account.getName(), Double.parseDouble(balance.getText().toString()));
                dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!account.isMain()) {
                    listener.delete(account.getID(), account.getName());
                    dismiss();
                }else{
                    Toast.makeText(getContext(), "Cannot remove main account!", Toast.LENGTH_SHORT).show();
                }
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
        void applyChanges(int id, String accountName, double newBalance );
        void delete(int id, String accountName);
    }

}
