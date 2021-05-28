package com.example.agilesavev2.fragments.TransactionActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.agilesavev2.R;
import com.example.agilesavev2.fragments.TransactionActivity.spendingActivityAssets.TransactionElement;
import com.example.agilesavev2.models.transactions.Transaction;
import com.example.agilesavev2.views.current_balance.CurrentBalanceActivity;
import com.example.agilesavev2.views.home.HomeActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionActivityFragment extends Fragment implements TransactionActivityActivityContract.spendingActivityView , com.example.agilesavev2.assets.Fragment {

    private TransactionActivityPresenter presenter;
    private LinearLayout linearLayout;
    public TransactionActivityFragment() {
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
        return inflater.inflate(R.layout.fragment_transaction_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new TransactionActivityPresenter(getActivity().getIntent(), this, getContext());
        ((com.example.agilesavev2.assets.View)getActivity()).setAdapter(this);

        ((TextView)view.findViewById(R.id.showMore)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getIntent().setClass(getContext(), CurrentBalanceActivity.class);
                startActivity(getActivity().getIntent());
            }
        });


    }

    @Override
    public void displayTransactions(List<Transaction> transactionList) {
        linearLayout = getView().findViewById(R.id.activity_list);
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        for(Transaction transactionData : transactionList){
            String accountName=null;
            HashMap<String, Integer> accountMap = (HashMap<String, Integer>) getActivity().getIntent().getSerializableExtra("ACCOUNTS");
            for(Map.Entry<String,Integer> account : accountMap.entrySet()){
                if(account.getValue() == transactionData.getAccountID()){
                    accountName = account.getKey();
                    break;
                }
            }
            TransactionElement transactionElement = new TransactionElement(transactionData.getName(), transactionData.getCategory(), accountName, transactionData.getDate(), Double.toString(transactionData.getTransaction()),transactionData.getType(), transactionData.getCurrency());
            fragmentTransaction.add(linearLayout.getId(), transactionElement);
        }
        fragmentTransaction.commitNow();




    }

    @Override
    public void hideTransaction() {
        ((HomeActivity)getActivity()).hideTransactionList();
    }

    @Override
    public void refreshFragment() {
        if(linearLayout!=null){
            linearLayout.removeAllViews();
        }

        presenter.getTransactions();
    }
}