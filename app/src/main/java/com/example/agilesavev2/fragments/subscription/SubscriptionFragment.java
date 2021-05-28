package com.example.agilesavev2.fragments.subscription;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.agilesavev2.R;
import com.example.agilesavev2.fragments.budgetElement.BudgetDialog;
import com.example.agilesavev2.models.subscriptions.Subscription;
import com.example.agilesavev2.utils.CurrencyConvertion;

import java.util.Currency;


public class SubscriptionFragment extends Fragment{
    private Subscription subscription;
    private String subscriptionName, category;
    private double subscriptionValue;
    private int iconColour;


    public SubscriptionFragment() {
        // Required empty public constructor
    }
    //    //int id, int userID, String name, String category, String started, double subscriptionValue
    public SubscriptionFragment(Subscription subscription,Integer iconColour){
        this.subscription = subscription;
        this.subscriptionName=subscription.getName();
        this.subscriptionValue=subscription.getSubscriptionValue();
        this.iconColour = iconColour;
        this.category = subscription.getCategory();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscription, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView name = view.findViewById(R.id.subscription_name);
        TextView subscriptionValue = view.findViewById(R.id.subscription_value);
        TextView category = view.findViewById(R.id.category);
        LinearLayout icon = view.findViewById(R.id.subscription_icon);

        icon.setBackgroundColor(this.iconColour);
        name.setText(this.subscriptionName);
        category.setText(this.category);
        String currency = getActivity().getIntent().getStringExtra("MAIN_CURRENCY");
        String currencySymbol = Currency.getInstance(currency).getSymbol();
        subscriptionValue.setText(currencySymbol+Double.toString(this.subscriptionValue));

        openDialog();

    }


    public void openDialog(){
        getView().findViewById(R.id.subscription).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubscriptionDialog subscriptionDialogDialog = new SubscriptionDialog(subscription);
                subscriptionDialogDialog.show(getChildFragmentManager(),null);
            }
        });
    }
}